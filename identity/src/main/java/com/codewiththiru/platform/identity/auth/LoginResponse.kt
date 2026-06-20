package com.codewiththiru.platform.identity.auth

import com.codewiththiru.platform.identity.token.AccessToken
import com.codewiththiru.platform.identity.token.RefreshToken

data class LoginResponse(
    val session: AuthSession,
    val accessToken: AccessToken,
    val refreshToken: RefreshToken?
)
