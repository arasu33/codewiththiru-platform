package com.codewiththiru.platform.updates.api

/**
 * Interface for providing the current time. This abstraction is critical for testing
 * cooldowns and staleness behavior.
 */
interface UpdateClock {
    /**
     * Returns the current time in milliseconds.
     */
    fun now(): Long
}
