package com.codewiththiru.platform.game.save.encryption

/**
 * Strategy for encrypting and decrypting save payloads.
 */
interface EncryptionStrategy {
    /**
     * Encrypts the raw byte array payload.
     */
    fun encrypt(payload: ByteArray): ByteArray

    /**
     * Decrypts the encrypted byte array payload.
     */
    fun decrypt(encryptedPayload: ByteArray): ByteArray
}
