package com.codewiththiru.governance.performance

interface PerformanceGovernanceManager {
    suspend fun auditPerformance(moduleName: String): PerformanceAudit
}

data class PerformanceBudget(
    val maxStartupTimeMs: Long = 500,
    val maxApkSizeBytes: Long = 10 * 1024 * 1024,
    val maxMemoryUsageMb: Int = 100,
    val maxNetworkRequestsPerMin: Int = 20
)

data class PerformanceRule(
    val id: String,
    val metricName: String,
    val currentValue: Number,
    val thresholdValue: Number,
    val isViolated: Boolean
)

data class PerformanceAudit(
    val moduleName: String,
    val isCertified: Boolean,
    val rules: List<PerformanceRule>
)
