package com.codewiththiru.platform.identity.auth

import com.codewiththiru.platform.identity.api.IdentityResult

interface AuthProvider {
    val method: AuthMethod

    suspend fun authenticate(request: LoginRequest): IdentityResult<LoginResponse>
}
