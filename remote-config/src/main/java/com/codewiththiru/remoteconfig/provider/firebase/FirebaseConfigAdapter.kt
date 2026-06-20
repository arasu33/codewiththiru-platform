package com.codewiththiru.remoteconfig.provider.firebase

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import kotlinx.coroutines.tasks.await

class FirebaseConfigAdapter {
    private val remoteConfig: FirebaseRemoteConfig by lazy { Firebase.remoteConfig }

    suspend fun initialize() {
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        }
        remoteConfig.setConfigSettingsAsync(configSettings).await()
    }

    suspend fun fetch() {
        remoteConfig.fetch().await()
    }

    suspend fun activate(): Boolean {
        return remoteConfig.activate().await()
    }

    suspend fun fetchAndActivate(): Boolean {
        return remoteConfig.fetchAndActivate().await()
    }

    fun getValue(key: String): FirebaseRemoteConfigValue {
        return remoteConfig.getValue(key)
    }
}
