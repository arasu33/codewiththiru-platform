package com.codewiththiru.platform.analytics.data.queue

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.io.File
import kotlinx.coroutines.test.runTest

@RunWith(RobolectricTestRunner::class)
class DataStoreAnalyticsQueueTest {
    private lateinit var context: Context
    private lateinit var queue: DataStoreAnalyticsQueue

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        // Ensure clean state
        File(context.filesDir, "datastore").deleteRecursively()
        queue = DataStoreAnalyticsQueue(context, AnalyticsConfig(maxQueuedEvents = 5))
        kotlinx.coroutines.runBlocking {
            queue.clear()
        }
    }

    @Test
    fun `enqueue persists events across instances`() =
        runTest {
            queue.enqueue(AnalyticsEvent("event1"))
            queue.enqueue(AnalyticsEvent("event2"))

            // Create new instance to verify persistence
            val newQueue = DataStoreAnalyticsQueue(context, AnalyticsConfig())
            val events = newQueue.peek(5)

            assertEquals(2, events.size)
            assertEquals("event1", events[0].name)
            assertEquals("event2", events[1].name)
        }

    @Test
    fun `enforces max queued events limit`() =
        runTest {
            for (i in 1..10) {
                queue.enqueue(AnalyticsEvent("event$i"))
            }

            // Should only keep last 5
            val events = queue.peek(10)
            assertEquals(5, events.size)
            assertEquals("event6", events[0].name)
            assertEquals("event10", events[4].name)
        }

    @Test
    fun `remove drops specific events`() =
        runTest {
            val event1 = AnalyticsEvent("event1")
            val event2 = AnalyticsEvent("event2")
            queue.enqueue(event1)
            queue.enqueue(event2)

            queue.remove(listOf(event1))

            val events = queue.peek(5)
            assertEquals(1, events.size)
            assertEquals("event2", events[0].name)
        }

    @Test
    fun `clear drops all events`() =
        runTest {
            queue.enqueue(AnalyticsEvent("event1"))
            queue.clear()

            val events = queue.peek(5)
            assertEquals(0, events.size)
        }
}
