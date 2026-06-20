package com.codewiththiru.platform.analytics.data.queue

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Interface representing a storage queue for offline analytics events.
 */
public interface AnalyticsQueue {
    /**
     * Enqueues a new event.
     */
    public suspend fun enqueue(event: AnalyticsEvent)

    /**
     * Dequeues up to [batchSize] events from the queue.
     */
    public suspend fun dequeue(batchSize: Int): List<AnalyticsEvent>

    /**
     * Peeks up to [count] events from the queue without removing them.
     */
    public suspend fun peek(count: Int): List<AnalyticsEvent>

    /**
     * Removes the specified events from the queue upon successful dispatch.
     */
    public suspend fun remove(events: List<AnalyticsEvent>)

    /**
     * Clears all queued events. Used when consent is denied.
     */
    public suspend fun clear()

    /**
     * Returns the current number of events in the queue.
     */
    public suspend fun size(): Int
}
