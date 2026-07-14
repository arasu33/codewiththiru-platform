package com.codewiththiru.remoteconfig.provider.firebase

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigValue

class FirebaseMapper {
    fun mapString(value: FirebaseRemoteConfigValue?): String? {
        if (value == null || value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return value.asString().takeIf { it.isNotEmpty() }
    }

    fun mapBoolean(value: FirebaseRemoteConfigValue?): Boolean? {
        if (value == null || value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asBoolean() } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e null }
    }

    fun mapInt(value: FirebaseRemoteConfigValue?): Int? {
        if (value == null || value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asLong().toInt() } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e null }
    }

    fun mapLong(value: FirebaseRemoteConfigValue?): Long? {
        if (value == null || value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asLong() } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e null }
    }

    fun mapDouble(value: FirebaseRemoteConfigValue?): Double? {
        if (value == null || value.source == FirebaseRemoteConfig.VALUE_SOURCE_STATIC) return null
        return try { value.asDouble() } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e null }
    }
    
    fun mapException(e: Exception): Throwable {
        return e
    }
}
