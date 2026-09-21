package com.codewiththiru.platform.about.model

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Root configuration object defining all structural data mapped into the AboutScreen.
 * Utilizes a standard Builder pattern for instantiation.
 */
@Immutable
@ConsistentCopyVisibility
data class AboutConfig private constructor(
    val appInfo: AppInfo,
    val developerInfo: DeveloperInfo?,
    val deviceInfo: DeviceInfo?,
    val legalInfo: LegalInfo?,
    val diagnosticsConfig: DiagnosticsConfig?,
    val themeConfig: AboutThemeConfig?,
    val visibility: AboutVisibility?,
) {
    class Builder {
        private var appInfo: AppInfo? = null
        private var developerInfo: DeveloperInfo? = null
        private var deviceInfo: DeviceInfo? = null
        private var legalInfo: LegalInfo? = null
        private var diagnosticsConfig: DiagnosticsConfig? = null
        private var themeConfig: AboutThemeConfig? = null
        private var visibility: AboutVisibility? = null

        fun setAppInfo(appInfo: AppInfo) = apply { this.appInfo = appInfo }

        fun setDeveloperInfo(developerInfo: DeveloperInfo?) = apply { this.developerInfo = developerInfo }

        fun setDeviceInfo(deviceInfo: DeviceInfo?) = apply { this.deviceInfo = deviceInfo }

        fun setLegalInfo(legalInfo: LegalInfo?) = apply { this.legalInfo = legalInfo }

        fun setDiagnosticsConfig(diagnosticsConfig: DiagnosticsConfig?) =
            apply {
                this.diagnosticsConfig = diagnosticsConfig
            }

        fun setThemeConfig(themeConfig: AboutThemeConfig?) =
            apply {
                this.themeConfig = themeConfig
            }

        fun setVisibility(visibility: AboutVisibility?) =
            apply {
                this.visibility = visibility
            }

        fun build(): AboutConfig {
            requireNotNull(appInfo) { "AppInfo must be provided" }
            return AboutConfig(
                appInfo = appInfo!!,
                developerInfo = developerInfo,
                deviceInfo = deviceInfo,
                legalInfo = legalInfo,
                diagnosticsConfig = diagnosticsConfig,
                themeConfig = themeConfig,
                visibility = visibility,
            )
        }
    }
}

@Immutable
data class AppInfo(
    val appName: String,
    val packageName: String,
    val versionName: String,
    val versionCode: Long,
    val buildType: String,
    val appDescription: String? = null,
    val appIcon: Painter? = null,
)

@Immutable
data class SocialLink(
    val platformName: String,
    val url: String,
    val icon: Painter? = null,
)

@Immutable
data class DeveloperInfo(
    val developerName: String,
    val avatar: Painter? = null,
    val email: String? = null,
    val websiteUrl: String? = null,
    val socialLinks: List<SocialLink> = emptyList(),
)

@Immutable
data class DeviceInfo(
    val osVersion: String,
    val apiLevel: Int,
    val manufacturer: String,
    val model: String,
)

@Immutable
data class LegalInfo(
    val privacyPolicyUrl: String? = null,
    val termsOfServiceUrl: String? = null,
    val showOssLicenses: Boolean = false,
)

@Immutable
data class DiagnosticsConfig(
    val customPayload: Map<String, String> = emptyMap(),
    val includeDeviceInfo: Boolean = true,
)

@Immutable
data class AboutVisibility(
    val showAppIcon: Boolean = true,
    val showDeviceSection: Boolean = true,
    val showLegalSection: Boolean = true,
    val showDiagnostics: Boolean = true,
)

@Immutable
data class AboutThemeConfig(
    val headerStyle: TextStyle? = null,
    val cardElevation: Dp = 1.dp,
)
