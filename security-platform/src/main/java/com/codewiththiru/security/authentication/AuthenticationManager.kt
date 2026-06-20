package com.codewiththiru.security.authentication

interface AuthenticationManager {
    fun validateToken(token: String): Boolean
    fun refreshToken(oldToken: String): String
}
