package com.codewiththiru.platform.developer.testutils


/**
 * A deterministic clock for unit testing time-based game mechanics (like streaks).
 */
class FakeClock(
    initialTimeMs: Long = 0L,
) {
    private val lock = Any()
    private var _time: Long = initialTimeMs

    var currentTimeMs: Long
        get() = synchronized(lock) { _time }
        set(value) {
            synchronized(lock) { _time = value }
        }

    fun advanceBy(ms: Long) {
        synchronized(lock) {
            _time += ms
        }
    }
}
