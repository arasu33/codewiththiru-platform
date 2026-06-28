package com.codewiththiru.platform.game.challenges.streak

import kotlinx.serialization.Serializable

/**
 * Representation of a user's consecutive challenge completion streak.
 */
@Serializable
data class StreakData(
    val currentStreak: Int = 0,
    val bestStreak: Int = 0,
    val lastCompletionTimeMs: Long = 0L,
    val freezeTokensAvailable: Int = 0,
)

/**
 * Engine responsible for advancing, breaking, or freezing streaks.
 */
interface StreakEngine {
    /**
     * Called when a daily challenge is completed.
     * Returns the updated streak data.
     */
    fun recordCompletion(
        data: StreakData,
        completionTimeMs: Long,
    ): StreakData

    /**
     * Checks if a streak has been broken due to inactivity, attempting to consume
     * a freeze token if available. Returns updated data.
     */
    fun evaluateDecay(
        data: StreakData,
        currentTimeMs: Long,
    ): StreakData
}
