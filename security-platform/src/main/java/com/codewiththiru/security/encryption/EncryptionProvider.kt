package com.codewiththiru.security.encryption

interface EncryptionProvider {
    fun encrypt(data: ByteArray): EncryptedPayload
    fun decrypt(payload: EncryptedPayload): ByteArray
}
