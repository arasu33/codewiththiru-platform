package com.codewiththiru.remoteconfig.provider.firebase

import com.google.firebase.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.remoteConfig
import com.google.firebase.remoteconfig.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class FirebaseConfigAdapter {
    private val remoteConfig: FirebaseRemoteConfig? by lazy {
        try {
            Firebase.remoteConfig
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            android.util.Log.e("CWT_PLATFORM", "Firebase Remote Config is not initialized.", e)
            null
        }
    }

    suspend fun initialize() {
        val config = remoteConfig ?: return
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        config.setConfigSettingsAsync(configSettings).await()
    }

    suspend fun fetch() {
        remoteConfig?.fetch()?.await()
    }

    suspend fun activate(): Boolean {
        return remoteConfig?.activate()?.await() ?: false
    }

    suspend fun fetchAndActivate(): Boolean {
        return remoteConfig?.fetchAndActivate()?.await() ?: false
    }

    fun getValue(key: String): FirebaseRemoteConfigValue? {
        return remoteConfig?.getValue(key)
    }
}
