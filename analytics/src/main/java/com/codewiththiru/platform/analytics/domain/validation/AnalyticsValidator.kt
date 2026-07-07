package com.codewiththiru.platform.analytics.domain.validation

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Validates and sanitizes analytics events to ensure they meet constraints and do not contain PII.
 */
public object AnalyticsValidator {
    private const val MAX_EVENT_NAME_LENGTH = 40
    private const val MAX_PARAM_COUNT = 25
    private const val MAX_PARAM_VALUE_LENGTH = 100

    private val EMAIL_REGEX = """[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}""".toRegex()
    private val PHONE_REGEX = """\+?[\d\s\-\.()]{10,}""".toRegex()
    private val PAN_REGEX = """[A-Z]{5}\d{4}[A-Z]""".toRegex()
    private val AADHAR_REGEX = """\d{4}\s?\d{4}\s?\d{4}""".toRegex()
    private val IP_REGEX = """\d{1,3}\.\d{1,3}\.\d{1,3}\.\d{1,3}""".toRegex()
    private val ILLEGAL_CHAR_REGEX = """[^a-zA-Z0-9_]""".toRegex()

    /**
     * Validates and sanitizes the given [event].
     * Returns a new sanitized [AnalyticsEvent].
     */
    public fun validateAndSanitize(event: AnalyticsEvent): AnalyticsEvent {
        // Sanitize name: remove illegal characters and truncate
        var safeName = event.name.replace(ILLEGAL_CHAR_REGEX, "_")
        if (safeName.length > MAX_EVENT_NAME_LENGTH) {
            safeName = safeName.substring(0, MAX_EVENT_NAME_LENGTH)
        }

        // Filter nulls, limit parameter count
        val safeParams =
            event.parameters.entries
                .filter { it.value != null }
                .take(MAX_PARAM_COUNT)
                .associate { (k, v) ->
                    val safeKey = k.replace(ILLEGAL_CHAR_REGEX, "_")
                    val safeValue = sanitizeValue(v.toString())
                    safeKey to safeValue
                }

        return AnalyticsEvent(
            name = safeName,
            parameters = safeParams,
            timestamp = event.timestamp,
        )
    }

    private fun sanitizeValue(value: String): String {
        var scrubbed = value
        scrubbed = scrubbed.replace(EMAIL_REGEX, "[email_redacted]")
        scrubbed = scrubbed.replace(AADHAR_REGEX, "[aadhar_redacted]")
        scrubbed = scrubbed.replace(PAN_REGEX, "[pan_redacted]")
        scrubbed = scrubbed.replace(IP_REGEX, "[ip_redacted]")
        scrubbed = scrubbed.replace(PHONE_REGEX, "[phone_redacted]")

        if (scrubbed.length > MAX_PARAM_VALUE_LENGTH) {
            scrubbed = scrubbed.substring(0, MAX_PARAM_VALUE_LENGTH)
        }

        return scrubbed
    }
}
