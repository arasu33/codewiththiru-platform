package com.codewiththiru.ai.analytics

data class AiMetrics(
    val totalRequests: Int,
    val successRate: Double,
    val averageLatencyMs: Long
)
