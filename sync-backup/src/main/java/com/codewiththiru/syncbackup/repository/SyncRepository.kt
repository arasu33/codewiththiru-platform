package com.codewiththiru.syncbackup.repository

import kotlinx.coroutines.flow.Flow

interface SyncRepository {
    suspend fun saveLastSyncTimestamp(timestamp: Long)
    fun getLastSyncTimestamp(): Flow<Long>
    suspend fun clearSyncData()
}
