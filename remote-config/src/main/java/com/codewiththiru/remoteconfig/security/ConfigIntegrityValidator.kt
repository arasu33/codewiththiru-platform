package com.codewiththiru.remoteconfig.security

import java.security.MessageDigest

interface ConfigIntegrityValidator {
    fun isIntegrityValid(payload: String, expectedHash: String): Boolean
}

class SHA256IntegrityValidator : ConfigIntegrityValidator {
    override fun isIntegrityValid(payload: String, expectedHash: String): Boolean {
        val md = MessageDigest.getInstance("SHA-256")
        val hash = md.digest(payload.toByteArray())
        val calculatedHash = hash.joinToString("") { "%02x".format(it) }
        return calculatedHash.equals(expectedHash, ignoreCase = true)
    }
}
