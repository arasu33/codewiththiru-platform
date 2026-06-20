package com.codewiththiru.platform.framework.foundation

data class PlatformApp(
    val appId: String,
    val name: String,
    val environment: PlatformEnvironment,
    val configuration: PlatformConfiguration
)

data class PlatformConfiguration(
    val featuresEnabled: List<String>,
    val whiteLabelConfigId: String
)

enum class PlatformEnvironment {
    DEV, QA, STAGING, PROD
}

data class PlatformManifest(
    val registeredApps: List<PlatformApp>,
    val globalVersion: String
)

interface PlatformRegistry {
    fun registerApp(app: PlatformApp)
    fun getApp(appId: String): PlatformApp?
    fun discoverApps(): List<PlatformApp>
}
