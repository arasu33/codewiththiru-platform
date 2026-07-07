package com.codewiththiru.platform.game.save.manager

import java.security.MessageDigest
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

class SignatureValidator(
    private val deviceIdentifier: String,
    private val secretKeyString: String
) {
    companion object {
        private const val ALGORITHM = "HmacSHA256"
    }

    fun generateSignature(payload: ByteArray): String {
        val mac = Mac.getInstance(ALGORITHM)
        val secretKeySpec = SecretKeySpec(secretKeyString.toByteArray(), ALGORITHM)
        mac.init(secretKeySpec)
        
        // Combine device identifier with payload to bind save to device
        mac.update(deviceIdentifier.toByteArray())
        val hashBytes = mac.doFinal(payload)
        
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    fun validateSignature(payload: ByteArray, expectedSignature: String): Boolean {
        val actualSignature = generateSignature(payload)
        return MessageDigest.isEqual(actualSignature.toByteArray(), expectedSignature.toByteArray())
    }
}
