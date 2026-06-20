package com.codewiththiru.remoteconfig.analytics

class HealthMonitor(
    private val metrics: RemoteConfigMetrics
) {
    fun checkHealth(): HealthSnapshot {
        val isHealthy = metrics.providerAvailability > 0.8 && metrics.securityViolations < 10
        return HealthSnapshot(
            timestamp = System.currentTimeMillis(),
            isHealthy = isHealthy,
            metrics = metrics.copy(),
            activeQuarantines = 0
        )
    }
    
    fun recordProviderLatency(latencyMs: Long) {
        metrics.providerLatencyMs = latencyMs
    }
    
    fun recordProviderAvailability(isAvailable: Boolean) {
        val newVal = if (isAvailable) 1.0 else 0.0
        metrics.providerAvailability = (metrics.providerAvailability * 0.9) + (newVal * 0.1)
    }
    
    fun recordSecurityViolation() {
        metrics.securityViolations++
    }
}
