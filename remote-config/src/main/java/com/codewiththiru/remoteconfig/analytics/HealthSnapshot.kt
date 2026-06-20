package com.codewiththiru.remoteconfig.analytics

data class HealthSnapshot(
    val timestamp: Long,
    val isHealthy: Boolean,
    val metrics: RemoteConfigMetrics,
    val activeQuarantines: Int
)
