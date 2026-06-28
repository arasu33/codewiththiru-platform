package com.codewiththiru.platform.game.sync.merge

/**
 * Handles merging distinct fields of a local JSON object and a remote JSON object.
 */
interface MergeEngine {
    /**
     * Attempts to non-destructively merge keys from both payloads.
     */
    fun deepMerge(
        localJson: String,
        remoteJson: String,
    ): String
}
