package com.codewiththiru.remoteconfig.experiment

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.stickyAssignmentDataStore by preferencesDataStore(name = "experiment_sticky_assignments")

class StickyAssignmentManager(private val context: Context) {
    private val key = stringPreferencesKey("assignments")
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getAssignment(experimentId: String): String? {
        val prefs = context.stickyAssignmentDataStore.data.first()
        val jsonStr = prefs[key]
        if (jsonStr.isNullOrEmpty()) return null
        
        return try {
            val map = json.decodeFromString<Map<String, String>>(jsonStr)
            map[experimentId]
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            null
        }
    }

    suspend fun saveAssignment(experimentId: String, variantId: String) {
        context.stickyAssignmentDataStore.edit { prefs ->
            val jsonStr = prefs[key]
            val map = if (jsonStr.isNullOrEmpty()) {
                mutableMapOf()
            } else {
                try {
                    json.decodeFromString<MutableMap<String, String>>(jsonStr)
                } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
                    mutableMapOf()
                }
            }
            map[experimentId] = variantId
            prefs[key] = json.encodeToString(map)
        }
    }
}
