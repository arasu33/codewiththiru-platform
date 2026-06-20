package com.codewiththiru.remoteconfig.analytics

class HealthReporter(
    private val monitor: HealthMonitor,
    private val logger: RemoteConfigLogger
) {
    fun reportHealth() {
        val snapshot = monitor.checkHealth()
        if (!snapshot.isHealthy) {
            logger.logError("Remote Config System is UNHEALTHY! Snapshot: $snapshot")
        } else {
            logger.logEvent("HealthCheck", mapOf("snapshot" to snapshot))
        }
    }
}
