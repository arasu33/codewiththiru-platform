package com.codewiththiru.platform.analytics.config

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Assert.assertFalse
import org.junit.Test

class AnalyticsConfigTest {

    @Test
    fun `default values are correct`() {
        val config = AnalyticsConfig()
        assertTrue(config.enabled)
        assertFalse(config.debugLogging)
        assertEquals(7, config.retentionDays)
        assertEquals(5000, config.maxQueuedEvents)

        val batchConfig = AnalyticsBatchConfig()
        assertEquals(50, batchConfig.batchSize)
        assertEquals(15, batchConfig.flushIntervalMinutes)

        val privacyConfig = AnalyticsPrivacyConfig()
        assertTrue(privacyConfig.anonymizeIp)
        assertFalse(privacyConfig.collectAdvertisingId)
    }
}
