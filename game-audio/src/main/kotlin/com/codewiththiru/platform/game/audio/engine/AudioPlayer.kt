package com.codewiththiru.platform.game.audio.engine

import com.codewiththiru.platform.game.audio.api.AudioRequest

/**
 * The hardware-agnostic contract for audio playback.
 * Platforms (like Android) must implement this interface using MediaPlayer or ExoPlayer.
 */
interface AudioPlayer {
    /**
     * Prepares and starts playback of an audio request.
     */
    suspend fun play(
        request: AudioRequest,
        finalVolume: Float,
    )

    /**
     * Pauses the current track.
     */
    suspend fun pause()

    /**
     * Resumes the paused track.
     */
    suspend fun resume()

    /**
     * Stops playback and releases immediate resources.
     */
    suspend fun stop()

    /**
     * Seeks to a specific position in milliseconds.
     */
    suspend fun seekTo(positionMs: Long)

    /**
     * Dynamically updates the volume of the playing track.
     */
    fun setVolume(volume: Float)

    /**
     * Checks if the player is currently actively playing.
     */
    fun isPlaying(): Boolean
}
