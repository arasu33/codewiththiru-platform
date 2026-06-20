package com.codewiththiru.platform.growth.funnel

interface ConversionTracker {
    suspend fun trackConversion(event: ConversionEvent)
    suspend fun getGoalProgress(goalId: String): Double
}

class DefaultConversionTracker : ConversionTracker {
    private val events = mutableListOf<ConversionEvent>()

    override suspend fun trackConversion(event: ConversionEvent) {
        events.add(event)
    }

    override suspend fun getGoalProgress(goalId: String): Double {
        // Compute against goals based on events
        return 1.0
    }
}
