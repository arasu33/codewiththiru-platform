package com.codewiththiru.syncbackup.sync

import com.codewiththiru.syncbackup.api.SyncResult
import kotlinx.coroutines.flow.Flow

interface CloudSyncManager {
    suspend fun startManualSync(): SyncResult
    fun observeSyncProgress(): Flow<Float>
    suspend fun scheduleNextSync()
    fun cancelSync()
}
