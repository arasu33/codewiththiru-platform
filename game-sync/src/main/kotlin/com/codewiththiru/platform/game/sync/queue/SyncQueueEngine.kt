package com.codewiththiru.platform.game.sync.queue

import kotlinx.serialization.Serializable

/**
 * Represents an atomic sync operation waiting to be executed.
 */
@Serializable
data class SyncRequest(
    val id: String,
    val collection: String,
    val documentId: String,
    val jsonPayload: String,
    val timestampMs: Long,
    var retryCount: Int = 0,
)

/**
 * Engine responsible for managing offline operations and applying exponential backoff.
 */
interface SyncQueueEngine {
    fun enqueue(request: SyncRequest)

    fun peekNext(): SyncRequest?

    fun remove(requestId: String)

    fun markFailed(requestId: String) // Increments retry count and delays

    fun getPendingCount(): Int
}
