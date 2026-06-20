package com.codewiththiru.platform.growth.segmentation

data class EngagementProfile(
    val userId: String,
    val segmentIds: List<String>,
    val predictedLtv: Double,
    val churnRisk: Float
)
