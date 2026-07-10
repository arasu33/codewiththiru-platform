package com.codewiththiru.platform.analytics.data.queue

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
internal data class AnalyticsEventEntity(
    val schemaVersion: Int = 1,
    val eventId: String,
    val eventName: String,
    val timestamp: Long,
    val parameters: Map<String, JsonElement>,
)
