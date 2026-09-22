package com.ontest.app.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "waste_listings")
data class WasteListing(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sourceReportId: Long? = null,
    val buyerName: String = "",
    val buyerType: String, // "Composting", "Biogas", "Fertilizer"
    val quantityKg: Int,
    val distanceKm: Float = 0f,
    val status: String = "available", // "available", "requested"
    val isUserListing: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)
