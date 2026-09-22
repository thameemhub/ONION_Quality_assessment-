package com.ontest.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ontest.app.data.AppDatabase
import com.ontest.app.data.DataStoreManager
import com.ontest.app.data.model.WasteListing
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MarketplaceViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val listingDao = db.wasteListingDao()
    val dataStore = DataStoreManager(application)

    val allListings: StateFlow<List<WasteListing>> = listingDao.getAllListings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userListings: StateFlow<List<WasteListing>> = listingDao.getUserListings()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedFilter = MutableStateFlow("All")
    val selectedFilter: StateFlow<String> = _selectedFilter.asStateFlow()

    val hasSeenMarketplaceCoach: StateFlow<Boolean> = dataStore.hasSeenMarketplaceCoach
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun setFilter(filter: String) {
        _selectedFilter.value = filter
    }

    fun dismissMarketplaceCoach() {
        viewModelScope.launch {
            dataStore.setMarketplaceCoachSeen()
        }
    }

    fun requestContact(listingId: Long) {
        viewModelScope.launch {
            listingDao.updateStatus(listingId, "requested")
        }
    }

    fun getFilteredListings(allItems: List<WasteListing>, filter: String): List<WasteListing> {
        return if (filter == "All") allItems.filter { !it.isUserListing }
        else allItems.filter { !it.isUserListing && it.buyerType == filter }
    }
}
