package com.codewiththiru.syncbackup.encryption

interface EncryptionManager {
    fun encrypt(data: ByteArray, keyId: String): ByteArray
    fun decrypt(encryptedData: ByteArray, keyId: String): ByteArray
}
