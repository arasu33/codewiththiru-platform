package com.codewiththiru.platform.identity.session

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthSession

interface SessionRecovery {
    suspend fun attemptRecovery(): IdentityResult<AuthSession>

    suspend fun storeRecoveryPayload(payload: String)
}
