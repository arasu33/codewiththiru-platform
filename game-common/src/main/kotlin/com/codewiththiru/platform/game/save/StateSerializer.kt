package com.codewiththiru.platform.game.save

import kotlin.reflect.KType
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Deprecated("Use Serializer from :game-save module instead")
interface StateSerializer {
    fun <T> serialize(
        state: T,
        type: KType,
    ): String

    fun <T> deserialize(
        serialized: String,
        type: KType,
    ): T
}

class JsonStateSerializer(
    private val json: Json =
        Json {
            ignoreUnknownKeys = true
            prettyPrint = false
            encodeDefaults = true
        },
) : StateSerializer {
    override fun <T> serialize(
        state: T,
        type: KType,
    ): String {
        val serializer = json.serializersModule.serializer(type)
        return json.encodeToString(serializer, state)
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T> deserialize(
        serialized: String,
        type: KType,
    ): T {
        val serializer = json.serializersModule.serializer(type)
        return json.decodeFromString(serializer, serialized) as T
    }
}
