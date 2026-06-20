package com.codewiththiru.syncbackup.encryption

interface SecureStorage {
    fun storeSecret(key: String, value: String)
    fun retrieveSecret(key: String): String?
    fun deleteSecret(key: String)
}
