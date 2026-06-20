package com.codewiththiru.platform.observability.api

/**
 * Base representation for operation results in the observability platform.
 */
public sealed class ObservabilityResult<out T> {
    public data class Success<T>(val data: T) : ObservabilityResult<T>()
    public data class Error(val throwable: Throwable, val contextInfo: String? = null) : ObservabilityResult<Nothing>()
}

/**
 * Represents the persistent state of the observability engine (e.g. pending logs, unsynced crashes).
 */
public data class ObservabilityState(
    val pendingCrashReports: Int,
    val pendingLogCount: Int,
    val lastSyncTimestamp: Long
)

/**
 * Captures environmental conditions for observability contexts.
 */
public data class ObservabilityEnvironment(
    val deviceModel: String,
    val osVersion: String,
    val appVersion: String,
    val isRooted: Boolean,
    val batteryLevel: Float,
    val networkType: String
)
