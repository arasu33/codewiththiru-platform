package com.codewiththiru.platform.game.statistics.tracker

import com.codewiththiru.platform.game.events.GameEvent

/**
 * Listens to GameEvents and translates them into statistic increments.
 */
interface StatisticsTracker {
    /**
     * Should be called whenever a GameEvent is fired by the Game Manager.
     */
    suspend fun onGameEvent(event: GameEvent)
}
