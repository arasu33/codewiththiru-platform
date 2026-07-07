package com.codewiththiru.platform.identity.token

data class AccessToken(
    val value: String,
    val expiresAt: Long,
)

data class RefreshToken(
    val value: String,
    val expiresAt: Long,
)
