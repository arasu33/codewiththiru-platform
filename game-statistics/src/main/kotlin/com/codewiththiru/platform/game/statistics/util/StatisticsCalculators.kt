package com.codewiththiru.platform.game.statistics.util

import com.codewiththiru.platform.game.statistics.api.StatisticsMetric

/**
 * Reusable calculators for trend analysis and percentiles.
 */
interface StatisticsCalculator {
    /**
     * Calculates the moving average of a specific metric over a set number of historical data points.
     */
    fun calculateMovingAverage(
        dataPoints: List<Double>,
        windowSize: Int,
    ): Double

    /**
     * Finds the given percentile (e.g., 90th percentile) in the data points.
     */
    fun calculatePercentile(
        dataPoints: List<Double>,
        percentile: Double,
    ): Double

    /**
     * Detects if the recent trend of a metric is improving, regressing, or stable.
     */
    fun detectTrend(dataPoints: List<Double>): TrendDirection
}

enum class TrendDirection {
    IMPROVING,
    REGRESSING,
    STABLE,
    INSUFFICIENT_DATA,
}

/**
 * Aggregates multiple statistical profiles into a single summary.
 */
interface StatisticsAggregator {
    /**
     * Aggregates multiple daily profiles into a single weekly or monthly profile.
     */
    fun aggregate(profiles: List<Map<StatisticsMetric, Double>>): Map<StatisticsMetric, Double>
}
