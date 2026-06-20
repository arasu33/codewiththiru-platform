package com.codewiththiru.syncbackup.backup

data class BackupPolicy(
    val isWifiOnly: Boolean = true,
    val requiresCharging: Boolean = true,
    val batteryNotLow: Boolean = true,
    val frequencyMillis: Long = 86400000 // 24 hours
)
