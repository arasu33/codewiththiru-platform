package com.codewiththiru.platform.identity.analytics

import com.codewiththiru.platform.identity.auth.AuthMethod

class AuthenticationMetrics(
    private val analytics: IdentityAnalyticsProvider,
) {
    fun trackLoginAttempt(method: AuthMethod) {
        analytics.logEvent("login_attempt", mapOf("method" to method.name))
    }

    fun trackLoginSuccess(
        method: AuthMethod,
        userId: String,
    ) {
        analytics.logEvent("login_success", mapOf("method" to method.name, "userId" to userId))
    }

    fun trackLoginFailure(
        method: AuthMethod,
        reason: String,
    ) {
        analytics.logEvent("login_failure", mapOf("method" to method.name, "reason" to reason))
    }
}
