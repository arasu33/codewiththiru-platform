package com.codewiththiru.remoteconfig.provider.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

class FirebaseMapper {
    fun mapString(value: FirebaseRemoteConfigValue): String? {
        if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return value.asString().takeIf { it.isNotEmpty() }
    }

    fun mapBoolean(value: FirebaseRemoteConfigValue): Boolean? {
        if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asBoolean() } catch (e: Exception) { null }
    }

    fun mapInt(value: FirebaseRemoteConfigValue): Int? {
        if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asLong().toInt() } catch (e: Exception) { null }
    }

    fun mapLong(value: FirebaseRemoteConfigValue): Long? {
        if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asLong() } catch (e: Exception) { null }
    }

    fun mapDouble(value: FirebaseRemoteConfigValue): Double? {
        if (value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asDouble() } catch (e: Exception) { null }
    }
    
    fun mapException(e: Exception): Throwable {
        return e
    }
}
