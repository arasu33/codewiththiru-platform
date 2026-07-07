package com.codewiththiru.platform.analytics.data.export

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Handles exporting analytics data to local files or external streams (like BigQuery/Looker).
 */
public class AnalyticsExportManager(
    private val bigQueryMapper: BigQueryEventMapper,
    private val lookerStudioMapper: LookerStudioMapper,
) {
    public fun exportToBigQuery(events: List<AnalyticsEvent>): List<Map<String, Any>> =
        events.map { bigQueryMapper.map(it) }

    public fun exportToCsv(events: List<AnalyticsEvent>): String =
        events.joinToString("\n") { lookerStudioMapper.map(it) }
}
