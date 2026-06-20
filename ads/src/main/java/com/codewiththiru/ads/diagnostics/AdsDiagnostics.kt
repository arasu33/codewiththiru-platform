package com.codewiththiru.ads.diagnostics

/**
 * Diagnostics model collecting the current health and status of the Ads integration.
 */
data class AdsDiagnostics(
    val providerStatus: Map<String, String> = emptyMap(),
    val consentStatus: String = "Unknown",
    val frequencyStatus: Map<String, String> = emptyMap(),
    val loadFailures: Int = 0,
    val fillRatePercent: Float = 0f,
    val averageLatencyMs: Long = 0L,
    val totalRevenueMicros: Long = 0L,
    val networkState: String = "Unknown"
)

/**
 * Interface for gathering diagnostics.
 */
interface AdsDiagnosticsCollector {
    fun getDiagnostics(): AdsDiagnostics
}
