package com.codewiththiru.platform.identity.token

import com.codewiththiru.platform.identity.api.IdentityResult

interface TokenManager {
    suspend fun getAccessToken(): IdentityResult<AccessToken>
    suspend fun refreshTokens(): IdentityResult<AccessToken>
    suspend fun rotateTokens(refreshToken: RefreshToken): IdentityResult<Unit>
    suspend fun clearTokens()
}
