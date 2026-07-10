package com.codewiththiru.platform.analytics.data.export

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Interface for mapping AnalyticsEvent domain models into specialized schemas
 * suitable for external data warehouses (e.g. BigQuery, Looker Studio).
 */
internal interface AnalyticsDashboardMapper<T> {
    fun map(event: AnalyticsEvent): T
}

internal class BigQueryEventMapper : AnalyticsDashboardMapper<Map<String, Any>> {
    override fun map(event: AnalyticsEvent): Map<String, Any> {
        val payload =
            mutableMapOf<String, Any>(
                "event_name" to event.name,
                "event_timestamp" to event.timestamp,
            )
        // Flatten parameters for simple column structure
        event.parameters.forEach { (key, value) ->
            payload["param_$key"] = value ?: ""
        }
        return payload
    }
}

internal class LookerStudioMapper : AnalyticsDashboardMapper<String> {
    override fun map(event: AnalyticsEvent): String {
        // Formats the event as a CSV line for simple Looker Studio ingestion
        val paramsJoined = event.parameters.entries.joinToString(";") { "${it.key}=${it.value}" }
        return "${event.timestamp},${event.name},$paramsJoined"
    }
}
