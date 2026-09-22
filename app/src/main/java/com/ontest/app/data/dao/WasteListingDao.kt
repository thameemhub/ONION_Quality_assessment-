package com.ontest.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ontest.app.data.model.WasteListing
import kotlinx.coroutines.flow.Flow

@Dao
interface WasteListingDao {

    @Insert
    suspend fun insert(listing: WasteListing): Long

    @Insert
    suspend fun insertAll(listings: List<WasteListing>)

    @Query("SELECT * FROM waste_listings ORDER BY createdAt DESC")
    fun getAllListings(): Flow<List<WasteListing>>

    @Query("SELECT * FROM waste_listings WHERE buyerType = :type ORDER BY createdAt DESC")
    fun getByType(type: String): Flow<List<WasteListing>>

    @Query("SELECT * FROM waste_listings WHERE isUserListing = 1 ORDER BY createdAt DESC")
    fun getUserListings(): Flow<List<WasteListing>>

    @Query("UPDATE waste_listings SET status = :status WHERE id = :id")
    suspend fun updateStatus(id: Long, status: String)

    @Query("SELECT COUNT(*) FROM waste_listings WHERE isUserListing = 1")
    fun getUserListingCount(): Flow<Int>

    @Query("SELECT COUNT(*) FROM waste_listings")
    fun getTotalCount(): Flow<Int>
}
