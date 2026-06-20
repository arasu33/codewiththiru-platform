package com.codewiththiru.ai.platform.analytics

interface InsightGenerator {
    suspend fun generateInsights(dataPayload: String): String
}

interface TrendDetector {
    suspend fun detectTrends(metricSeries: List<Float>): String
}

interface ForecastEngine {
    suspend fun forecastRevenue(historicalData: List<Float>): List<Float>
    suspend fun forecastRetention(historicalData: List<Float>): List<Float>
}

class AIAnalyticsEngine(
    private val insightGenerator: InsightGenerator,
    private val trendDetector: TrendDetector,
    private val forecastEngine: ForecastEngine
)
