package com.codewiththiru.security.session

data class SessionPolicy(
    val timeoutMinutes: Int = 30,
    val maxConcurrentSessions: Int = 3
)
