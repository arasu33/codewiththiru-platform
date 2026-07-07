package com.codewiththiru.platform.identity.analytics

import com.codewiththiru.platform.identity.auth.AuthMethod
import org.junit.Assert.assertEquals
import org.junit.Test

class AuthenticationMetricsTest {
    private val fakeAnalytics =
        object : IdentityAnalyticsProvider {
            val events = mutableListOf<Pair<String, Map<String, Any>>>()

            override fun logEvent(
                name: String,
                params: Map<String, Any>,
            ) {
                events.add(name to params)
            }

            override fun setUserProperty(
                name: String,
                value: String,
            ) {}
        }

    private val metrics = AuthenticationMetrics(fakeAnalytics)

    @Test
    fun testLoginAttempt() {
        metrics.trackLoginAttempt(AuthMethod.EMAIL_PASSWORD)
        assertEquals("login_attempt", fakeAnalytics.events.first().first)
        assertEquals(AuthMethod.EMAIL_PASSWORD.name, fakeAnalytics.events.first().second["method"])
    }
}
