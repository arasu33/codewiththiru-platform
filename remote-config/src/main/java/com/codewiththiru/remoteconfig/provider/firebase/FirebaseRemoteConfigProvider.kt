package com.codewiththiru.remoteconfig.provider.firebase

import com.codewiththiru.remoteconfig.provider.RemoteConfigProvider

class FirebaseRemoteConfigProvider(
    private val adapter: FirebaseConfigAdapter = FirebaseConfigAdapter(),
    private val mapper: FirebaseMapper = FirebaseMapper()
) : RemoteConfigProvider {
    
    override val name: String = "Firebase"

    override suspend fun initialize() {
        adapter.initialize()
    }

    override suspend fun fetch(): Result<Unit> {
        return try {
            adapter.fetch()
            Result.success(Unit)
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            Result.failure(mapper.mapException(e))
        }
    }

    override suspend fun activate(): Boolean {
        return try {
            adapter.activate()
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            false
        }
    }

    override suspend fun fetchAndActivate(): Result<Boolean> {
        return try {
            Result.success(adapter.fetchAndActivate())
        } catch (e: Exception) {
            if (e is kotlinx.coroutines.CancellationException || e is kotlin.coroutines.cancellation.CancellationException) throw e
            Result.failure(mapper.mapException(e))
        }
    }

    override fun getString(key: String): String? {
        return mapper.mapString(adapter.getValue(key))
    }

    override fun getBoolean(key: String): Boolean? {
        return mapper.mapBoolean(adapter.getValue(key))
    }

    override fun getInt(key: String): Int? {
        return mapper.mapInt(adapter.getValue(key))
    }

    override fun getLong(key: String): Long? {
        return mapper.mapLong(adapter.getValue(key))
    }

    override fun getDouble(key: String): Double? {
        return mapper.mapDouble(adapter.getValue(key))
    }
}
