package com.ontest.app.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ontest.app.data.AppDatabase
import com.ontest.app.data.model.GradingReport
import com.ontest.app.data.model.WasteListing
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.random.Random

data class GradingUiState(
    val isAnalyzing: Boolean = false,
    val analyzeStep: String = "",
    val result: GradingReport? = null,
    val showUrsPrompt: Boolean = false
)

class GradingViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val reportDao = db.gradingReportDao()
    private val listingDao = db.wasteListingDao()

    private val _uiState = MutableStateFlow(GradingUiState())
    val uiState: StateFlow<GradingUiState> = _uiState.asStateFlow()

    private val locations = listOf(
        "Koyambedu Mandi",
        "Lasalgaon APMC",
        "Pimpalgaon Mandi"
    )

    private val analyzeSteps = listOf(
        "Detecting onions...",
        "Checking size & shape...",
        "Estimating quality..."
    )

    fun startAnalysis() {
        viewModelScope.launch {
            _uiState.value = GradingUiState(isAnalyzing = true, analyzeStep = analyzeSteps[0])

            for (i in analyzeSteps.indices) {
                _uiState.value = _uiState.value.copy(analyzeStep = analyzeSteps[i])
                delay(800L)
            }
            delay(400L)

            val result = generateMockResult()
            _uiState.value = GradingUiState(
                isAnalyzing = false,
                result = result,
                showUrsPrompt = result.ursPercent > 15
            )
        }
    }

    private fun generateMockResult(): GradingReport {
        val gradeA = Random.nextInt(50, 86)
        val remaining = 100 - gradeA
        val maxUrs = remaining.coerceAtMost(30)
        val minUrs = (remaining - 35).coerceAtLeast(0)
        val urs = Random.nextInt(minUrs, maxUrs + 1)
        val gradeB = 100 - gradeA - urs

        val rotRisk = when {
            urs < 10 -> "Low"
            urs <= 20 -> "Medium"
            else -> "High"
        }
        val confidence = Random.nextInt(85, 99)
        val location = locations.random()

        val now = System.currentTimeMillis()
        return GradingReport(
            timestamp = now,
            gradeAPercent = gradeA,
            gradeBPercent = gradeB,
            ursPercent = urs,
            rotRiskLevel = rotRisk,
            confidenceScore = confidence,
            locationText = location
        )
    }

    fun saveReport(onSaved: (Long) -> Unit = {}) {
        val report = _uiState.value.result ?: return
        viewModelScope.launch {
            val id = reportDao.insert(report)
            _uiState.value = _uiState.value.copy(result = report.copy(id = id))
            onSaved(id)
        }
    }

    fun createUserListing(reportId: Long, quantityKg: Int) {
        viewModelScope.launch {
            val listing = WasteListing(
                sourceReportId = reportId,
                buyerName = "My Listing",
                buyerType = "Composting",
                quantityKg = quantityKg,
                distanceKm = 0f,
                status = "available",
                isUserListing = true
            )
            listingDao.insert(listing)
        }
    }

    fun dismissUrsPrompt() {
        _uiState.value = _uiState.value.copy(showUrsPrompt = false)
    }

    fun resetState() {
        _uiState.value = GradingUiState()
    }

    suspend fun getReportById(id: Long): GradingReport? {
        return reportDao.getById(id)
    }
}
