package com.codewiththiru.platform.growth.activation

data class ActivationEvent(
    val eventId: String,
    val weight: Int = 1,
    val timestamp: Long = System.currentTimeMillis()
)
