package com.codewiththiru.security.threat

data class ThreatEvent(
    val id: String,
    val type: String,
    val severity: ThreatSeverity,
    val timestamp: Long
)
