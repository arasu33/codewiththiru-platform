package com.codewiththiru.platform.gamification.api

import com.codewiththiru.platform.gamification.config.GamificationConfig
import com.codewiththiru.platform.gamification.repository.GamificationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

/**
 * Standard implementation of GamificationManager.
 */
public class DefaultGamificationManager(
    private val repository: GamificationRepository,
    private val config: GamificationConfig
) : GamificationManager {

    private val eventFlow = MutableSharedFlow<GamificationEvent>(extraBufferCapacity = 64)
    private var currentUserId: String = ""

    override suspend fun initialize(userId: String) {
        if (!config.isEnabled) return
        currentUserId = userId
        syncNow()
    }

    override fun observeState(): Flow<GamificationState> {
        return repository.observeLocalState(currentUserId)
    }

    override fun observeEvents(): Flow<GamificationEvent> {
        return eventFlow.asSharedFlow()
    }

    override suspend fun syncNow(): GamificationResult<Unit> {
        if (!config.isEnabled) return GamificationResult.Error(IllegalStateException("Gamification disabled"))
        
        val syncResult = repository.syncRemote(currentUserId)
        return if (syncResult is GamificationResult.Success) {
            repository.commitState(syncResult.data)
        } else {
            // Offline fallback -> if offline is allowed, we consider the sync attempt "successful" for local usage.
            if (config.offlineModeAllowed) {
                GamificationResult.Success(Unit)
            } else {
                GamificationResult.Error(Exception("Remote sync failed and offline mode is disallowed."))
            }
        }
    }
}
