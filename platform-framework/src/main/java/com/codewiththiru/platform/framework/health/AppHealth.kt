package com.codewiththiru.platform.framework.health

data class HealthMetrics(
    val crashRate: Float,
    val anrRate: Float,
    val revenueDelta: Float,
    val d1Retention: Float
)

interface AppHealthManager {
    fun getOverallHealth(appId: String): HealthMetrics
}

interface HealthDashboard {
    fun displayDashboard(metrics: HealthMetrics)
}
