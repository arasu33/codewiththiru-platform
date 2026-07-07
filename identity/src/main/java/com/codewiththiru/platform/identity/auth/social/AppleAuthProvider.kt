package com.codewiththiru.platform.identity.auth.social

import com.codewiththiru.platform.identity.api.IdentityException
import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthMethod
import com.codewiththiru.platform.identity.auth.AuthProvider
import com.codewiththiru.platform.identity.auth.LoginRequest
import com.codewiththiru.platform.identity.auth.LoginResponse

class AppleAuthProvider : AuthProvider {
    override val method = AuthMethod.APPLE

    override suspend fun authenticate(request: LoginRequest): IdentityResult<LoginResponse> =
        IdentityResult.Failure(IdentityException("Not implemented"))
}
