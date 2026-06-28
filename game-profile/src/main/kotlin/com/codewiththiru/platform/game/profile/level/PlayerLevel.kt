package com.codewiththiru.platform.game.profile.level

import kotlinx.serialization.Serializable

/**
 * Describes the user's current progress through the leveling system.
 */
@Serializable
data class PlayerLevel(
    val currentLevel: Int = 1,
    val rankName: String = "Novice",
    val currentXp: Long = 0L,
    val xpToNextLevel: Long = 1000L,
    val prestigeCount: Int = 0,
)
