package com.codewiththiru.platform.identity.security

import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class RiskAssessmentTest {
    private val engine =
        object : RiskAssessmentEngine {
            var recorded = false

            override suspend fun assessRisk(
                userId: String,
                action: String,
            ): Float = 0.5f

            override suspend fun recordSuspiciousActivity(
                userId: String,
                action: String,
            ) {
                recorded = true
            }
        }

    @Test
    fun testAssessRisk() =
        runTest {
            assertEquals(0.5f, engine.assessRisk("u1", "login"))
            engine.recordSuspiciousActivity("u1", "login")
            assertEquals(true, engine.recorded)
        }
}
