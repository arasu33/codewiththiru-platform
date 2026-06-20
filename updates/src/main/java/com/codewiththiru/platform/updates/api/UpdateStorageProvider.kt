package com.codewiththiru.platform.updates.api

/**
 * Persists and retrieves update-related state to manage cooldowns and prompt logic.
 */
interface UpdateStorageProvider {
    /**
     * Timestamp in milliseconds when the user was last prompted for a flexible update.
     */
    suspend fun getLastPromptDate(): Long

    /**
     * Sets the timestamp when the user was last prompted for a flexible update.
     */
    suspend fun setLastPromptDate(timestamp: Long)

    /**
     * Timestamp in milliseconds when the user explicitly deferred/cancelled the update.
     */
    suspend fun getLastDeferDate(): Long

    /**
     * Sets the timestamp when the user deferred the update.
     */
    suspend fun setLastDeferDate(timestamp: Long)

    /**
     * Returns the last version code for which the "What's New" dialog was shown.
     */
    suspend fun getLastShownReleaseNotesVersion(): Int

    /**
     * Sets the last version code for which the "What's New" dialog was shown.
     */
    suspend fun setLastShownReleaseNotesVersion(versionCode: Int)
}
