package com.codewiththiru.platform.analytics.data.queue

import android.content.Context
import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Stores events that repeatedly failed to dispatch.
 * Prevents toxic events from blocking the main queue forever while ensuring they aren't lost.
 */
internal class DeadLetterAnalyticsQueue(
    private val context: Context,
    private val config: AnalyticsConfig,
) : AnalyticsQueue {
    // Internal queue specifically for dead letters
    private val internalQueue = DataStoreAnalyticsQueue(context, config, "dead_letter_queue")

    override suspend fun enqueue(event: AnalyticsEvent) {
        internalQueue.enqueue(event)
    }

    public suspend fun enqueueAll(events: List<AnalyticsEvent>) {
        events.forEach { enqueue(it) }
    }

    override suspend fun dequeue(batchSize: Int): List<AnalyticsEvent> = internalQueue.dequeue(batchSize)

    override suspend fun peek(count: Int): List<AnalyticsEvent> = internalQueue.peek(count)

    override suspend fun remove(events: List<AnalyticsEvent>) {
        internalQueue.remove(events)
    }

    override suspend fun clear() {
        internalQueue.clear()
    }

    override suspend fun size(): Int = internalQueue.size()
}
