package com.codewiththiru.platform.game.statistics.metrics

import com.codewiththiru.platform.game.statistics.api.StatisticsMetric

/**
 * Utility object for evaluating high-level performance scores based on raw metrics.
 */
object PerformanceMetrics {
    /**
     * Move Efficiency = (Minimum Possible Moves / Actual Moves Made) * 100
     * Assumes you know the 'minimumPossibleMoves' for the specific level.
     */
    fun calculateMoveEfficiency(
        actualMoves: Long,
        minMoves: Long,
    ): Double {
        if (actualMoves <= 0) return 100.0
        val ratio = minMoves.toDouble() / actualMoves.toDouble()
        return (ratio * 100).coerceIn(0.0, 100.0)
    }

    /**
     * Win Rate = (Games Won / Games Played) * 100
     */
    fun calculateWinRate(metrics: Map<StatisticsMetric, Double>): Double {
        val played = metrics[StatisticsMetric.GAMES_PLAYED] ?: 0.0
        val won = metrics[StatisticsMetric.GAMES_WON] ?: 0.0
        if (played <= 0) return 0.0
        return (won / played * 100).coerceIn(0.0, 100.0)
    }

    /**
     * Hint Ratio = (Hints Used / Games Played)
     */
    fun calculateHintRatio(metrics: Map<StatisticsMetric, Double>): Double {
        val played = metrics[StatisticsMetric.GAMES_PLAYED] ?: 0.0
        val hints = metrics[StatisticsMetric.HINTS_USED] ?: 0.0
        if (played <= 0) return 0.0
        return hints / played
    }
}
