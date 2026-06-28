package com.codewiththiru.platform.game.sync.manager

import com.codewiththiru.platform.game.sync.api.SyncStatus
import com.codewiththiru.platform.game.sync.api.SyncType
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for the Sync Framework.
 */
interface SyncManager {
    val globalStatus: StateFlow<SyncStatus>

    /**
     * Enqueues a local mutation to be pushed to the cloud when network is available.
     */
    fun scheduleSync(
        collection: String,
        documentId: String,
        jsonPayload: String,
    )

    /**
     * Forces an immediate pull or push, bypassing normal queue wait times.
     */
    suspend fun forceSyncNow(type: SyncType)

    /**
     * Cancels all pending sync operations in the queue.
     */
    fun cancelAllPending()
}
