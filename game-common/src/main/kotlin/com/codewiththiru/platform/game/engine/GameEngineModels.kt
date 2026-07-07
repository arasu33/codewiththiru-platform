package com.codewiththiru.platform.game.engine

import kotlinx.serialization.Serializable

/**
 * Lifecycle states of a game session.
 */
enum class GameLifecycle {
    IDLE,
    RUNNING,
    PAUSED,
    COMPLETED,
    GAME_OVER,
}

/**
 * Generic game difficulty options.
 */
enum class GameDifficulty {
    EASY,
    MEDIUM,
    HARD,
    EXPERT,
}

/**
 * Result of a completed game session.
 */
@Serializable
sealed class GameResult {
    @Serializable
    data class Won(
        val finalScore: Long,
        val timeSpentSeconds: Long,
    ) : GameResult()

    @Serializable
    data class Lost(
        val currentScore: Long,
        val movesMade: Int,
    ) : GameResult()

    @Serializable
    data class Forfeit(
        val currentScore: Long,
    ) : GameResult()
}

/**
 * Real-time progression state of a game.
 */
@Serializable
data class GameProgress(
    val level: Int = 1,
    val score: Long = 0,
    val highScore: Long = 0,
    val movesMade: Int = 0,
    val hintsRemaining: Int = 3,
    val undosRemaining: Int = 3,
)

/**
 * Configuration variables for a game mode.
 */
@Serializable
data class GameConfiguration(
    val gameId: String,
    val gameMode: String = "Classic",
    val difficulty: GameDifficulty = GameDifficulty.MEDIUM,
    val initialHints: Int = 3,
    val initialUndos: Int = 3,
    val isTimerEnabled: Boolean = true,
    // For countdown modes
    val maxTimerSeconds: Long? = null,
)

/**
 * Core game state containing progress, configuration, lifecycle, and active timer.
 */
@Serializable
data class GameState(
    val config: GameConfiguration,
    val progress: GameProgress = GameProgress(),
    val lifecycle: GameLifecycle = GameLifecycle.IDLE,
    val elapsedSeconds: Long = 0,
    val extraData: Map<String, String> = emptyMap(),
)

/**
 * Active playthrough session details.
 */
data class GameSession(
    val sessionId: String,
    val gameId: String,
    val startTime: Long = System.currentTimeMillis(),
)
