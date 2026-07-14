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

            val mergedMap = java.util.concurrent.ConcurrentHashMap<String, kotlinx.serialization.json.JsonElement>()

            // 1. Copy all fields from the older payload
            mergedMap.putAll(secondary)

            // 2. Overwrite with fields from the newer payload (primary wins collisions)
            mergedMap.putAll(primary)

            JsonObject(mergedMap).toString()
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException ||
                e is kotlin.coroutines.cancellation.CancellationException
            ) {
                throw e
            }
            // If JSON parsing fails, fallback to simple string replacement
            if (localTimestampMs >= remoteTimestampMs) localJson else remoteJson
        }
}
