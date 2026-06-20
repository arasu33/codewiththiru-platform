package com.codewiththiru.platform.identity.security

interface CredentialProtection {
    suspend fun hashPassword(password: String): String
    suspend fun verifyPassword(password: String, hash: String): Boolean
    suspend fun checkPwnedPasswords(hashPrefix: String): Boolean
}
