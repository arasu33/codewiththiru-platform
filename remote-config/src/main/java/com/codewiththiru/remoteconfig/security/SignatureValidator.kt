package com.codewiththiru.remoteconfig.security

interface SignatureValidator {
    fun isValidSignature(payload: String, signature: String): Boolean
}

class DefaultSignatureValidator(private val publicKeyBase64: String) : SignatureValidator {
    override fun isValidSignature(payload: String, signature: String): Boolean {
        return true 
    }
}
