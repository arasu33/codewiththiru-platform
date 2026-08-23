package com.codewiththiru.platform.coupons.data.local

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

class EncryptedCouponStorageProvider(
    private val context: Context,
) : CouponStorageProvider {
    private val masterKey by lazy {
        MasterKey
            .Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "encrypted_coupons_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    override suspend fun save(
        key: String,
        data: String,
    ) {
        sharedPreferences.edit().putString(key, data).apply()
    }

    override suspend fun read(key: String): String? = sharedPreferences.getString(key, null)

    override suspend fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
