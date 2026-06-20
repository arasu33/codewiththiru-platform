package com.codewiththiru.ai.platform.operations

interface IncidentAnalyzer {
    suspend fun analyzeCrash(stacktrace: String): String
}

interface HealthPredictor {
    suspend fun predictFailureRisk(healthMetrics: Map<String, Float>): Float
}

class AIOperationsManager(
    private val incidentAnalyzer: IncidentAnalyzer,
    private val healthPredictor: HealthPredictor
)
