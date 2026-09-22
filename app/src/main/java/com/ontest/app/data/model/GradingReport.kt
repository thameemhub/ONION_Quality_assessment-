package com.ontest.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "grading_reports")
data class GradingReport(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val timestamp: Long = System.currentTimeMillis(),
    val gradeAPercent: Int,
    val gradeBPercent: Int,
    val ursPercent: Int,
    val rotRiskLevel: String, // "Low", "Medium", "High"
    val confidenceScore: Int,
    val locationText: String
)
