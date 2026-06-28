package com.codewiththiru.platform.game.events

import com.codewiththiru.platform.game.events.filter.EventFilter
import com.codewiththiru.platform.game.events.history.EventHistory
import com.codewiththiru.platform.game.events.priority.EventPriority
import com.codewiththiru.platform.game.events.testing.DummyEvent
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EventTests {
    @Test
    fun `test event history bounds`() {
        val history = EventHistory(maxCapacity = 3)
        history.record(DummyEvent(payload = "1"))
        history.record(DummyEvent(payload = "2"))
        history.record(DummyEvent(payload = "3"))

        assertEquals(3, history.getRecentEvents().size)

        history.record(DummyEvent(payload = "4")) // Should evict 1

        val recent = history.getRecentEvents()
        assertEquals(3, recent.size)
        assertEquals("2", (recent[0] as DummyEvent).payload)
        assertEquals("4", (recent[2] as DummyEvent).payload)
    }

    @Test
    fun `test custom filter execution`() {
        val criticalFilter = EventFilter { it.priority == EventPriority.CRITICAL }

        val normalEvent = DummyEvent(priority = EventPriority.NORMAL)
        val criticalEvent = DummyEvent(priority = EventPriority.CRITICAL)

        assertFalse(criticalFilter.shouldProcess(normalEvent))
        assertTrue(criticalFilter.shouldProcess(criticalEvent))
    }
}
