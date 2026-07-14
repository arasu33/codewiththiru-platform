package com.codewiththiru.remoteconfig.provider.json

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.jsonPrimitive
import kotlinx.serialization.json.longOrNull

class JsonParser {
    fun parseToMap(jsonString: String): Map<String, Any> {
        val configMap = java.util.concurrent.ConcurrentHashMap<String, Any>()
        try {
            val jsonObject = Json.parseToJsonElement(jsonString) as JsonObject
            jsonObject.forEach { (key, value) ->
                when {
                    value.jsonPrimitive.isString -> configMap[key] = value.jsonPrimitive.content
                    value.jsonPrimitive.booleanOrNull != null -> configMap[key] = value.jsonPrimitive.booleanOrNull!!
                    value.jsonPrimitive.intOrNull != null -> configMap[key] = value.jsonPrimitive.intOrNull!!
                    value.jsonPrimitive.longOrNull != null -> configMap[key] = value.jsonPrimitive.longOrNull!!
                    value.jsonPrimitive.doubleOrNull != null -> configMap[key] = value.jsonPrimitive.doubleOrNull!!
                }
            }
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            // parsing error
        }
        return configMap
    }
}
