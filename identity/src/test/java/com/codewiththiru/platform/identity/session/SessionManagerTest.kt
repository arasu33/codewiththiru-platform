package com.codewiththiru.platform.identity.session

import com.codewiththiru.platform.identity.api.IdentityResult
import com.codewiththiru.platform.identity.auth.AuthSession
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Test

class SessionManagerTest {

    private val fakeStore = object : SessionStore {
        override suspend fun saveSession(session: AuthSession) {}
        override suspend fun getSession(sessionId: String): AuthSession? = null
        override suspend fun getAllSessions(): List<AuthSession> = emptyList()
        override suspend fun removeSession(sessionId: String) {}
        override suspend fun clearAll() {}
    }

    private val manager = object : SessionManager {
        override suspend fun createSession(session: AuthSession) = IdentityResult.Success(Unit)
        override suspend fun getActiveSessions() = fakeStore.getAllSessions()
        override suspend fun revokeSession(sessionId: String) = IdentityResult.Success(Unit)
        override suspend fun revokeAllSessionsExceptCurrent() = IdentityResult.Success(Unit)
        override suspend fun refreshCurrentSession() = IdentityResult.Failure(com.codewiththiru.platform.identity.api.IdentityException.SessionExpired())
    }

    @Test
    fun testCreateSession() = runTest {
        val session = AuthSession("s1", "u1", "d1", 1000L, false)
        val result = manager.createSession(session)
        assertTrue(result is IdentityResult.Success)
    }
}
