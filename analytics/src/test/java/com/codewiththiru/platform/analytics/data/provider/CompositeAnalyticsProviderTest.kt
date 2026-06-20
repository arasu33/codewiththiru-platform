package com.codewiththiru.platform.analytics.data.provider

import com.codewiththiru.platform.analytics.api.AnalyticsProvider
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.analytics.domain.event.AnalyticsScreen
import com.codewiththiru.platform.analytics.domain.event.AnalyticsUserProperty
import io.mockk.mockkStatic
import io.mockk.every
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Before
import org.junit.Assert.assertEquals
import android.util.Log

class CompositeAnalyticsProviderTest {

    @Before
    fun setup() {
        mockkStatic(Log::class)
        every { Log.e(any(), any(), any()) } returns 0
        every { Log.d(any(), any()) } returns 0
    }

    class FakeAnalyticsProvider(val shouldThrow: Boolean = false) : AnalyticsProvider {
        val events = mutableListOf<AnalyticsEvent>()
        val screens = mutableListOf<AnalyticsScreen>()
        val properties = mutableListOf<AnalyticsUserProperty>()
        var flushCount = 0

        override suspend fun trackEvent(event: AnalyticsEvent) {
            if (shouldThrow) error("Simulated failure")
            events.add(event)
        }

        override suspend fun trackScreen(screen: AnalyticsScreen) {
            if (shouldThrow) error("Simulated failure")
            screens.add(screen)
        }

        override suspend fun setUserProperty(property: AnalyticsUserProperty) {
            if (shouldThrow) error("Simulated failure")
            properties.add(property)
        }

        override suspend fun flush() {
            if (shouldThrow) error("Simulated failure")
            flushCount++
        }
    }

    @Test
    fun `composite fans out events to all providers`() = runTest {
        val p1 = FakeAnalyticsProvider()
        val p2 = FakeAnalyticsProvider()
        val composite = CompositeAnalyticsProvider(listOf(p1, p2))

        val event = AnalyticsEvent("test_event")
        composite.trackEvent(event)

        assertEquals(1, p1.events.size)
        assertEquals(1, p2.events.size)
    }

    @Test
    fun `failure of one provider does not block others`() = runTest {
        val p1 = FakeAnalyticsProvider(shouldThrow = true)
        val p2 = FakeAnalyticsProvider()
        val composite = CompositeAnalyticsProvider(listOf(p1, p2))

        val event = AnalyticsEvent("test_event")
        composite.trackEvent(event)

        assertEquals(0, p1.events.size)
        assertEquals(1, p2.events.size)
    }

    @Test
    fun `trackScreen fans out`() = runTest {
        val p1 = FakeAnalyticsProvider()
        val composite = CompositeAnalyticsProvider(listOf(p1))
        
        composite.trackScreen(AnalyticsScreen("Home"))
        assertEquals(1, p1.screens.size)
    }

    @Test
    fun `setUserProperty fans out`() = runTest {
        val p1 = FakeAnalyticsProvider()
        val composite = CompositeAnalyticsProvider(listOf(p1))
        
        composite.setUserProperty(AnalyticsUserProperty("tier", "pro"))
        assertEquals(1, p1.properties.size)
    }

    @Test
    fun `flush fans out`() = runTest {
        val p1 = FakeAnalyticsProvider()
        val composite = CompositeAnalyticsProvider(listOf(p1))
        
        composite.flush()
        assertEquals(1, p1.flushCount)
    }
}
