package com.codewiththiru.platform.analytics.config

/**
 * Configuration for offline event batching and dispatch.
 *
 * @property batchSize Number of events to send in a single batch.
 * @property flushIntervalMinutes How often to flush the queue to the network.
 */
public data class AnalyticsBatchConfig(
    public val batchSize: Int = 50,
    public val flushIntervalMinutes: Int = 15,
)
