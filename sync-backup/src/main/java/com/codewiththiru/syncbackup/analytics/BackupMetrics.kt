package com.codewiththiru.syncbackup.analytics

data class BackupMetrics(
    val sizeBytes: Long,
    val durationMillis: Long,
    val filesBackedUp: Int,
    val isEncrypted: Boolean
)
