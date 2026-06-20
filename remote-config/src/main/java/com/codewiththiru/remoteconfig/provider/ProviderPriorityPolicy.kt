package com.codewiththiru.remoteconfig.provider

interface ProviderPriorityPolicy {
    fun getProvidersInPriorityOrder(): List<RemoteConfigProvider>
}

class DefaultProviderPriorityPolicy(
    private val firebaseProvider: RemoteConfigProvider,
    private val jsonProvider: RemoteConfigProvider
) : ProviderPriorityPolicy {
    override fun getProvidersInPriorityOrder(): List<RemoteConfigProvider> {
        return listOf(firebaseProvider, jsonProvider)
    }
}
