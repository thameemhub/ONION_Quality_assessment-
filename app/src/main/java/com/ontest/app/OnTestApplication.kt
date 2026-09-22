package com.ontest.app

import android.app.Application
import com.ontest.app.data.AppDatabase

class OnTestApplication : Application() {
    val database: AppDatabase by lazy { AppDatabase.getInstance(this) }

    override fun onCreate() {
        super.onCreate()
        // Eagerly initialize database to trigger seed callback
        database
    }
}
