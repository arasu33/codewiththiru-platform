package com.codewiththiru.platform.game.profile.experience

import com.codewiththiru.platform.game.profile.level.PlayerLevel

/**
 * Calculates XP thresholds and handles overflow mechanics when leveling up.
 */
interface ExperienceEngine {
    /**
     * Determines how much XP is required to progress from the given level to the next.
     */
    fun calculateXpRequiredForLevel(level: Int): Long

    /**
     * Applies an XP gain to a PlayerLevel, handling potential multi-level jumps and overflow.
     * Returns a Pair: the updated PlayerLevel, and a boolean indicating if a level-up occurred.
     */
    fun applyXp(
        current: PlayerLevel,
        gainedXp: Long,
    ): Pair<PlayerLevel, Boolean>
}
