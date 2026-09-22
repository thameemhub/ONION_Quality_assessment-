package com.ontest.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ontest.app.data.AppDatabase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

data class ReputationState(
    val score: Int = 50,
    val label: String = "Getting Started",
    val totalBatches: Int = 0,
    val avgGradeA: Double = 0.0,
    val marketplaceListings: Int = 0
)

class ReputationViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val reportDao = db.gradingReportDao()
    private val listingDao = db.wasteListingDao()

    val reputationState: StateFlow<ReputationState> = combine(
        reportDao.getCount(),
        reportDao.getAverageGradeA(),
        listingDao.getUserListingCount()
    ) { totalBatches, avgA, marketListings ->
        val base = 50
        val gradeBonus = (avgA * 0.4).toInt().coerceAtMost(34)
        val batchBonus = (totalBatches * 2).coerceAtMost(10)
        val marketBonus = (marketListings * 2).coerceAtMost(6)
        val score = (base + gradeBonus + batchBonus + marketBonus).coerceAtMost(100)

        val label = when {
            score >= 85 -> "Consistent Quality"
            score >= 70 -> "Good Standing"
            score >= 55 -> "Building Trust"
            else -> "Getting Started"
        }

        ReputationState(
            score = score,
            label = label,
            totalBatches = totalBatches,
            avgGradeA = avgA,
            marketplaceListings = marketListings
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ReputationState())
}
