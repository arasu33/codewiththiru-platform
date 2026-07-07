package com.codewiththiru.platform.developer.testutils

/**
 * A deterministic clock for unit testing time-based game mechanics (like streaks).
 */
class FakeClock(
    var currentTimeMs: Long = 0L,
) {
    fun advanceBy(ms: Long) {
        currentTimeMs += ms
    }
}
