package com.codewiththiru.platform.game.audio.manager

import com.codewiththiru.platform.game.audio.api.AudioResponse
import com.codewiththiru.platform.game.audio.api.SoundEffectType
import com.codewiththiru.platform.game.audio.profile.AudioConfiguration
import kotlinx.coroutines.flow.StateFlow

/**
 * Top level orchestrator for all game audio configurations.
 */
interface AudioManager {
    /**
     * Exposes the reactive state of the current audio configuration.
     */
    val configuration: StateFlow<AudioConfiguration>

    /**
     * Updates the master volume (0.0f to 1.0f).
     */
    fun setMasterVolume(volume: Float)

    /**
     * Globally mutes or unmutes all audio.
     */
    fun setMuted(muted: Boolean)
}

/**
 * Dedicated manager for long-playing, looping background tracks.
 */
interface MusicManager {
    /**
     * Plays a music track, optionally fading it in.
     */
    suspend fun playMusic(
        trackId: String,
        loop: Boolean = true,
        fadeMs: Long = 1000,
    ): AudioResponse

    /**
     * Pauses the current music track.
     */
    suspend fun pauseMusic(fadeMs: Long = 500)

    /**
     * Resumes the paused music track.
     */
    suspend fun resumeMusic(fadeMs: Long = 500)

    /**
     * Stops the music track entirely.
     */
    suspend fun stopMusic(fadeMs: Long = 1000)

    fun setMusicVolume(volume: Float)
}

/**
 * Dedicated manager for short, transient sound effects.
 */
interface SoundEffectManager {
    /**
     * Plays a one-shot sound effect.
     */
    suspend fun playEffect(
        type: SoundEffectType,
        resourceId: String,
    ): AudioResponse

    /**
     * Preloads a sound effect into memory for zero-latency playback later.
     */
    suspend fun preloadEffect(resourceId: String)

    fun setEffectsVolume(volume: Float)
}
