package com.codewiththiru.ai.learning

interface DifficultyEngine {
    fun calculateNextDifficulty(currentDifficulty: Int, userPerformance: Double): Int
}
