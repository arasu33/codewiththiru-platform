@file:Suppress("EmptyFunctionBlock", "LongParameterList")

package com.codewiththiru.platform.about

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.about.model.AboutConfig
import com.codewiththiru.platform.about.model.AboutThemeConfig
import com.codewiththiru.platform.about.model.AboutVisibility
import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.about.model.LegalInfo
import com.codewiththiru.platform.about.model.SocialLink

/**
 * Provides standard fallback implementations for About module constraints.
 */
object AboutDefaults {
    /**
     * Retrieves the default Theme bounds linked natively to Material 3.
     */
    @Composable
    fun themeConfig(): AboutThemeConfig =
        AboutThemeConfig(
            headerStyle = MaterialTheme.typography.titleMedium,
            cardElevation = 1.dp,
        )

    /**
     * Default visibility matrix (everything enabled).
     */
    fun visibility(): AboutVisibility =
        AboutVisibility(
            showAppIcon = true,
            showDeviceSection = true,
            showLegalSection = true,
            showDiagnostics = true,
        )

    /**
     * Returns a no-op event listener.
     */
    fun eventListener(): AboutEventListener =
        object : AboutEventListener {
            override fun onShareApp(appInfo: AppInfo) {}

            override fun onCopyDiagnostics(payload: String) {}

            override fun onContactDeveloper(developerInfo: DeveloperInfo) {}

            override fun onSocialLinkClick(link: SocialLink) {}

            override fun onLegalLinkClick(url: String) {}
        }

    /**
     * Creates a turnkey, fully customizable default [AboutConfig].
     */
    fun defaultConfig(
        appName: String = "App Name",
        packageName: String = "com.example.app",
        versionName: String = "1.0.0",
        versionCode: Long = 1L,
        buildType: String = "release",
        developerName: String? = null,
        developerEmail: String? = null,
        websiteUrl: String? = null,
        privacyPolicyUrl: String? = null,
        termsOfServiceUrl: String? = null,
    ): AboutConfig {
        val builder = AboutConfig.Builder()
        builder.setAppInfo(
            AppInfo(
                appName = appName,
                packageName = packageName,
                versionName = versionName,
                versionCode = versionCode,
                buildType = buildType,
            ),
        )
        builder.setVisibility(visibility())

        if (developerName != null) {
            builder.setDeveloperInfo(
                DeveloperInfo(
                    developerName = developerName,
                    email = developerEmail,
                    websiteUrl = websiteUrl,
                ),
            )
        }

        if (privacyPolicyUrl != null || termsOfServiceUrl != null) {
            builder.setLegalInfo(
                LegalInfo(
                    privacyPolicyUrl = privacyPolicyUrl,
                    termsOfServiceUrl = termsOfServiceUrl,
                ),
            )
        }

        return builder.build()
    }
}
