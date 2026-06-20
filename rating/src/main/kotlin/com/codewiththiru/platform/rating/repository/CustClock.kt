package com.codewiththiru.platform.rating.repository

/**
 * Abstraction for system time to enable testable cooldown logic.
 */
interface CustClock {
    fun currentTimeMillis(): Long
}

class SystemClock : CustClock {
    override fun currentTimeMillis(): Long = System.currentTimeMillis()
}
