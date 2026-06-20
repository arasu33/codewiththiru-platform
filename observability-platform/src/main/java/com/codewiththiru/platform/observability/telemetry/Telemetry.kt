package com.codewiththiru.platform.observability.telemetry

public data class TraceContext(
    val traceId: String,
    val parentSpanId: String? = null,
    val correlationId: String? = null
)

public data class Span(
    val id: String,
    val name: String,
    val context: TraceContext,
    val startTime: Long,
    var endTime: Long? = null,
    val tags: MutableMap<String, String> = mutableMapOf()
)

public data class Trace(
    val id: String,
    val name: String,
    val rootSpan: Span,
    val childSpans: List<Span>
)

public interface TraceExporter {
    public suspend fun export(traces: List<Trace>)
}

public interface TelemetryManager {
    public fun startTrace(name: String): Trace
    public fun endTrace(traceId: String)
    public fun startSpan(traceId: String, name: String, parentSpanId: String? = null): Span
    public fun endSpan(spanId: String)
    public fun addTag(spanId: String, key: String, value: String)
}
