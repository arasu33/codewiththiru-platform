package com.codewiththiru.platform.game.haptics.session

/**
 * Manages the queueing, flushing, and interruption of haptic events.
 */
interface HapticSession {
    suspend fun start()

    suspend fun pause()

    suspend fun resume()

    suspend fun stop()

    /**
     * Flushes any pending haptic requests in the queue immediately.
     */
    fun flush()
}
