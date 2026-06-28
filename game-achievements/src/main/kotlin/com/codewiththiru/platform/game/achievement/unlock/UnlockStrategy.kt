package com.codewiththiru.platform.game.achievement.unlock

/**
 * Defines how an achievement is unlocked once its conditions are met.
 */
enum class UnlockStrategy {
    AUTOMATIC, // Unlocks immediately when condition is met
    MANUAL, // Requires player to explicitly "claim" it
    DELAYED, // Unlocks after a certain delay (e.g. end of level)
    SCHEDULED, // Unlocks only during a specific time window
}
