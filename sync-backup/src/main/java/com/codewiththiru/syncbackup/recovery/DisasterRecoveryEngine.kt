package com.codewiththiru.syncbackup.recovery

import com.codewiththiru.syncbackup.api.SyncResult

class DisasterRecoveryEngine(private val recoveryManager: RecoveryManager) {
    suspend fun attemptRecovery(): SyncResult {
        // Find most recent valid checkpoint and restore
        val checkpoints = recoveryManager.getAvailableCheckpoints()
        if (checkpoints.isEmpty()) return SyncResult.Failure(Exception("No recovery points available") as com.codewiththiru.syncbackup.api.SyncException)
        return recoveryManager.rollbackToCheckpoint(checkpoints.last().id)
    }
}
