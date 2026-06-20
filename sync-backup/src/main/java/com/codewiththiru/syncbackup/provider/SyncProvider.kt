package com.codewiththiru.syncbackup.provider

import com.codewiththiru.syncbackup.api.SyncEntity
import com.codewiththiru.syncbackup.api.SyncResult

interface SyncProvider {
    suspend fun pushChanges(entities: List<SyncEntity>): SyncResult
    suspend fun fetchChanges(sinceTimestamp: Long): List<SyncEntity>
}
