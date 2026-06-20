package com.codewiththiru.syncbackup.recovery

import com.codewiththiru.syncbackup.api.SyncResult

interface RecoveryManager {
    suspend fun createCheckpoint(): RecoveryCheckpoint
    suspend fun rollbackToCheckpoint(checkpointId: String): SyncResult
    fun getAvailableCheckpoints(): List<RecoveryCheckpoint>
}
