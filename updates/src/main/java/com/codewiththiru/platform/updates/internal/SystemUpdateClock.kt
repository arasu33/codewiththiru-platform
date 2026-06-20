package com.codewiththiru.platform.updates.internal

import com.codewiththiru.platform.updates.api.UpdateClock

/**
 * Standard implementation of UpdateClock that returns the system's current time.
 */
class SystemUpdateClock : UpdateClock {
    override fun now(): Long = System.currentTimeMillis()
}
