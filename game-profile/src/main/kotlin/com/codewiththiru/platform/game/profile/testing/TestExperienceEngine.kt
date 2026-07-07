package com.codewiththiru.platform.game.profile.testing

import com.codewiththiru.platform.game.profile.experience.ExperienceEngine
import com.codewiththiru.platform.game.profile.level.PlayerLevel

class TestExperienceEngine : ExperienceEngine {
    // Simple mock formula: next level requires (currentLevel * 1000) XP
    override fun calculateXpRequiredForLevel(level: Int): Long = (level * 1000L)

    override fun applyXp(
        current: PlayerLevel,
        gainedXp: Long,
    ): Pair<PlayerLevel, Boolean> {
        var totalXp = current.currentXp + gainedXp
        var level = current.currentLevel
        var leveledUp = false

        while (true) {
            val req = calculateXpRequiredForLevel(level)
            if (totalXp >= req) {
                totalXp -= req
                level++
                leveledUp = true
            } else {
                break
            }
        }

        val updated =
            current.copy(
                currentLevel = level,
                currentXp = totalXp,
                xpToNextLevel = calculateXpRequiredForLevel(level),
            )

        return Pair(updated, leveledUp)
    }
}
