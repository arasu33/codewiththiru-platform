package com.codewiththiru.platform.developer.testutils

import java.util.concurrent.atomic.AtomicLong

/**
 * A deterministic clock for unit testing time-based game mechanics (like streaks).
 */
class FakeClock(
    initialTimeMs: Long = 0L,
) {
    private val time = AtomicLong(initialTimeMs)

    var currentTimeMs: Long
        get() = time.get()
        set(value) { time.set(value) }

    fun advanceBy(ms: Long) {
        time.addAndGet(ms)
    }
}
