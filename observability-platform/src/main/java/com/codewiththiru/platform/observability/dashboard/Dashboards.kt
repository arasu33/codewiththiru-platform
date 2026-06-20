package com.codewiththiru.platform.observability.dashboard

public enum class DashboardType {
    EXECUTIVE, DEVELOPER, CRASH, PERFORMANCE, SYSTEM
}

public data class ObservabilityReport(
    val id: String,
    val generatedAt: Long,
    val summaryData: Map<String, String>
)

public data class ObservabilityDashboard(
    val type: DashboardType,
    val title: String,
    val widgets: List<String>
)

public interface MetricsExporter {
    public suspend fun exportMetrics(): String
}

public interface DashboardProvider {
    public fun getDashboard(type: DashboardType): ObservabilityDashboard
    public suspend fun generateReport(): ObservabilityReport
}
