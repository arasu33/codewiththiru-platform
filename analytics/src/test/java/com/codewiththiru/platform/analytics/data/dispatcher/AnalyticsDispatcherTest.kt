package com.codewiththiru.platform.analytics.data.dispatcher

import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.config.AnalyticsBatchConfig
import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.data.queue.MemoryAnalyticsQueue
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import android.util.Log
import io.mockk.mockkStatic
import io.mockk.every
import org.junit.Before

@OptIn(ExperimentalCoroutinesApi::class)
class AnalyticsDispatcherTest {

    class MockProvider : AnalyticsProvider {
        val events = mutableListOf<AnalyticsEvent>()
        var flushCalled = 0
        override suspend fun trackEvent(event: AnalyticsEvent) { events.add(event) }
        override suspend fun trackScreen(screen: AnalyticsScreen) { /* no-op */ }
        override suspend fun setUserProperty(property: AnalyticsUserProperty) { /* no-op */ }
        override suspend fun flush() { flushCalled++ }
    }

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.d(any(), any()) } returns 0
        every { Log.e(any(), any(), any()) } returns 0
    }

    @Test
    fun `flush dispatches all queued events in batches and removes them`() = runTest {
        val queue = MemoryAnalyticsQueue(AnalyticsConfig())
        for (i in 1..5) {
            queue.enqueue(AnalyticsEvent("event$i"))
        }

        val provider = MockProvider()
        val config = AnalyticsBatchConfig(batchSize = 2, flushIntervalMinutes = 15)
        
        val dispatcher = AnalyticsDispatcher(queue, provider, config, this)

        dispatcher.flush()

        assertEquals(5, provider.events.size)
        // 5 items, batch size 2 -> 3 batches (2, 2, 1) -> flush() called 3 times
        assertEquals(3, provider.flushCalled)
        assertEquals(0, queue.size())
    }

    @Test
    fun `periodic dispatch triggers after interval`() = runTest {
        val testDispatcher = StandardTestDispatcher(testScheduler)
        val testScope = TestScope(testDispatcher)

        val queue = MemoryAnalyticsQueue(AnalyticsConfig())
        queue.enqueue(AnalyticsEvent("event1"))

        val provider = MockProvider()
        val config = AnalyticsBatchConfig(batchSize = 10, flushIntervalMinutes = 15)
        
        val dispatcher = AnalyticsDispatcher(queue, provider, config, testScope)
        dispatcher.start()

        // Fast forward time just before the interval
        testScope.advanceTimeBy(14 * 60 * 1000L)
        assertEquals(0, provider.events.size)

        // Fast forward past the interval
        testScope.advanceTimeBy(1 * 60 * 1000L + 10)
        assertEquals(1, provider.events.size)
        assertEquals(0, queue.size())

        dispatcher.stop()
    }
}
