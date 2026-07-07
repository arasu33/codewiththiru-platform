package com.codewiththiru.platform.game.haptics.api

/**
 * Categorization of haptic feedback types.
 */
enum class HapticPatternType {
    SELECTION,
    SUCCESS,
    FAILURE,
    WARNING,
    ERROR,
    MOVE,
    CLICK,
    LONG_PRESS,
    DRAG,
    DROP,
    REWARD,
    ACHIEVEMENT,
    LEVEL_COMPLETE,
    GAME_OVER,
    COUNTDOWN,
    NOTIFICATION,
    TUTORIAL,
    PREMIUM,
    CUSTOM,
}

/**
 * Defines a request for the haptic engine to process.
 */
data class HapticRequest(
    val type: HapticPatternType,
    val intensityScale: Float = 1.0f,
    val priority: Int = 0,
    val customPatternId: String? = null,
)

/**
 * Result of a haptic request.
 */
enum class HapticResponse {
    PLAYED,
    QUEUED,
    IGNORED_POLICY,
    IGNORED_CAPABILITY,
    ERROR,
}

/**
 * Events for Analytics tracking.
 */
sealed class HapticEvent {
    data class HapticPlayed(
        val type: HapticPatternType,
    ) : HapticEvent()

    data class HapticSuppressed(
        val reason: String,
    ) : HapticEvent()

    data class ProfileChanged(
        val profileId: String,
    ) : HapticEvent()

    data class CapabilityDetected(
        val capabilities: Map<String, Boolean>,
    ) : HapticEvent()

    data class PatternQueued(
        val type: HapticPatternType,
    ) : HapticEvent()

    data class PatternCancelled(
        val type: HapticPatternType,
    ) : HapticEvent()
}
