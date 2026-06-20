package com.codewiththiru.syncbackup.backup

import com.codewiththiru.syncbackup.api.SyncResult

interface BackupManager {
    suspend fun performFullBackup(): SyncResult
    suspend fun performIncrementalBackup(): SyncResult
    suspend fun requestBackup(policy: BackupPolicy): SyncResult
}
