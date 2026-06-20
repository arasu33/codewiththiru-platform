package com.codewiththiru.platform.identity.token

interface TokenStorage {
    suspend fun saveAccessToken(token: AccessToken)
    suspend fun saveRefreshToken(token: RefreshToken)
    suspend fun getAccessToken(): AccessToken?
    suspend fun getRefreshToken(): RefreshToken?
    suspend fun clearTokens()
}
