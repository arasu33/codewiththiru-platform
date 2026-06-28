package com.codewiththiru.platform.game.audio.profile

import kotlinx.serialization.Serializable

/**
 * Defines the volume limits and mute statuses.
 */
@Serializable
data class AudioConfiguration(
    val masterVolume: Float = 1.0f,
    val musicVolume: Float = 1.0f,
    val effectsVolume: Float = 1.0f,
    val voiceVolume: Float = 1.0f,
    val isMuted: Boolean = false,
    val isReducedAudioMode: Boolean = false,
) {
    /**
     * Helper to compute the final combined volume.
     */
    fun computeFinalVolume(categoryVolume: Float): Float {
        if (isMuted) return 0f
        return (masterVolume * categoryVolume).coerceIn(0f, 1f)
    }
}

/**
 * A predefined preset of configurations (e.g. Accessibility presets).
 */
@Serializable
data class AudioProfile(
    val id: String,
    val name: String,
    val configuration: AudioConfiguration,
)
