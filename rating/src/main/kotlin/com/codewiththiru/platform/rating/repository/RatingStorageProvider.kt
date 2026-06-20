@file:Suppress("MaxLineLength")

package com.codewiththiru.platform.rating.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

/**
 * Manages persistent storage for rating engine metrics.
 */
interface RatingStorageProvider {
    val launchCount: Flow<Int>
    val eventCount: Flow<Int>
    val installDate: Flow<Long>
    val lastPromptDate: Flow<Long>
    val lastReviewDate: Flow<Long>
    val lastFeedbackDate: Flow<Long>

    suspend fun incrementLaunchCount()
    suspend fun incrementEventCount()
    suspend fun setInstallDate(dateMillis: Long)
    suspend fun setLastPromptDate(dateMillis: Long)
    suspend fun setLastReviewDate(dateMillis: Long)
    suspend fun setLastFeedbackDate(dateMillis: Long)
}

private val Context.ratingDataStore: DataStore<Preferences> by preferencesDataStore(name = "rating_datastore")

class RatingStorageProviderImpl(private val context: Context) : RatingStorageProvider {
    override val launchCount: Flow<Int> = context.ratingDataStore.data.map { it[RatingStorageKeys.LAUNCH_COUNT] ?: 0 }
    override val eventCount: Flow<Int> = context.ratingDataStore.data.map { it[RatingStorageKeys.EVENT_COUNT] ?: 0 }
    override val installDate: Flow<Long> = context.ratingDataStore.data.map { it[RatingStorageKeys.INSTALL_DATE] ?: 0L }
    override val lastPromptDate: Flow<Long> = context.ratingDataStore.data.map { it[RatingStorageKeys.LAST_PROMPT_DATE] ?: 0L }
    override val lastReviewDate: Flow<Long> = context.ratingDataStore.data.map { it[RatingStorageKeys.LAST_REVIEW_DATE] ?: 0L }
    override val lastFeedbackDate: Flow<Long> = context.ratingDataStore.data.map { it[RatingStorageKeys.LAST_FEEDBACK_DATE] ?: 0L }

    override suspend fun incrementLaunchCount() {
        context.ratingDataStore.edit { prefs ->
            val current = prefs[RatingStorageKeys.LAUNCH_COUNT] ?: 0
            prefs[RatingStorageKeys.LAUNCH_COUNT] = current + 1
        }
    }

    override suspend fun incrementEventCount() {
        context.ratingDataStore.edit { prefs ->
            val current = prefs[RatingStorageKeys.EVENT_COUNT] ?: 0
            prefs[RatingStorageKeys.EVENT_COUNT] = current + 1
        }
    }

    override suspend fun setInstallDate(dateMillis: Long) {
        context.ratingDataStore.edit { prefs ->
            // Only set if not already set
            if (prefs[RatingStorageKeys.INSTALL_DATE] == null) {
                prefs[RatingStorageKeys.INSTALL_DATE] = dateMillis
            }
        }
    }

    override suspend fun setLastPromptDate(dateMillis: Long) {
        context.ratingDataStore.edit { prefs -> prefs[RatingStorageKeys.LAST_PROMPT_DATE] = dateMillis }
    }

    override suspend fun setLastReviewDate(dateMillis: Long) {
        context.ratingDataStore.edit { prefs -> prefs[RatingStorageKeys.LAST_REVIEW_DATE] = dateMillis }
    }

    override suspend fun setLastFeedbackDate(dateMillis: Long) {
        context.ratingDataStore.edit { prefs -> prefs[RatingStorageKeys.LAST_FEEDBACK_DATE] = dateMillis }
    }
}
