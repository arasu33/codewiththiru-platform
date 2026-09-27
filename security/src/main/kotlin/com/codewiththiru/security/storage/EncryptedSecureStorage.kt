package com.codewiththiru.security.storage

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Production-ready implementation of [SecureStorage] backed by [EncryptedSharedPreferences]
 * with hardware-backed AES-256 GCM key encryption.
 */
class EncryptedSecureStorage(
    context: Context,
    fileName: String = DEFAULT_PREFS_NAME,
) : SecureStorage {
    private val masterKey: MasterKey =
        MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

    private val sharedPreferences: SharedPreferences =
        EncryptedSharedPreferences.create(
            context,
            fileName,
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )

    override fun putString(
        key: String,
        value: String,
    ) {
        sharedPreferences.edit().putString(key, value).apply()
    }

    override fun getString(
        key: String,
        defaultValue: String?,
    ): String? = sharedPreferences.getString(key, defaultValue)

    override fun putBoolean(
        key: String,
        value: Boolean,
    ) {
        sharedPreferences.edit().putBoolean(key, value).apply()
    }

    override fun getBoolean(
        key: String,
        defaultValue: Boolean,
    ): Boolean = sharedPreferences.getBoolean(key, defaultValue)

    override fun putInt(
        key: String,
        value: Int,
    ) {
        sharedPreferences.edit().putInt(key, value).apply()
    }

    override fun getInt(
        key: String,
        defaultValue: Int,
    ): Int = sharedPreferences.getInt(key, defaultValue)

    override fun putLong(
        key: String,
        value: Long,
    ) {
        sharedPreferences.edit().putLong(key, value).apply()
    }

    override fun getLong(
        key: String,
        defaultValue: Long,
    ): Long = sharedPreferences.getLong(key, defaultValue)

    override fun remove(key: String) {
        sharedPreferences.edit().remove(key).apply()
    }

    override fun clear() {
        sharedPreferences.edit().clear().apply()
    }

    override fun contains(key: String): Boolean = sharedPreferences.contains(key)

    companion object {
        const val DEFAULT_PREFS_NAME = "cwt_encrypted_prefs"
    }
}
