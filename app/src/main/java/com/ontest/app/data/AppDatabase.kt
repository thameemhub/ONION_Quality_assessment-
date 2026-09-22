package com.ontest.app.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.ontest.app.data.dao.GradingReportDao
import com.ontest.app.data.dao.WasteListingDao
import com.ontest.app.data.model.GradingReport
import com.ontest.app.data.model.WasteListing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [GradingReport::class, WasteListing::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun gradingReportDao(): GradingReportDao
    abstract fun wasteListingDao(): WasteListingDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "ontest_database"
                )
                    .addCallback(SeedCallback())
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class SeedCallback : Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                CoroutineScope(Dispatchers.IO).launch {
                    seedMarketplace(database.wasteListingDao())
                }
            }
        }

        private suspend fun seedMarketplace(dao: WasteListingDao) {
            val mockListings = listOf(
                WasteListing(
                    buyerName = "GreenCycle Composting",
                    buyerType = "Composting",
                    quantityKg = 500,
                    distanceKm = 3.2f,
                    status = "available"
                ),
                WasteListing(
                    buyerName = "BioEnergy Solutions",
                    buyerType = "Biogas",
                    quantityKg = 1000,
                    distanceKm = 7.5f,
                    status = "available"
                ),
                WasteListing(
                    buyerName = "AgroFert Industries",
                    buyerType = "Fertilizer",
                    quantityKg = 300,
                    distanceKm = 5.1f,
                    status = "available"
                ),
                WasteListing(
                    buyerName = "EcoCompost Hub",
                    buyerType = "Composting",
                    quantityKg = 750,
                    distanceKm = 2.8f,
                    status = "available"
                ),
                WasteListing(
                    buyerName = "MethaneWorks Plant",
                    buyerType = "Biogas",
                    quantityKg = 2000,
                    distanceKm = 12.0f,
                    status = "available"
                )
            )
            dao.insertAll(mockListings)
        }
    }
}
