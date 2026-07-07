package com.codewiththiru.platform.identity.security

import com.codewiththiru.platform.identity.api.IdentityResult

interface IdentitySecurityValidator {
    suspend fun validateRequest(payload: String): IdentityResult<Unit>

    suspend fun isSessionHijacked(sessionId: String): Boolean
}
