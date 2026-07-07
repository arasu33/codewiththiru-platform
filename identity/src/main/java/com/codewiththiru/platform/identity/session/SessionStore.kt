package com.codewiththiru.platform.identity.session

import com.codewiththiru.platform.identity.auth.AuthSession

interface SessionStore {
    suspend fun saveSession(session: AuthSession)

    suspend fun getSession(sessionId: String): AuthSession?

    suspend fun getAllSessions(): List<AuthSession>

    suspend fun removeSession(sessionId: String)

    suspend fun clearAll()
}
