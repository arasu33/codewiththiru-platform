package com.codewiththiru.platform.game.save.compression

/**
 * Strategy for compressing and decompressing save payloads.
 */
interface CompressionStrategy {
    /**
     * Compresses the raw byte array payload.
     */
    fun compress(payload: ByteArray): ByteArray

    /**
     * Decompresses the compressed byte array payload.
     */
    fun decompress(compressedPayload: ByteArray): ByteArray
}
