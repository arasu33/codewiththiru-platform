package com.codewiththiru.security.encryption

data class EncryptionPolicy(
    val algorithm: Algorithm = Algorithm.AES_256_GCM,
    val requireHardwareBacked: Boolean = true
) {
    enum class Algorithm {
        AES_256_GCM,
        RSA_4096
    }
}
