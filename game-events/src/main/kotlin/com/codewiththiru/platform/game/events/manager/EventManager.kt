package com.codewiththiru.platform.game.events.manager

import com.codewiththiru.platform.game.events.api.PlatformEvent
import com.codewiththiru.platform.game.events.filter.EventFilter
import kotlinx.coroutines.flow.Flow

/**
 * Top level facade combining Publishing and Subscribing capabilities.
 */
interface EventManager {
    /**
     * Publishes an event to the global bus.
     */
    suspend fun publish(event: PlatformEvent)

    /**
     * Subscribes to events, optionally applying a filter.
     * Returns a Flow that the caller can collect (which is lifecycle-aware).
     */
    fun subscribe(filter: EventFilter? = null): Flow<PlatformEvent>

    /**
     * Returns a snapshot of the last N events.
     */
    fun getHistorySnapshot(): List<PlatformEvent>
}
