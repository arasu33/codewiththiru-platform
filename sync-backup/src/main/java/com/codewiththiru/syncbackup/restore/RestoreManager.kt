package com.codewiththiru.syncbackup.restore

import com.codewiththiru.syncbackup.api.SyncResult

interface RestoreManager {
    suspend fun performFullRestore(snapshotId: String): SyncResult
    suspend fun performPartialRestore(snapshotId: String, collections: List<String>): SyncResult
    suspend fun validateSnapshot(snapshotId: String): Boolean
}
