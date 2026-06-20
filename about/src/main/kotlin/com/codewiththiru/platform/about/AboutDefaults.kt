@file:Suppress("EmptyFunctionBlock")

package com.codewiththiru.platform.about

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.about.model.AboutThemeConfig
import com.codewiththiru.platform.about.model.AboutVisibility
import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.about.model.SocialLink

/**
 * Provides standard fallback implementations for About module constraints.
 */
object AboutDefaults {

    /**
     * Retrieves the default Theme bounds linked natively to Material 3.
     */
    @Composable
    fun themeConfig(): AboutThemeConfig {
        return AboutThemeConfig(
            headerStyle = MaterialTheme.typography.titleMedium,
            cardElevation = 1.dp
        )
    }

    /**
     * Default visibility matrix (everything enabled).
     */
    fun visibility(): AboutVisibility {
        return AboutVisibility(
            showAppIcon = true,
            showDeviceSection = true,
            showLegalSection = true,
            showDiagnostics = true
        )
    }

    /**
     * Returns a no-op event listener.
     */
    fun eventListener(): AboutEventListener {
        return object : AboutEventListener {
            override fun onShareApp(appInfo: AppInfo) {}
            override fun onCopyDiagnostics(payload: String) {}
            override fun onContactDeveloper(developerInfo: DeveloperInfo) {}
            override fun onSocialLinkClick(link: SocialLink) {}
            override fun onLegalLinkClick(url: String) {}
        }
    }
}
