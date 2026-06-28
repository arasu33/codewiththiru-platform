package com.codewiththiru.platform.game.events.api

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import com.codewiththiru.platform.game.events.priority.EventPriority

/**
 * The base interface for all decoupled platform events.
 * Replaces the legacy GameEvent sealed class.
 */
interface PlatformEvent {
    val timestamp: Long
    val priority: EventPriority

    /**
     * Maps this event to a standardized analytics schema.
     */
    fun toAnalyticsEvent(): AnalyticsEvent
}
