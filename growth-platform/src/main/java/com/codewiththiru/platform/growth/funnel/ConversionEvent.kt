package com.codewiththiru.platform.growth.funnel

data class ConversionEvent(
    val eventName: String,
    val value: Double = 0.0,
    val currency: String? = null,
    val metadata: Map<String, String> = emptyMap()
)
