package com.codewiththiru.platform.growth.segmentation

data class AudienceSegment(
    val id: String,
    val name: String,
    val criteria: Map<String, Any>
)
