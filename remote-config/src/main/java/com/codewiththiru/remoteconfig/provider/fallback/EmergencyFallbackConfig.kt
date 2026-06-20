package com.codewiththiru.remoteconfig.provider.fallback

class EmergencyFallbackConfig {
    private val emergencyMap = mapOf(
        "ads_enabled" to false,
        "analytics_enabled" to false,
        "force_update_enabled" to false
    )

    fun getFallbackValue(key: String): Any? {
        return emergencyMap[key]
    }
}
