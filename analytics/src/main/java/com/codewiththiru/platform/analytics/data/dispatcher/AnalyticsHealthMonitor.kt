package com.codewiththiru.platform.analytics.data.dispatcher

import com.codewiththiru.platform.analytics.data.queue.AnalyticsQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Periodically observes the analytics infrastructure and emits health metrics.
 */
public class AnalyticsHealthMonitor(
    private val queue: AnalyticsQueue,
    private val deadLetterQueue: AnalyticsQueue?,
    private val metricsTracker: MetricsTracker,
    private val scope: CoroutineScope
) {
    private var job: Job? = null
    private val monitorIntervalMs = 60 * 1000L // 1 minute

    public fun start() {
        if (job?.isActive == true) return
        job = scope.launch {
            while (isActive) {
                try {
                    val size = queue.size()
                    metricsTracker.trackQueueSize(size)
                    
                    deadLetterQueue?.let {
                        val dlqSize = it.size()
                        if (dlqSize > 0) {
                            metricsTracker.trackDeadLetter(dlqSize)
                        }
                    }
                } catch (e: Exception) {
                    // Ignore exceptions during monitoring
                }
                delay(monitorIntervalMs)
            }
        }
    }

    public fun stop() {
        job?.cancel()
        job = null
    }
}
