package com.codewiththiru.remoteconfig.featureflags

class FeatureFlagRegistry {
    private val flags = java.util.concurrent.ConcurrentHashMap<String, FeatureFlagMetadata>()

    fun register(metadata: FeatureFlagMetadata) {
        flags[metadata.key] = metadata
    }

    fun getMetadata(key: String): FeatureFlagMetadata? {
        return flags[key]
    }

    fun getAllFlags(): List<FeatureFlagMetadata> = flags.values.toList()

    fun getDeprecatedFlags(): List<FeatureFlagMetadata> {
        return flags.values.filter { it.lifecycle.status == FeatureFlagStatus.DEPRECATED }
    }
}
