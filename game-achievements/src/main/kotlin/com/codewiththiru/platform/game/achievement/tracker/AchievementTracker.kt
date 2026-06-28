package com.codewiththiru.platform.game.achievement.tracker

import com.codewiththiru.platform.game.events.GameEvent

/**
 * Listens to game events or statistics changes to trigger evaluation in the AchievementEngine.
 */
interface AchievementTracker {
    /**
     * Processes an incoming game event, which may potentially evaluate conditions and unlock achievements.
     */
    suspend fun onGameEvent(event: GameEvent)
}
