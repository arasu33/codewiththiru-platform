package com.codewiththiru.platform.identity.auth.social

import com.codewiththiru.platform.identity.api.IdentityException
import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthMethod
import com.codewiththiru.platform.identity.auth.AuthenticationManager
import com.codewiththiru.platform.identity.auth.LoginRequest
import com.codewiththiru.platform.identity.auth.LoginResponse

class SocialLoginManager(
    private val authManager: AuthenticationManager,
) {
    suspend fun loginWithGoogle(idToken: String): IdentityResult<LoginResponse> =
        authManager.login(LoginRequest(AuthMethod.GOOGLE, token = idToken))

    suspend fun loginWithApple(identityToken: String): IdentityResult<LoginResponse> =
        authManager.login(LoginRequest(AuthMethod.APPLE, token = identityToken))

    suspend fun loginWithFacebook(accessToken: String): IdentityResult<LoginResponse> =
        authManager.login(LoginRequest(AuthMethod.FACEBOOK, token = accessToken))

    suspend fun linkAccount(
        method: AuthMethod,
        token: String,
    ): IdentityResult<Unit> = IdentityResult.Failure(IdentityException("Not implemented"))
}
