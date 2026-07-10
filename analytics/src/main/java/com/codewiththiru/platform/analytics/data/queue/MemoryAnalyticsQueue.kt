package com.codewiththiru.platform.analytics.data.queue

import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * An in-memory implementation of [AnalyticsQueue].
 * Follows FIFO behavior and respects maxQueuedEvents.
 */
internal class MemoryAnalyticsQueue(
    private val config: AnalyticsConfig,
) : AnalyticsQueue {
    private val queue = mutableListOf<AnalyticsEvent>()
    private val mutex = Mutex()

    override suspend fun enqueue(event: AnalyticsEvent) {
        mutex.withLock {
            queue.add(event)
            // Enforce max queued events (FIFO cleanup)
            if (queue.size > config.maxQueuedEvents) {
                queue.removeAt(0)
            }
        }
    }

    override suspend fun dequeue(batchSize: Int): List<AnalyticsEvent> {
        mutex.withLock {
            return queue.take(batchSize).toList()
        }
    }

    override suspend fun peek(count: Int): List<AnalyticsEvent> {
        mutex.withLock {
            return queue.take(count).toList()
        }
    }

    override suspend fun remove(events: List<AnalyticsEvent>) {
        mutex.withLock {
            queue.removeAll(events)
        }
    }

    override suspend fun clear() {
        mutex.withLock {
            queue.clear()
        }
    }

    override suspend fun size(): Int {
        mutex.withLock {
            return queue.size
        }
    }
}
