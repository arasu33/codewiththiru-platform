package com.codewiththiru.platform.game.sync.engine

import com.codewiththiru.platform.game.sync.api.SyncStatus
import kotlinx.coroutines.flow.StateFlow

/**
 * Core processor that drains the SyncQueueEngine, talks to the SyncProvider,
 * and passes collisions to the ConflictResolver.
 */
interface SyncEngine {
    val currentStatus: StateFlow<SyncStatus>

    /**
     * Attempts to process all pending requests in the queue.
     * Automatically suspends/fails if network drops.
     */
    suspend fun processQueue()

    /**
     * Performs a full pull from the provider for a specific collection,
     * merging or overwriting local data.
     */
    suspend fun pullAndMerge(
        collection: String,
        documentId: String,
    )
}
