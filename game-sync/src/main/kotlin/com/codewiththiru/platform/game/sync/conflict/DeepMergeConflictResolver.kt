package com.codewiththiru.platform.game.sync.conflict

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject

/**
 * A ConflictResolver that performs a basic flat deep-merge on JSON payloads.
 * Fields from both payloads are combined. In the event of a field collision,
 * the payload with the newer timestamp takes precedence.
 */
class DeepMergeConflictResolver : ConflictResolver {
    @Suppress("SwallowedException", "TooGenericExceptionCaught")
    override fun resolve(
        collection: String,
        localJson: String,
        remoteJson: String,
        localTimestampMs: Long,
        remoteTimestampMs: Long,
    ): String =
        try {
            val localObj = Json.parseToJsonElement(localJson) as JsonObject
            val remoteObj = Json.parseToJsonElement(remoteJson) as JsonObject

            val localWins = localTimestampMs >= remoteTimestampMs

            val primary = if (localWins) localObj else remoteObj
            val secondary = if (localWins) remoteObj else localObj

            fun deepMerge(
                secondary: JsonObject,
                primary: JsonObject,
            ): JsonObject {
                val merged = mutableMapOf<String, kotlinx.serialization.json.JsonElement>()
                merged.putAll(secondary)
                for ((key, primaryValue) in primary) {
                    val secondaryValue = secondary[key]
                    if (secondaryValue is JsonObject && primaryValue is JsonObject) {
                        merged[key] = deepMerge(secondaryValue, primaryValue)
                    } else {
                        merged[key] = primaryValue
                    }
                }
                return JsonObject(merged)
            }

            deepMerge(secondary, primary).toString()
        } catch (e: kotlinx.coroutines.CancellationException) {
            throw e
        } catch (e: kotlin.coroutines.cancellation.CancellationException) {
            throw e
        } catch (e: Exception) {
            // If JSON parsing fails, fallback to simple string replacement
            if (localTimestampMs >= remoteTimestampMs) localJson else remoteJson
        }
}
