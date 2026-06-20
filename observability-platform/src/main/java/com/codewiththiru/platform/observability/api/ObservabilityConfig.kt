package com.codewiththiru.platform.observability.api

/**
 * Global configuration for the observability SDK.
 */
public data class ObservabilityConfig(
    val isEnabled: Boolean = true,
    val captureCrashes: Boolean = true,
    val captureANRs: Boolean = true,
    val capturePerformanceMetrics: Boolean = true,
    val enableNetworkTracing: Boolean = true,
    val maxLogBufferSize: Int = 500,
    val uploadOnWifiOnly: Boolean = false,
    val dataRetentionDays: Int = 7
)
