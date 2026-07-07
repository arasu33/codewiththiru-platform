package com.codewiththiru.platform.identity.analytics

class SessionMetrics(
    private val analytics: IdentityAnalyticsProvider,
) {
    fun trackSessionCreated(sessionId: String) {
        analytics.logEvent("session_created", mapOf("sessionId" to sessionId))
    }

    fun trackSessionExpired(sessionId: String) {
        analytics.logEvent("session_expired", mapOf("sessionId" to sessionId))
    }
}
