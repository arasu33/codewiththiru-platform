package com.codewiththiru.syncbackup.analytics

data class SyncMetrics(
    val itemsSynced: Int,
    val durationMillis: Long,
    val conflictsResolved: Int,
    val bytesTransferred: Long
)
