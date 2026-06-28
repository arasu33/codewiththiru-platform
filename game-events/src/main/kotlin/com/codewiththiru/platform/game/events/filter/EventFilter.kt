package com.codewiththiru.platform.game.events.filter

import com.codewiththiru.platform.game.events.api.PlatformEvent

/**
 * Interface allowing subscribers to conditionally ignore events before processing them.
 */
fun interface EventFilter {
    fun shouldProcess(event: PlatformEvent): Boolean
}

class AcceptAllFilter : EventFilter {
    override fun shouldProcess(event: PlatformEvent) = true
}
