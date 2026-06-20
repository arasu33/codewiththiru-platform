package com.codewiththiru.platform.developer.productivity

data class ProductivityMetric(
    val developerId: String,
    val linesOfCode: Int,
    val reviewsCompleted: Int,
    val prsMerged: Int
)
