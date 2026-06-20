package com.codewiththiru.platform.identity.auth

data class AuthSession(
    val sessionId: String,
    val userId: String,
    val deviceId: String,
    val expiresAt: Long,
    val isAnonymous: Boolean
)
