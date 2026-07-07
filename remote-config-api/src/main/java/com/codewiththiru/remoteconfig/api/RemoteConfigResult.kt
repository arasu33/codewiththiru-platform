package com.codewiththiru.remoteconfig.api

sealed class RemoteConfigResult<out T> {
    data class Success<T>(
        val value: T,
        val source: ConfigSource,
    ) : RemoteConfigResult<T>()

    data class Failure(
        val exception: Throwable,
        val fallbackValue: Any? = null,
    ) : RemoteConfigResult<Nothing>()
}

enum class ConfigSource {
    MEMORY_CACHE,
    DATASTORE_CACHE,
    FIREBASE,
    JSON,
    HARDCODED_DEFAULT,
    EMERGENCY_FALLBACK,
}
