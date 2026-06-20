package com.codewiththiru.platform.identity.auth

data class LoginRequest(
    val method: AuthMethod,
    val credentials: Map<String, String> = emptyMap(),
    val token: String? = null
)
