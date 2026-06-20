package com.codewiththiru.platform.analytics.data.queue

import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class MemoryAnalyticsQueueTest {

    @Test
    fun `enqueue respects maxQueuedEvents and performs FIFO`() = runTest {
        val config = AnalyticsConfig(maxQueuedEvents = 3)
        val queue = MemoryAnalyticsQueue(config)

        queue.enqueue(AnalyticsEvent("event1"))
        queue.enqueue(AnalyticsEvent("event2"))
        queue.enqueue(AnalyticsEvent("event3"))
        queue.enqueue(AnalyticsEvent("event4")) // Should evict event1

        assertEquals(3, queue.size())
        val dequeued = queue.dequeue(3)
        assertEquals("event2", dequeued[0].name)
        assertEquals("event3", dequeued[1].name)
        assertEquals("event4", dequeued[2].name)
    }

    @Test
    fun `dequeue respects batch size`() = runTest {
        val queue = MemoryAnalyticsQueue(AnalyticsConfig())
        for (i in 1..5) {
            queue.enqueue(AnalyticsEvent("event$i"))
        }

        val batch = queue.dequeue(2)
        assertEquals(2, batch.size)
        assertEquals("event1", batch[0].name)
        assertEquals("event2", batch[1].name)
    }

    @Test
    fun `remove removes specified events`() = runTest {
        val queue = MemoryAnalyticsQueue(AnalyticsConfig())
        val event1 = AnalyticsEvent("1")
        val event2 = AnalyticsEvent("2")
        queue.enqueue(event1)
        queue.enqueue(event2)

        queue.remove(listOf(event1))
        assertEquals(1, queue.size())
        val remaining = queue.dequeue(1)
        assertEquals("2", remaining[0].name)
    }

    @Test
    fun `clear empties the queue`() = runTest {
        val queue = MemoryAnalyticsQueue(AnalyticsConfig())
        queue.enqueue(AnalyticsEvent("1"))
        queue.clear()
        assertEquals(0, queue.size())
    }
}
