package com.codewiththiru.platform.framework.config

import com.codewiththiru.platform.framework.foundation.PlatformEnvironment

data class ConfigurationProfile(
    val profileId: String,
    val environment: EnvironmentProfile,
    val properties: Map<String, String>
)

data class EnvironmentProfile(
    val env: PlatformEnvironment,
    val baseUrl: String,
    val apiKey: String
)

interface PlatformConfigManager {
    fun loadConfiguration(profile: ConfigurationProfile)
    fun getConfigValue(key: String): String?
}
