package com.codewiththiru.ai.platform.gaming

interface GameCoach {
    suspend fun getStrategyAdvice(gameState: String): String
}

interface SudokuCoach : GameCoach
interface ChessCoach : GameCoach

interface HintEngine {
    suspend fun generateHint(gameState: String): String
}
