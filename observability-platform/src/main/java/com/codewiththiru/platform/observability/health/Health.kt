package com.codewiththiru.platform.observability.health

public enum class HealthStatus {
    HEALTHY, DEGRADED, DOWN, UNKNOWN
}

public data class HealthIndicator(
    val name: String,
    val status: HealthStatus,
    val details: Map<String, String> = emptyMap(),
    val lastCheckTime: Long
)

public interface HealthCheck {
    public val name: String
    public suspend fun check(): HealthIndicator
}

public interface HealthMonitor {
    public fun registerCheck(check: HealthCheck)
    public suspend fun runAllChecks(): List<HealthIndicator>
    public fun observeSystemHealth(): kotlinx.coroutines.flow.Flow<HealthStatus>
}
