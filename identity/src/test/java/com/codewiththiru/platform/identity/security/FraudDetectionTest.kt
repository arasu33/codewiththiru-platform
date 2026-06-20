package com.codewiththiru.platform.identity.security

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class FraudDetectionTest {

    private val engine = object : FraudDetectionEngine {
        override suspend fun analyzeLoginAttempt(userId: String, ipAddress: String, deviceId: String): FraudRisk {
            return if (ipAddress == "1.1.1.1") FraudRisk.BLOCKED else FraudRisk.LOW
        }
    }

    @Test
    fun testAnalyze() = runTest {
        assertEquals(FraudRisk.LOW, engine.analyzeLoginAttempt("u1", "0.0.0.0", "d1"))
        assertEquals(FraudRisk.BLOCKED, engine.analyzeLoginAttempt("u1", "1.1.1.1", "d1"))
    }
}
