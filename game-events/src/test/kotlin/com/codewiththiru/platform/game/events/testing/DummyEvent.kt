package com.codewiththiru.platform.game.events.testing

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.game.events.api.PlatformEvent
import com.codewiththiru.platform.game.events.priority.EventPriority

data class DummyEvent(
    override val timestamp: Long = System.currentTimeMillis(),
    override val priority: EventPriority = EventPriority.NORMAL,
    val payload: String = "",
) : PlatformEvent {
    override fun toAnalyticsEvent(): AnalyticsEvent {
        return AnalyticsEvent("dummy_event", mapOf("payload" to payload), timestamp)
    }
}
