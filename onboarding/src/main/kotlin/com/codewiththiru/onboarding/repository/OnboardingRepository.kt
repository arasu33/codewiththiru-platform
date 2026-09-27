package com.codewiththiru.onboarding.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.onboardingDataStore: DataStore<Preferences> by preferencesDataStore(name = "cwt_onboarding_prefs")

/**
 * Interface to persist and observe the user's onboarding completion status.
 */
interface OnboardingRepository {
    /** Whether the user has completed or skipped the onboarding flow. */
    val isOnboardingCompleted: Flow<Boolean>

    /** Marks the onboarding state as completed or resets it. */
    suspend fun setOnboardingCompleted(completed: Boolean = true)
}

/**
 * Default implementation of [OnboardingRepository] backed by DataStore Preferences.
 */
class DataStoreOnboardingRepository(
    private val context: Context,
) : OnboardingRepository {
    private val completedKey = booleanPreferencesKey("key_onboarding_completed")

    override val isOnboardingCompleted: Flow<Boolean> =
        context.onboardingDataStore.data.map { prefs ->
            prefs[completedKey] ?: false
        }

    override suspend fun setOnboardingCompleted(completed: Boolean) {
        context.onboardingDataStore.edit { prefs ->
            prefs[completedKey] = completed
        }
    }
}
