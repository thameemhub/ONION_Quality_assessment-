package com.ontest.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ontest.app.data.AppDatabase
import com.ontest.app.data.DataStoreManager
import com.ontest.app.data.model.GradingReport
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val reportDao = db.gradingReportDao()
    private val dataStore = DataStoreManager(application)

    val totalGradings: StateFlow<Int> = reportDao.getCount()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val avgGradeA: StateFlow<Double> = reportDao.getAverageGradeA()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0.0)

    val recentReports: StateFlow<List<GradingReport>> = reportDao.getRecentReports()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val hasSeenHomeCoach: StateFlow<Boolean> = dataStore.hasSeenHomeCoach
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), true)

    fun dismissHomeCoach() {
        viewModelScope.launch {
            dataStore.setHomeCoachSeen()
        }
    }

    // Reputation computed from total gradings and average grade
    fun computeReputationScore(totalBatches: Int, avgA: Double, marketListings: Int): Int {
        val base = 50
        val gradeBonus = (avgA * 0.4).toInt().coerceAtMost(34)
        val batchBonus = (totalBatches * 2).coerceAtMost(10)
        val marketBonus = (marketListings * 2).coerceAtMost(6)
        return (base + gradeBonus + batchBonus + marketBonus).coerceAtMost(100)
    }
}
