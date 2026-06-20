package com.codewiththiru.platform.analytics.domain.validation

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class AnalyticsValidatorTest {

    @Test
    fun `validates and truncates event name`() {
        val longName = "A".repeat(50)
        val event = AnalyticsEvent(longName)
        val sanitized = AnalyticsValidator.validateAndSanitize(event)
        assertEquals(40, sanitized.name.length)
        assertEquals("A".repeat(40), sanitized.name)
    }

    @Test
    fun `removes illegal characters from name and keys`() {
        val event = AnalyticsEvent("invalid!@#name", mapOf("key!@#" to "value"))
        val sanitized = AnalyticsValidator.validateAndSanitize(event)
        assertEquals("invalid___name", sanitized.name)
        assertTrue(sanitized.parameters.containsKey("key___"))
    }

    @Test
    fun `truncates parameter count to 25`() {
        val params = (1..30).associate { "key$it" to "value" }
        val event = AnalyticsEvent("test", params)
        val sanitized = AnalyticsValidator.validateAndSanitize(event)
        assertEquals(25, sanitized.parameters.size)
    }

    @Test
    fun `filters out null parameters`() {
        val params = mapOf("key1" to "value1", "key2" to null)
        val event = AnalyticsEvent("test", params)
        val sanitized = AnalyticsValidator.validateAndSanitize(event)
        assertEquals(1, sanitized.parameters.size)
        assertEquals("value1", sanitized.parameters["key1"])
    }

    @Test
    fun `scrubs PII data`() {
        val params = mapOf(
            "email" to "test@example.com",
            "phone" to "+1 555-123-4567",
            "pan" to "ABCDE1234F",
            "aadhar" to "1234 5678 9012",
            "ip" to "192.168.1.1",
            "mixed" to "My email is test@example.com"
        )
        val event = AnalyticsEvent("pii_test", params)
        val sanitized = AnalyticsValidator.validateAndSanitize(event)

        assertEquals("[email_redacted]", sanitized.parameters["email"])
        assertEquals("[phone_redacted]", sanitized.parameters["phone"])
        assertEquals("[pan_redacted]", sanitized.parameters["pan"])
        assertEquals("[aadhar_redacted]", sanitized.parameters["aadhar"])
        assertEquals("[ip_redacted]", sanitized.parameters["ip"])
        assertEquals("My email is [email_redacted]", sanitized.parameters["mixed"])
    }
}
