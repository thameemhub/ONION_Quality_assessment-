package com.ontest.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "ontest_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        private val HAS_COMPLETED_ONBOARDING = booleanPreferencesKey("has_completed_onboarding")
        private val HAS_SEEN_HOME_COACH = booleanPreferencesKey("has_seen_home_coach")
        private val HAS_SEEN_CALIBRATION_COACH = booleanPreferencesKey("has_seen_calibration_coach")
        private val HAS_SEEN_MARKETPLACE_COACH = booleanPreferencesKey("has_seen_marketplace_coach")
    }

    val hasCompletedOnboarding: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[HAS_COMPLETED_ONBOARDING] ?: false
    }

    suspend fun setOnboardingCompleted() {
        context.dataStore.edit { prefs ->
            prefs[HAS_COMPLETED_ONBOARDING] = true
        }
    }

    suspend fun resetOnboarding() {
        context.dataStore.edit { prefs ->
            prefs[HAS_COMPLETED_ONBOARDING] = false
            prefs[HAS_SEEN_HOME_COACH] = false
            prefs[HAS_SEEN_CALIBRATION_COACH] = false
            prefs[HAS_SEEN_MARKETPLACE_COACH] = false
        }
    }

    val hasSeenHomeCoach: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[HAS_SEEN_HOME_COACH] ?: false
    }

    suspend fun setHomeCoachSeen() {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_HOME_COACH] = true
        }
    }

    val hasSeenCalibrationCoach: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[HAS_SEEN_CALIBRATION_COACH] ?: false
    }

    suspend fun setCalibrationCoachSeen() {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_CALIBRATION_COACH] = true
        }
    }

    val hasSeenMarketplaceCoach: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[HAS_SEEN_MARKETPLACE_COACH] ?: false
    }

    suspend fun setMarketplaceCoachSeen() {
        context.dataStore.edit { prefs ->
            prefs[HAS_SEEN_MARKETPLACE_COACH] = true
        }
    }
}
