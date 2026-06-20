@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.about.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.res.stringResource
import com.codewiththiru.platform.about.AboutEventListener
import com.codewiththiru.platform.about.R
import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.about.model.DeviceInfo
import com.codewiththiru.platform.about.model.DiagnosticsConfig
import com.codewiththiru.platform.designsystem.components.CustButton
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.widgets.CustSectionHeader

@Suppress("LongParameterList")
@Composable
internal fun ActionsSection(
    eventListener: AboutEventListener,
    appInfo: AppInfo,
    deviceInfo: DeviceInfo?,
    developerInfo: DeveloperInfo?,
    diagnosticsConfig: DiagnosticsConfig?,
    onPreviewDiagnostics: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustSectionHeader(title = stringResource(id = R.string.about_section_actions))

        CustButton(
            onClick = { eventListener.onShareApp(appInfo) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        ) {
            CustText(text = stringResource(id = R.string.about_action_share_app))
        }

        if (diagnosticsConfig != null) {
            val diagnostics = buildDiagnosticsString(appInfo, deviceInfo, diagnosticsConfig)
            CustButton(
                onClick = { onPreviewDiagnostics(diagnostics) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                CustText(text = stringResource(id = R.string.about_action_copy_diagnostics))
            }
        }

        if (developerInfo != null) {
            CustButton(
                onClick = { eventListener.onContactDeveloper(developerInfo) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp)
            ) {
                CustText(text = stringResource(id = R.string.about_action_contact_support))
            }
        }
    }
}

private fun buildDiagnosticsString(
    appInfo: AppInfo,
    deviceInfo: DeviceInfo?,
    config: DiagnosticsConfig
): String {
    return buildString {
        appendLine("App: ${appInfo.appName}")
        appendLine("Package: ${appInfo.packageName}")
        appendLine("Version: ${appInfo.versionName} (${appInfo.versionCode})")
        appendLine("Build Type: ${appInfo.buildType}")
        if (config.includeDeviceInfo && deviceInfo != null) {
            appendLine("OS: Android ${deviceInfo.osVersion} (API ${deviceInfo.apiLevel})")
            appendLine("Device: ${deviceInfo.manufacturer} ${deviceInfo.model}")
        }
        config.customPayload.forEach { (key, value) ->
            appendLine("$key: $value")
        }
    }
}
