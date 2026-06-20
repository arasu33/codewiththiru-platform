package com.codewiththiru.platform.gamification.level

public class DefaultLevelManager : LevelManager {
    
    // Simple mock formula: level = sqrt(xp / 100)
    // 0 XP -> Lvl 1
    // 400 XP -> Lvl 2
    // 900 XP -> Lvl 3

    override fun getLevelDefinition(level: Int): LevelDefinition {
        return LevelDefinition(
            levelNumber = level,
            requiredXp = (level * level * 100L),
            title = "Level $level",
            rewardCoins = level * 10L
        )
    }

    override fun calculateLevelProgress(totalXp: Long): LevelProgress {
        var currentLevel = 1
        while (totalXp >= getLevelDefinition(currentLevel + 1).requiredXp) {
            currentLevel++
        }
        
        val currentDef = getLevelDefinition(currentLevel)
        val nextDef = getLevelDefinition(currentLevel + 1)
        
        val xpInCurrent = totalXp - currentDef.requiredXp
        val xpNeeded = nextDef.requiredXp - currentDef.requiredXp
        
        return LevelProgress(
            currentLevel = currentLevel,
            currentLevelTitle = currentDef.title,
            currentXpInLevel = xpInCurrent,
            xpRequiredForNextLevel = xpNeeded,
            percentageComplete = xpInCurrent.toFloat() / xpNeeded.toFloat()
        )
    }

    override suspend fun checkLevelUps(userId: String, oldXp: Long, newXp: Long): List<LevelDefinition> {
        val oldLevel = calculateLevelProgress(oldXp).currentLevel
        val newLevel = calculateLevelProgress(newXp).currentLevel
        
        return if (newLevel > oldLevel) {
            (oldLevel + 1..newLevel).map { getLevelDefinition(it) }
        } else {
            emptyList()
        }
    }
}
