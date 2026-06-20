package com.codewiththiru.security.keystore

interface SecretManager {
    fun storeSecret(key: String, secret: String, policy: SecretRotationPolicy)
    fun retrieveSecret(key: String): String?
    fun rotateSecret(key: String, newSecret: String)
}
