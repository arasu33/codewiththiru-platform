package com.codewiththiru.platform.game.sync.provider

/**
 * Abstraction for any cloud synchronization backend (Firebase, Supabase, REST).
 */
interface SyncProvider {
    /**
     * @return true if the provider is currently reachable and authenticated.
     */
    suspend fun isAvailable(): Boolean

    /**
     * Uploads a serialized payload to a specific remote collection/path.
     */
    suspend fun pushData(
        collection: String,
        documentId: String,
        jsonPayload: String,
    ): Boolean

    /**
     * Pulls a serialized payload from a specific remote collection/path.
     * Returns null if not found.
     */
    suspend fun pullData(
        collection: String,
        documentId: String,
    ): String?

    /**
     * Returns the remote timestamp or version hash of a document.
     */
    suspend fun getRemoteVersion(
        collection: String,
        documentId: String,
    ): Long?
}
