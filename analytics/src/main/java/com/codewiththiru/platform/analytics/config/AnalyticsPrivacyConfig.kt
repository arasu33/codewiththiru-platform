package com.codewiththiru.platform.analytics.config

/**
 * Privacy controls for analytics data collection.
 *
 * @property anonymizeIp Whether IP addresses should be anonymized before sending.
 * @property collectAdvertisingId Whether the device advertising ID should be collected.
 */
public data class AnalyticsPrivacyConfig(
    public val anonymizeIp: Boolean = true,
    public val collectAdvertisingId: Boolean = false
)
