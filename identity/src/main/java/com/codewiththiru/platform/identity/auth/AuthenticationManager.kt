package com.codewiththiru.platform.identity.auth

import com.codewiththiru.platform.identity.api.IdentityResult

interface AuthenticationManager {
    suspend fun login(request: LoginRequest): IdentityResult<LoginResponse>

    suspend fun logout(sessionId: String): IdentityResult<Unit>

    suspend fun getAvailableMethods(): List<AuthMethod>
}

class DefaultAuthenticationManager(
    private val providers: Map<AuthMethod, AuthProvider>,
) : AuthenticationManager {
    override suspend fun login(request: LoginRequest): IdentityResult<LoginResponse> {
        val provider =
            providers[request.method] ?: return IdentityResult.Failure(
                com.codewiththiru.platform.identity.api.IdentityException.InvalidCredentials(
                    "Provider not found for method",
                ),
            )
        return provider.authenticate(request)
    }

    override suspend fun logout(sessionId: String): IdentityResult<Unit> = IdentityResult.Success(Unit)

    override suspend fun getAvailableMethods(): List<AuthMethod> = providers.keys.toList()
}
