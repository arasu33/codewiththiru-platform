package com.codewiththiru.platform.game.sync.queue

import java.util.concurrent.ConcurrentHashMap
import kotlin.math.pow

class DefaultSyncQueueEngine : SyncQueueEngine {
    private val queue = ConcurrentHashMap<String, SyncRequest>()
    private val failureTimestamps = ConcurrentHashMap<String, Long>()

    override fun enqueue(request: SyncRequest) {
        queue[request.id] = request
    }

    override fun peekNext(): SyncRequest? {
        val currentTime = System.currentTimeMillis()

        // Find the oldest request that is ready to be processed based on backoff
        return queue.values
            .filter { isReady(it, currentTime) }
            .minByOrNull { it.timestampMs }
    }

    override fun remove(requestId: String) {
        queue.remove(requestId)
        failureTimestamps.remove(requestId)
    }

    override fun markFailed(requestId: String) {
        val request = queue[requestId]
        if (request != null) {
            request.retryCount += 1
            failureTimestamps[requestId] = System.currentTimeMillis()
        }
    }

    override fun getPendingCount(): Int {
        return queue.size
    }

    private fun isReady(
        request: SyncRequest,
        currentTimeMs: Long,
    ): Boolean {
        if (request.retryCount == 0) return true

        val lastFailureTime = failureTimestamps[request.id] ?: return true

        // Exponential backoff: (2^retryCount) * 1000 ms
        val backoffDelayMs = (2.0.pow(request.retryCount.toDouble()) * 1000).toLong()

        return currentTimeMs >= (lastFailureTime + backoffDelayMs)
    }
}
