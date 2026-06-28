package com.codewiththiru.platform.game.audio.cache

/**
 * Interface to preload and cache audio resources in memory or disk.
 */
interface AudioCache {
    /**
     * Preloads an audio file into the cache.
     */
    suspend fun preload(resourceId: String)

    /**
     * Checks if a resource is currently cached and ready for instant playback.
     */
    fun isCached(resourceId: String): Boolean

    /**
     * Evicts a resource from the cache to free memory.
     */
    fun evict(resourceId: String)

    /**
     * Clears the entire cache.
     */
    fun clearAll()
}
