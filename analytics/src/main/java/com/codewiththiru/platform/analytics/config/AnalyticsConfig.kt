package com.codewiththiru.platform.analytics.config

/**
 * Core configuration for the analytics module.
 *
 * @property enabled Whether analytics tracking is globally enabled.
 * @property debugLogging Whether to enable debug logging.
 * @property retentionDays Maximum number of days to retain queued offline events.
 * @property maxQueuedEvents Maximum number of events to hold in the offline queue.
 */
public data class AnalyticsConfig(
    public val enabled: Boolean = true,
    public val debugLogging: Boolean = false,
    public val retentionDays: Int = 7,
    public val maxQueuedEvents: Int = 5000
)
