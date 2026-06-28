package com.codewiththiru.platform.game.audio.policy

import com.codewiththiru.platform.game.audio.api.AudioCategory

/**
 * Governs global rules for audio playback.
 */
interface AudioPolicy {
    /**
     * Returns the maximum number of concurrent sounds allowed for a category.
     */
    fun getMaxConcurrentSounds(category: AudioCategory): Int

    /**
     * Determines if a specific category is globally muted by policy (e.g. Battery Saver active).
     */
    fun isCategoryMutedByPolicy(category: AudioCategory): Boolean
}
