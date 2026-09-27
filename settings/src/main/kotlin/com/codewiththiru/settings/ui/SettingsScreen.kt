@file:Suppress("FunctionNaming", "LongMethod", "LongParameterList")

package com.codewiththiru.settings.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.settings.model.AppSettings
import com.codewiththiru.settings.model.AppTheme

/**
 * Reusable Jetpack Compose settings screen with standard toggles,
 * theme configuration, extensible slot content, and customizable headers.
 */
@Composable
fun SettingsScreen(
    settings: AppSettings,
    versionName: String = "1.0.0",
    modifier: Modifier = Modifier,
    onThemeSelected: (AppTheme) -> Unit = {},
    onDynamicColorChanged: (Boolean) -> Unit = {},
    onNotificationsChanged: (Boolean) -> Unit = {},
    onAnalyticsChanged: (Boolean) -> Unit = {},
    onAboutClicked: (() -> Unit)? = null,
    appearanceTitle: String = "Appearance",
    privacyTitle: String = "Privacy & Notifications",
    aboutTitle: String = "About",
    extraContent: @Composable ColumnScope.() -> Unit = {},
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
    ) {
        // Section: Appearance
        Text(
            text = appearanceTitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        SettingsRow(
            title = "Theme",
            subtitle =
                when (settings.theme) {
                    AppTheme.SYSTEM -> "System default"
                    AppTheme.LIGHT -> "Light"
                    AppTheme.DARK -> "Dark"
                },
            onClick = {
                val nextTheme =
                    when (settings.theme) {
                        AppTheme.SYSTEM -> AppTheme.LIGHT
                        AppTheme.LIGHT -> AppTheme.DARK
                        AppTheme.DARK -> AppTheme.SYSTEM
                    }
                onThemeSelected(nextTheme)
            },
        )

        SettingsSwitchRow(
            title = "Dynamic Colors",
            subtitle = "Theme colors based on your wallpaper (Android 12+)",
            checked = settings.dynamicColor,
            onCheckedChange = onDynamicColorChanged,
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

        // Section: Privacy & Notifications
        Text(
            text = privacyTitle,
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(vertical = 8.dp),
        )

        SettingsSwitchRow(
            title = "Push Notifications",
            subtitle = "Receive app updates and announcements",
            checked = settings.notificationsEnabled,
            onCheckedChange = onNotificationsChanged,
        )

        SettingsSwitchRow(
            title = "Usage Analytics",
            subtitle = "Help us improve by sharing anonymous usage statistics",
            checked = settings.analyticsEnabled,
            onCheckedChange = onAnalyticsChanged,
        )

        // Section: About (optional)
        if (onAboutClicked != null) {
            HorizontalDivider(modifier = Modifier.padding(vertical = 12.dp))

            Text(
                text = aboutTitle,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 8.dp),
            )

            SettingsRow(
                title = "About App",
                subtitle = "Version $versionName",
                onClick = onAboutClicked,
            )
        }

        // Consumer Slot: Extra settings or options
        extraContent()
    }
}

/**
 * Backward-compatible overload matching legacy parameter ordering.
 */
@Composable
fun SettingsScreen(
    settings: AppSettings,
    versionName: String,
    onThemeSelected: (AppTheme) -> Unit,
    onDynamicColorChanged: (Boolean) -> Unit,
    onNotificationsChanged: (Boolean) -> Unit,
    onAnalyticsChanged: (Boolean) -> Unit,
    onAboutClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    SettingsScreen(
        settings = settings,
        versionName = versionName,
        modifier = modifier,
        onThemeSelected = onThemeSelected,
        onDynamicColorChanged = onDynamicColorChanged,
        onNotificationsChanged = onNotificationsChanged,
        onAnalyticsChanged = onAnalyticsChanged,
        onAboutClicked = onAboutClicked,
    )
}

@Composable
private fun SettingsRow(
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .clickable(onClick = onClick)
                .padding(vertical = 12.dp),
    ) {
        Text(text = title, style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}

@Composable
private fun SettingsSwitchRow(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
