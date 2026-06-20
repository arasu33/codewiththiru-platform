package com.codewiththiru.syncbackup.sync

import java.util.UUID

data class SyncJob(
    val id: String = UUID.randomUUID().toString(),
    val strategy: SyncStrategy,
    val enqueuedAtMillis: Long = System.currentTimeMillis()
)

enum class SyncStrategy {
    FULL_SYNC,
    DELTA_SYNC,
    INCREMENTAL_SYNC,
    REAL_TIME_SYNC
}
