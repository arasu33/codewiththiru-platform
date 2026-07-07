package com.codewiththiru.platform.game.save.encryption

import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec

class AesGcmEncryptionStrategy(
    private val secretKey: SecretKey
) : EncryptionStrategy {

    companion object {
        private const val ALGORITHM = "AES/GCM/NoPadding"
        private const val IV_LENGTH = 12
        private const val TAG_LENGTH = 128
    }

    override fun encrypt(payload: ByteArray): ByteArray {
        val cipher = Cipher.getInstance(ALGORITHM)
        val iv = ByteArray(IV_LENGTH)
        SecureRandom().nextBytes(iv)
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
        
        val encryptedData = cipher.doFinal(payload)
        
        // Append IV to the start of the payload
        return iv + encryptedData
    }

    override fun decrypt(encryptedPayload: ByteArray): ByteArray {
        if (encryptedPayload.size < IV_LENGTH) {
            throw IllegalArgumentException("Payload too small to contain IV")
        }

        val iv = encryptedPayload.copyOfRange(0, IV_LENGTH)
        val actualData = encryptedPayload.copyOfRange(IV_LENGTH, encryptedPayload.size)

        val cipher = Cipher.getInstance(ALGORITHM)
        cipher.init(Cipher.DECRYPT_MODE, secretKey, GCMParameterSpec(TAG_LENGTH, iv))
        
        return cipher.doFinal(actualData)
    }
}
