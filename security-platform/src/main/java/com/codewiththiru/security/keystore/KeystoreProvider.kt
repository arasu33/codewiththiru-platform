package com.codewiththiru.security.keystore

class KeystoreProvider : SecretProvider {
    override fun save(key: String, value: String) {
        // Implementation using Android Keystore
    }

    override fun load(key: String): String? {
        return null
    }

    override fun delete(key: String) {
    }
}
