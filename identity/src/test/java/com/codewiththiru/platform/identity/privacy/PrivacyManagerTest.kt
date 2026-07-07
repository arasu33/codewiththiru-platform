package com.codewiththiru.platform.identity.privacy

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class PrivacyManagerTest {
    private val manager =
        object : PrivacyManager {
            private val prefs = mutableMapOf<String, PrivacyPreferences>()

            override suspend fun getPrivacyPreferences(userId: String): PrivacyPreferences =
                prefs[userId] ?: PrivacyPreferences()

            override suspend fun updatePrivacyPreferences(
                userId: String,
                preferences: PrivacyPreferences,
            ) {
                prefs[userId] = preferences
            }
        }

    @Test
    fun testUpdatePreferences() =
        runTest {
            val newPrefs = PrivacyPreferences(allowAnalyticsTracking = false)
            manager.updatePrivacyPreferences("u1", newPrefs)
            val fetched = manager.getPrivacyPreferences("u1")
            assertEquals(false, fetched.allowAnalyticsTracking)
        }
}
