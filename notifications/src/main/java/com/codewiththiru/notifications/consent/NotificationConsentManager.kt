package com.codewiththiru.notifications.consent

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.consentDataStore by preferencesDataStore(name = "notification_consent")

class NotificationConsentManager(private val context: Context) {
    private val CONSENT_KEY = booleanPreferencesKey("has_notification_consent")

    val hasConsent: Flow<Boolean> = context.consentDataStore.data.map { prefs ->
        prefs[CONSENT_KEY] ?: false
    }

    suspend fun setConsent(granted: Boolean) {
        context.consentDataStore.edit { prefs ->
            prefs[CONSENT_KEY] = granted
        }
    }
}
