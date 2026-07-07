package com.codewiththiru.platform.game.audio.api

/**
 * Broad categorizations of audio.
 */
enum class AudioCategory {
    MUSIC,
    SOUND_EFFECT,
    VOICE,
    AMBIENT,
    SYSTEM,
    UI,
}

/**
 * Common sound effect archetypes.
 */
enum class SoundEffectType {
    CLICK,
    SELECTION,
    MOVE,
    SUCCESS,
    FAILURE,
    WARNING,
    ERROR,
    REWARD,
    ACHIEVEMENT,
    COUNTDOWN,
    TIMER,
    POPUP,
    NOTIFICATION,
    CUSTOM,
}

/**
 * A request to play an audio track.
 */
data class AudioRequest(
    // Unique identifier for the track/resource
    val id: String,
    val category: AudioCategory,
    val loop: Boolean = false,
    val volumeOverride: Float? = null,
    val fadeInMs: Long = 0,
    val fadeOutMs: Long = 0,
    // Higher number = higher priority in queues
    val priority: Int = 0,
)

/**
 * The response/result of an audio playback request.
 */
enum class AudioResponse {
    PLAYING,
    QUEUED,
    IGNORED_MUTED,
    IGNORED_POLICY,
    ERROR,
}

/**
 * Events for Analytics tracking.
 */
sealed class AudioEvent {
    data class MusicStarted(
        val trackId: String,
    ) : AudioEvent()

    data class MusicStopped(
        val trackId: String,
    ) : AudioEvent()

    data class SoundPlayed(
        val soundId: String,
        val type: SoundEffectType,
    ) : AudioEvent()

    data class VolumeChanged(
        val category: AudioCategory,
        val volume: Float,
    ) : AudioEvent()

    object AudioMuted : AudioEvent()

    object AudioUnmuted : AudioEvent()
}
