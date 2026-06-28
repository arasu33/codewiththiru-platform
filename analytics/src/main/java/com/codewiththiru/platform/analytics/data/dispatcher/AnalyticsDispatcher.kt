package com.codewiththiru.platform.analytics.data.dispatcher

import android.util.Log
import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.config.AnalyticsBatchConfig
import com.codewiththiru.platform.analytics.data.queue.AnalyticsQueue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

/**
 * Periodically flushes queued events to the [AnalyticsProvider].
 */
public class AnalyticsDispatcher(
    private val queue: AnalyticsQueue,
    private val provider: AnalyticsProvider,
    private val config: AnalyticsBatchConfig,
    private val scope: CoroutineScope,
    private val deadLetterQueue: AnalyticsQueue? = null
) {
    private var job: Job? = null
    private var consecutiveFailures = 0

    /** Starts the periodic dispatch loop. */
    @Suppress("MagicNumber")
    public fun start() {
        if (job?.isActive == true) return
        job = scope.launch {
            while (isActive) {
                delay(config.flushIntervalMinutes * 60 * 1000L)
                flush()
            }
        }
    }

    /** Stops the dispatch loop. */
    public fun stop() {
        job?.cancel()
        job = null
    }

    /** Forces an immediate flush of the queue. */
    @Suppress("TooGenericExceptionCaught", "NestedBlockDepth", "LoopWithTooManyJumpStatements", "MagicNumber")
    public suspend fun flush() {
        try {
            var dispatched = 0
            while (true) {
                // Peek the events to ensure they remain in queue during failure
                val batch = queue.peek(config.batchSize)
                if (batch.isEmpty()) break
                
                var success = true
                try {
                    // Track all events in the batch
                    batch.forEach { provider.trackEvent(it) }
                    provider.flush()
                } catch (e: Exception) {
                    success = false
                    Log.e("AnalyticsDispatcher", "Provider failed during dispatch. Entering backoff.", e)
                }

                if (success) {
                    // Remove from queue ONLY after successful dispatch
                    queue.remove(batch)
                    dispatched += batch.size
                    consecutiveFailures = 0 // Reset backoff
                } else {
                    // Exponential backoff logic
                    consecutiveFailures++
                    if (consecutiveFailures > 10 && deadLetterQueue != null) {
                        Log.e("AnalyticsDispatcher", "Batch failed > 10 times. Moving to Dead Letter Queue.")
                        batch.forEach { deadLetterQueue.enqueue(it) }
                        queue.remove(batch)
                        consecutiveFailures = 0
                    } else {
                        val backoffMinutes = (1 shl (consecutiveFailures - 1)).coerceAtMost(15)
                        Log.w("AnalyticsDispatcher", "Backing off for $backoffMinutes minutes")
                        
                        // Stop the current flush loop
                        break
                    }
                }
                
                // Yield to prevent monopolizing thread if queue is massive
                kotlinx.coroutines.yield()
            }
            if (dispatched > 0) {
                Log.d("AnalyticsDispatcher", "Flushed $dispatched events")
            }
        } catch (e: Exception) {
            Log.e("AnalyticsDispatcher", "Error during flush", e)
        }
    }
}
