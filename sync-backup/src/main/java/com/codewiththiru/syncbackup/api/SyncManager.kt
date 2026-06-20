package com.codewiththiru.syncbackup.api

import kotlinx.coroutines.flow.StateFlow

interface SyncManager {
    val state: StateFlow<SyncState>
    
    fun initialize(config: SyncConfig)
    suspend fun syncNow(): SyncResult
    suspend fun requestBackup(): SyncResult
    suspend fun requestRestore(): SyncResult
}
