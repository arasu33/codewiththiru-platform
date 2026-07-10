package com.codewiththiru.platform.analytics.data.export

import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent

/**
 * Handles exporting analytics data to local files or external streams (like BigQuery/Looker).
 */
internal class AnalyticsExportManager(
    private val bigQueryMapper: BigQueryEventMapper,
    private val lookerStudioMapper: LookerStudioMapper,
) {
    internal fun exportToBigQuery(events: List<AnalyticsEvent>): List<Map<String, Any>> =
        events.map { bigQueryMapper.map(it) }

    internal fun exportToCsv(events: List<AnalyticsEvent>): String =
        events.joinToString("\n") { lookerStudioMapper.map(it) }
}
