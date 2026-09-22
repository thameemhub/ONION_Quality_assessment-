package com.ontest.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ontest.app.data.model.GradingReport
import kotlinx.coroutines.flow.Flow

@Dao
interface GradingReportDao {

    @Insert
    suspend fun insert(report: GradingReport): Long

    @Query("SELECT * FROM grading_reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<GradingReport>>

    @Query("SELECT * FROM grading_reports ORDER BY timestamp DESC LIMIT 3")
    fun getRecentReports(): Flow<List<GradingReport>>

    @Query("SELECT * FROM grading_reports WHERE id = :id")
    suspend fun getById(id: Long): GradingReport?

    @Query("SELECT COUNT(*) FROM grading_reports")
    fun getCount(): Flow<Int>

    @Query("SELECT COALESCE(AVG(gradeAPercent), 0) FROM grading_reports")
    fun getAverageGradeA(): Flow<Double>
}
