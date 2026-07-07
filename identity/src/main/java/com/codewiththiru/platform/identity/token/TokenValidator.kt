package com.codewiththiru.platform.identity.token

interface TokenValidator {
    suspend fun isTokenValid(token: String): Boolean

    suspend fun getExpiry(token: String): Long

    suspend fun getClaims(token: String): Map<String, String>
}
