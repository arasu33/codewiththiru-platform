package com.codewiththiru.platform.rating.repository

import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey

/**
 * Centralized DataStore keys for the rating module.
 */
object RatingStorageKeys {
    val LAUNCH_COUNT = intPreferencesKey("rating_launch_count")
    val EVENT_COUNT = intPreferencesKey("rating_event_count")
    val INSTALL_DATE = longPreferencesKey("rating_install_date")
    val LAST_PROMPT_DATE = longPreferencesKey("rating_last_prompt_date")
    val LAST_REVIEW_DATE = longPreferencesKey("rating_last_review_date")
    val LAST_FEEDBACK_DATE = longPreferencesKey("rating_last_feedback_date")
}
