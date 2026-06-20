package com.codewiththiru.platform.growth.funnel

data class ConversionGoal(
    val id: String,
    val targetEvents: List<String>,
    val timeLimitMs: Long? = null
)
