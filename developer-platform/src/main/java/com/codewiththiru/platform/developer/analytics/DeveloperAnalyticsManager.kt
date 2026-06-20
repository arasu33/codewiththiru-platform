package com.codewiththiru.platform.developer.analytics

import com.codewiththiru.platform.developer.productivity.ProductivityMetric

interface DeveloperAnalyticsManager {
    suspend fun trackMetric(metric: DeveloperMetric)
    suspend fun getProductivity(developerId: String): ProductivityMetric
    suspend fun getInsights(): List<EngineeringInsight>
}

class DefaultDeveloperAnalyticsManager : DeveloperAnalyticsManager {
    override suspend fun trackMetric(metric: DeveloperMetric) {
        // Log metric
    }

    override suspend fun getProductivity(developerId: String): ProductivityMetric {
        return ProductivityMetric(developerId, 500, 5, 2)
    }

    override suspend fun getInsights(): List<EngineeringInsight> {
        return listOf(EngineeringInsight("Test Coverage Dropping", "Module :core coverage dropped by 2%.", 1))
    }
}
