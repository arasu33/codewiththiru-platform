package com.codewiththiru.security.network

data class SecureHttpPolicy(
    val enforceHttps: Boolean = true,
    val enforceTls13: Boolean = true
)
