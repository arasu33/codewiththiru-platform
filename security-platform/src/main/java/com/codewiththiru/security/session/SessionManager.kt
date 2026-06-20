package com.codewiththiru.security.session

interface SessionManager {
    fun createSession(userId: String): String
    fun invalidateSession(sessionId: String)
}
