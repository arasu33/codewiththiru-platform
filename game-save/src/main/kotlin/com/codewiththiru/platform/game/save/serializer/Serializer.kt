package com.codewiththiru.platform.game.save.serializer

import kotlin.reflect.KType

/**
 * Generic abstraction for serialization of Game States.
 */
interface Serializer {
    /**
     * Serializes an object to a ByteArray.
     */
    fun <T : Any> serialize(
        obj: T,
        type: KType,
    ): ByteArray

    /**
     * Deserializes an object from a ByteArray.
     */
    fun <T : Any> deserialize(
        bytes: ByteArray,
        type: KType,
    ): T

    /**
     * Identifies the format of this serializer (e.g. "json", "protobuf", "cbor")
     */
    val format: String
}
