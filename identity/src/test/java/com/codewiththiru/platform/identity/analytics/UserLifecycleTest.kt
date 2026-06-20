package com.codewiththiru.platform.identity.analytics

import org.junit.Assert.assertEquals
import org.junit.Test

class UserLifecycleTest {

    private val fakeAnalytics = object : IdentityAnalyticsProvider {
        val properties = mutableMapOf<String, String>()
        override fun logEvent(name: String, params: Map<String, Any>) {}
        override fun setUserProperty(name: String, value: String) {
            properties[name] = value
        }
    }

    private val tracker = UserLifecycleTracker(fakeAnalytics)

    @Test
    fun testAccountCreated() {
        tracker.trackAccountCreated("u1")
        assertEquals("active", fakeAnalytics.properties["account_status"])
    }
}
