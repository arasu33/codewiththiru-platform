package com.codewiththiru.remoteconfig.provider

class CompositeRemoteConfigProvider(
    private val priorityPolicy: ProviderPriorityPolicy,
    private val circuitBreakerPolicy: CircuitBreakerPolicy = CircuitBreakerPolicy()
) : RemoteConfigProvider {
    
    override val name: String = "CompositeProvider"

    override suspend fun initialize() {
        priorityPolicy.getProvidersInPriorityOrder().forEach { it.initialize() }
    }

    override suspend fun fetch(): Result<Unit> {
        var lastException: Throwable? = null
        for (provider in priorityPolicy.getProvidersInPriorityOrder()) {
            val result = circuitBreakerPolicy.execute { provider.fetch() }
            if (result.isSuccess) return Result.success(Unit)
            lastException = result.exceptionOrNull()
        }
        return Result.failure(lastException ?: Exception("All providers failed to fetch"))
    }

    override suspend fun activate(): Boolean {
        for (provider in priorityPolicy.getProvidersInPriorityOrder()) {
            if (provider.activate()) return true
        }
        return false
    }

    override suspend fun fetchAndActivate(): Result<Boolean> {
        var lastException: Throwable? = null
        for (provider in priorityPolicy.getProvidersInPriorityOrder()) {
            val result = circuitBreakerPolicy.execute { provider.fetchAndActivate() }
            if (result.isSuccess && result.getOrNull() == true) return Result.success(true)
            if (result.isFailure) {
                lastException = result.exceptionOrNull()
            }
        }
        return Result.failure(lastException ?: Exception("All providers failed to fetchAndActivate"))
    }

    override fun getString(key: String): String? {
        return priorityPolicy.getProvidersInPriorityOrder().firstNotNullOfOrNull { it.getString(key) }
    }

    override fun getBoolean(key: String): Boolean? {
        return priorityPolicy.getProvidersInPriorityOrder().firstNotNullOfOrNull { it.getBoolean(key) }
    }

    override fun getInt(key: String): Int? {
        return priorityPolicy.getProvidersInPriorityOrder().firstNotNullOfOrNull { it.getInt(key) }
    }

    override fun getLong(key: String): Long? {
        return priorityPolicy.getProvidersInPriorityOrder().firstNotNullOfOrNull { it.getLong(key) }
    }

    override fun getDouble(key: String): Double? {
        return priorityPolicy.getProvidersInPriorityOrder().firstNotNullOfOrNull { it.getDouble(key) }
    }
}
