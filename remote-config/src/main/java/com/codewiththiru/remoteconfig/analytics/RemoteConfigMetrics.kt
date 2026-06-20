package com.codewiththiru.remoteconfig.analytics

data class RemoteConfigMetrics(
    var providerAvailability: Double = 1.0,
    var providerLatencyMs: Long = 0,
    var cacheHitRate: Double = 0.0,
    var cacheMissRate: Double = 0.0,
    var flagEvaluationCount: Long = 0,
    var experimentAssignmentCount: Long = 0,
    var securityViolations: Long = 0
)
