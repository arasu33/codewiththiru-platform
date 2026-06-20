package com.codewiththiru.remoteconfig.provider.fallback

data class SafeModeConfig(
    val isSafeModeEnabled: Boolean = false,
    val disableFirebase: Boolean = false,
    val forceJsonFallback: Boolean = false,
    val disableExperiments: Boolean = false,
    val disableFeatureFlags: Boolean = false
)
