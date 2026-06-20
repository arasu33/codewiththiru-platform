package com.codewiththiru.syncbackup.analytics

data class RestoreMetrics(
    val itemsRestored: Int,
    val durationMillis: Long,
    val migrationsApplied: Int
)
