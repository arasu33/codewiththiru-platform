package com.codewiththiru.consent.manager

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codewiththiru.consent.model.ConsentCategory
import com.codewiththiru.consent.model.ConsentSnapshot
import com.codewiththiru.consent.model.ConsentStatus
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.consentDataStore: DataStore<Preferences> by preferencesDataStore(name = "cwt_consent_prefs")

/**
 * Interface managing user consent state for GDPR, CCPA, and telemetry compliance.
 */
interface ConsentManager {
    /** Reactive stream of the complete consent snapshot. */
    val consentSnapshot: Flow<ConsentSnapshot>

    /** Checks whether consent has been determined for a category. */
    fun isGranted(category: ConsentCategory): Flow<Boolean>

    /** Updates consent status for a specific category. */
    suspend fun setConsent(
        category: ConsentCategory,
        status: ConsentStatus,
    )

    /** Grants all non-essential consent categories. */
    suspend fun grantAll()

    /** Denies all non-essential consent categories. */
    suspend fun denyAll()

    /** Resets consent back to unconfigured. */
    suspend fun resetConsent()
}

/**
 * Default DataStore-backed implementation of [ConsentManager].
 */
class DataStoreConsentManager(
    private val context: Context,
) : ConsentManager {
    override val consentSnapshot: Flow<ConsentSnapshot> =
        context.consentDataStore.data.map { prefs ->
            val map =
                ConsentCategory.entries.associateWith { category ->
                    if (category == ConsentCategory.NECESSARY) {
                        ConsentStatus.GRANTED
                    } else {
                        val key = stringPreferencesKey("consent_${category.name.lowercase()}")
                        val raw = prefs[key]
                        when (raw) {
                            ConsentStatus.GRANTED.name -> ConsentStatus.GRANTED
                            ConsentStatus.DENIED.name -> ConsentStatus.DENIED
                            else -> ConsentStatus.NOT_CONFIGURED
                        }
                    }
                }
            ConsentSnapshot(map)
        }

    override fun isGranted(category: ConsentCategory): Flow<Boolean> = consentSnapshot.map { it.isGranted(category) }

    override suspend fun setConsent(
        category: ConsentCategory,
        status: ConsentStatus,
    ) {
        if (category == ConsentCategory.NECESSARY) return
        val key = stringPreferencesKey("consent_${category.name.lowercase()}")
        context.consentDataStore.edit { prefs ->
            prefs[key] = status.name
        }
    }

    override suspend fun grantAll() {
        context.consentDataStore.edit { prefs ->
            ConsentCategory.entries.filter { it != ConsentCategory.NECESSARY }.forEach { category ->
                val key = stringPreferencesKey("consent_${category.name.lowercase()}")
                prefs[key] = ConsentStatus.GRANTED.name
            }
        }
    }

    override suspend fun denyAll() {
        context.consentDataStore.edit { prefs ->
            ConsentCategory.entries.filter { it != ConsentCategory.NECESSARY }.forEach { category ->
                val key = stringPreferencesKey("consent_${category.name.lowercase()}")
                prefs[key] = ConsentStatus.DENIED.name
            }
        }
    }

    override suspend fun resetConsent() {
        context.consentDataStore.edit { prefs ->
            ConsentCategory.entries.forEach { category ->
                val key = stringPreferencesKey("consent_${category.name.lowercase()}")
                prefs.remove(key)
            }
        }
    }
}
