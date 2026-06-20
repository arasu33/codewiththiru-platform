package com.codewiththiru.platform.identity.auth.social

import com.codewiththiru.platform.identity.api.IdentityException
import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthMethod
import com.codewiththiru.platform.identity.auth.AuthProvider
import com.codewiththiru.platform.identity.auth.LoginRequest
import com.codewiththiru.platform.identity.auth.LoginResponse

class GoogleAuthProvider : AuthProvider {
    override val method = AuthMethod.GOOGLE

    override suspend fun authenticate(request: LoginRequest): IdentityResult<LoginResponse> {
        // Pseudo-implementation mapping Google token to Identity backend
        return IdentityResult.Failure(IdentityException("Not implemented"))
    }
}
