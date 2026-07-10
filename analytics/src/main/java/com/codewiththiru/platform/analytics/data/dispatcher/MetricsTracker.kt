package com.codewiththiru.platform.analytics.data.dispatcher

import android.util.Log

/**
 * Tracks performance and operational metrics for the analytics pipeline.
 */
internal interface MetricsTracker {
    fun trackQueueSize(size: Int)

    fun trackBatchLatency(latencyMs: Long)

    fun trackQueueDrop(
        count: Int,
        reason: String,
    )

    fun trackDeadLetter(count: Int)
}

/**
 * Default implementation of MetricsTracker that logs to standard output.
 */
internal class DefaultMetricsTracker : MetricsTracker {
    override fun trackQueueSize(size: Int) {
        Log.v("AnalyticsMetrics", "Queue size: $size")
    }

    override fun trackBatchLatency(latencyMs: Long) {
        Log.v("AnalyticsMetrics", "Batch latency: ${latencyMs}ms")
    }

    override fun trackQueueDrop(
        count: Int,
        reason: String,
    ) {
        Log.w("AnalyticsMetrics", "Dropped $count events. Reason: $reason")
    }

    override fun trackDeadLetter(count: Int) {
        Log.w("AnalyticsMetrics", "Moved $count events to DLQ")
    }
}
