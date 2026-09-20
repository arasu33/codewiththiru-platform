package com.codewiththiru.platform.gamification.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codewiththiru.platform.gamification.api.GamificationResult
import com.codewiththiru.platform.gamification.api.GamificationState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.gamificationDataStore: DataStore<Preferences> by preferencesDataStore(name = "gamification_state")

/**
 * DataStore-backed repository for offline-first state tracking.
 */
public class DefaultGamificationRepository(
    private val context: Context,
    private val json: Json = Json { ignoreUnknownKeys = true }
) : GamificationRepository {



    override fun observeLocalState(userId: String): Flow<GamificationState> {
        val stateKey = stringPreferencesKey("state_$userId")
        return context.applicationContext.gamificationDataStore.data.map { prefs ->
            val jsonStr = prefs[stateKey]
            if (jsonStr.isNullOrEmpty()) {
                GamificationState(userId, 0L, 1, 0L, 0L, 0L)
            } else {
                try {
                    val state = json.decodeFromString<GamificationState>(jsonStr)
                    if (state.userId == userId) state else GamificationState(userId, 0L, 1, 0L, 0L, 0L)
                } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
                    GamificationState(userId, 0L, 1, 0L, 0L, 0L)
                }
            }
        }
    }

    override suspend fun commitState(state: GamificationState): GamificationResult<Unit> {
        val stateKey = stringPreferencesKey("state_${state.userId}")
        return try {
            context.applicationContext.gamificationDataStore.edit { prefs ->
                prefs[stateKey] = json.encodeToString(state)
            }
            // Enqueue work manager sync job here...
            GamificationResult.Success(Unit)
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            GamificationResult.Error(e, "DATASTORE_WRITE_ERROR")
        }
    }

    override suspend fun syncRemote(userId: String): GamificationResult<GamificationState> {
        // In a real provider model, this calls Provider.sync(localState)
        // For now, this is mocked as returning the local state.
        return GamificationResult.Error(UnsupportedOperationException("Remote sync not attached yet."))
    }
}
