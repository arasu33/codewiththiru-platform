package com.codewiththiru.security.encryption

data class EncryptedPayload(
    val ciphertext: ByteArray,
    val iv: ByteArray,
    val algorithm: String
)
