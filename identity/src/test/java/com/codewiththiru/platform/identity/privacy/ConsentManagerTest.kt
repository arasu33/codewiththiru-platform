package com.codewiththiru.platform.identity.privacy

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import kotlinx.coroutines.test.runTest

class ConsentManagerTest {
    private val manager =
        object : ConsentManager {
            private val consents = mutableMapOf<String, Boolean>()

            override suspend fun recordConsent(
                userId: String,
                policyId: String,
                granted: Boolean,
            ) {
                consents["$userId-$policyId"] = granted
            }

            override suspend fun hasConsented(
                userId: String,
                policyId: String,
            ): Boolean = consents["$userId-$policyId"] ?: false

            override suspend fun revokeConsent(
                userId: String,
                policyId: String,
            ) {
                consents["$userId-$policyId"] = false
            }
        }

    @Test
    fun testConsent() =
        runTest {
            manager.recordConsent("u1", "p1", true)
            assertTrue(manager.hasConsented("u1", "p1"))
            manager.revokeConsent("u1", "p1")
            assertFalse(manager.hasConsented("u1", "p1"))
        }
}
