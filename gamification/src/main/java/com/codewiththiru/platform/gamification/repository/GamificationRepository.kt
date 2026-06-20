package com.codewiththiru.platform.gamification.repository

import com.codewiththiru.platform.gamification.api.GamificationResult
import com.codewiththiru.platform.gamification.api.GamificationState
import kotlinx.coroutines.flow.Flow

/**
 * Interface abstracting the storage and synchronization of gamification data.
 */
public interface GamificationRepository {
    /** 
     * Retrieves the current state from the fastest available local source. 
     * Defaults to 0/empty if the user has no history.
     */
    public fun observeLocalState(userId: String): Flow<GamificationState>

    /**
     * Updates local state and queues a sync.
     */
    public suspend fun commitState(state: GamificationState): GamificationResult<Unit>

    /**
     * Attempts an immediate sync with the remote authority.
     */
    public suspend fun syncRemote(userId: String): GamificationResult<GamificationState>
}
