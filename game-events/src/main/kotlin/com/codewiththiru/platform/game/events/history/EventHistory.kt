package com.codewiththiru.platform.game.events.history

import com.codewiththiru.platform.game.events.api.PlatformEvent
import java.util.concurrent.ConcurrentLinkedDeque

/**
 * A bounded buffer that retains the last N events for debugging, crash reporting,
 * and replay mechanics.
 */
class EventHistory(
    private val maxCapacity: Int = 100,
) {
    private val buffer = ConcurrentLinkedDeque<PlatformEvent>()

    fun record(event: PlatformEvent) {
        if (buffer.size >= maxCapacity) {
            buffer.pollFirst()
        }
        buffer.addLast(event)
    }

    fun getRecentEvents(): List<PlatformEvent> = buffer.toList()
}
