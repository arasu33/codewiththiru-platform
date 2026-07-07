package com.codewiththiru.platform.analytics.data.queue

import kotlinx.serialization.json.JsonArray
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonNull
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

/**
 * Utility to safely convert arbitrary Any? objects to kotlinx.serialization JsonElement.
 */
public object AnyValueSerializer {
    @Suppress("TooGenericExceptionCaught", "SwallowedException")
    public fun toJsonElement(value: Any?): JsonElement {
        if (value == null) return JsonNull

        return try {
            when (value) {
                is JsonElement -> value
                is String -> JsonPrimitive(value)
                is Boolean -> JsonPrimitive(value)
                is Number -> JsonPrimitive(value)
                is List<*> -> {
                    val elements = value.map { toJsonElement(it) }
                    JsonArray(elements)
                }
                is Map<*, *> -> {
                    val entries =
                        value.entries.associate {
                            it.key.toString() to toJsonElement(it.value)
                        }
                    JsonObject(entries)
                }
                else -> JsonPrimitive(value.toString())
            }
        } catch (e: Exception) {
            // Fallback for safety - never crash
            JsonPrimitive(value.toString())
        }
    }

    public fun toParametersMap(parameters: Map<String, Any?>): Map<String, JsonElement> =
        parameters.mapValues {
            toJsonElement(it.value)
        }
}
