package com.codewiththiru.platform.growth.retention

data class CohortReport(
    val cohortId: String,
    val startDate: Long,
    val metrics: List<RetentionMetric>
)
