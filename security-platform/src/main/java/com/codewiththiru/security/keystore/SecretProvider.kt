package com.codewiththiru.security.keystore

interface SecretProvider {
    fun save(key: String, value: String)
    fun load(key: String): String?
    fun delete(key: String)
}
