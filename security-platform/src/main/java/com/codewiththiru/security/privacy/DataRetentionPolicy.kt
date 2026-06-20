package com.codewiththiru.security.privacy

data class DataRetentionPolicy(
    val maxRetentionDays: Int = 365,
    val autoDeleteExpiredData: Boolean = true
)
