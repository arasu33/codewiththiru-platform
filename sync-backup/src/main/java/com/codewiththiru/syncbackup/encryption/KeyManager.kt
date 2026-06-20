package com.codewiththiru.syncbackup.encryption

interface KeyManager {
    fun getOrCreateKey(keyAlias: String): String
    fun rotateKey(keyAlias: String)
    fun deleteKey(keyAlias: String)
}
