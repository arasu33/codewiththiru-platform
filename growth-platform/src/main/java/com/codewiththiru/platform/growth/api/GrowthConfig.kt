package com.codewiththiru.platform.growth.api

data class GrowthConfig(
    val offlineModeEnabled: Boolean = true,
    val environment: GrowthEnvironment = GrowthEnvironment.PRODUCTION,
    val automaticFunnelTracking: Boolean = true,
    val enablePredictions: Boolean = true
)
