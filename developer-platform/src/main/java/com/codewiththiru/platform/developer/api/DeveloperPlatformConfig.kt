package com.codewiththiru.platform.developer.api

data class DeveloperPlatformConfig(
    val enableAiAssistant: Boolean = true,
    val enableAutoDocumentation: Boolean = true,
    val telemetryEnabled: Boolean = true
)
