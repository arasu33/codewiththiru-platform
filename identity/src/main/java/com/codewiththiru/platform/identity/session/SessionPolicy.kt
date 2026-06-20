package com.codewiththiru.platform.identity.session

data class SessionPolicy(
    val maxConcurrentSessions: Int = 5,
    val enforceSingleDevicePerType: Boolean = false,
    val absoluteTimeoutMinutes: Long = 60 * 24 * 30, // 30 days
    val idleTimeoutMinutes: Long = 60 * 24 * 7 // 7 days
)
