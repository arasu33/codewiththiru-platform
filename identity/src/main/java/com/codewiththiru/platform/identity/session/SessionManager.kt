package com.codewiththiru.platform.identity.session

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthSession

interface SessionManager {
    suspend fun createSession(session: AuthSession): IdentityResult<Unit>

    suspend fun getActiveSessions(): List<AuthSession>

    suspend fun revokeSession(sessionId: String): IdentityResult<Unit>

    suspend fun revokeAllSessionsExceptCurrent(): IdentityResult<Unit>

    suspend fun refreshCurrentSession(): IdentityResult<AuthSession>
}
