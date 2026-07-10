package com.codewiththiru.platform.analytics.api

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codewiththiru.platform.analytics.data.queue.AnalyticsQueue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

private val Context.consentDataStore: DataStore<Preferences> by preferencesDataStore(name = "analytics_consent")

/**
 * Default implementation of [AnalyticsConsentManager].
 */
internal class DefaultAnalyticsConsentManager(
    private val context: Context,
    private val queue: AnalyticsQueue,
) : AnalyticsConsentManager {
    private val consentKey = intPreferencesKey("consent_state")

    override suspend fun grant() {
        context.consentDataStore.edit { prefs ->
            prefs[consentKey] = ConsentState.Granted.ordinal
        }
    }

    override suspend fun deny() {
        context.consentDataStore.edit { prefs ->
            prefs[consentKey] = ConsentState.Denied.ordinal
        }

        // Enforce privacy: Clear queue of all pending events to prevent dispatch
        queue.clear()
    }

    override suspend fun getConsent(): ConsentState =
        context.consentDataStore.data
            .map { prefs ->
                val ordinal = prefs[consentKey] ?: ConsentState.Unknown.ordinal
                ConsentState.values().getOrNull(ordinal) ?: ConsentState.Unknown
            }.first()
}
