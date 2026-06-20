package com.codewiththiru.platform.observability.alerting

public enum class AlertSeverity {
    INFO, WARNING, CRITICAL, FATAL
}

public data class AlertRule(
    val id: String,
    val metricName: String,
    val threshold: Long,
    val severity: AlertSeverity
)

public data class Incident(
    val id: String,
    val title: String,
    val severity: AlertSeverity,
    val startTime: Long,
    var resolveTime: Long? = null,
    val relatedAlertIds: List<String>
)

public interface AlertManager {
    public fun registerRule(rule: AlertRule)
    public fun triggerAlert(ruleId: String, currentVal: Long)
}

public interface IncidentManager {
    public fun declareIncident(title: String, severity: AlertSeverity): Incident
    public fun resolveIncident(incidentId: String)
    public fun getActiveIncidents(): List<Incident>
}
