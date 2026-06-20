package com.codewiththiru.security.encryption

interface EncryptionManager {
    fun encrypt(data: ByteArray, policy: EncryptionPolicy): EncryptedPayload
    fun decrypt(payload: EncryptedPayload): ByteArray
}
