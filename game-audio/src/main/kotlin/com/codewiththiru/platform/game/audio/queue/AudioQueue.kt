package com.codewiththiru.platform.game.audio.queue

import com.codewiththiru.platform.game.audio.api.AudioRequest

/**
 * Handles ordering and queuing of audio requests.
 */
interface AudioQueue {
    /**
     * Enqueues a request.
     */
    fun enqueue(request: AudioRequest)

    /**
     * Dequeues the next appropriate request (based on priority or FIFO).
     * Returns null if empty.
     */
    fun dequeue(): AudioRequest?

    /**
     * Clears all pending requests.
     */
    fun clear()

    /**
     * Peeks at the next request without removing it.
     */
    fun peek(): AudioRequest?

    /**
     * Returns true if the queue is empty.
     */
    fun isEmpty(): Boolean
}
