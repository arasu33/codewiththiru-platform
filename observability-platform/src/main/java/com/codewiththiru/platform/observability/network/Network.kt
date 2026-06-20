package com.codewiththiru.platform.observability.network

public data class NetworkPolicy(
    val trackHeaders: Boolean = false,
    val trackPayloadSize: Boolean = true,
    val ignoredDomains: List<String> = emptyList()
)

public data class RequestMetric(
    val url: String,
    val method: String,
    val requestSizeBytes: Long
)

public data class ResponseMetric(
    val statusCode: Int,
    val responseSizeBytes: Long,
    val latencyMs: Long,
    val errorClass: String? = null
)

public data class ApiTrace(
    val id: String,
    val request: RequestMetric,
    val response: ResponseMetric?,
    val timestamp: Long
)

public interface NetworkMonitor {
    public fun logTrace(trace: ApiTrace)
    public fun isConnected(): Boolean
    public fun getConnectionType(): String
}
