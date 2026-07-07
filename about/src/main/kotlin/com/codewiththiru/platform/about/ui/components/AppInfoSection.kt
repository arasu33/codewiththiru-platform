@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.about.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.about.model.AboutVisibility
import com.codewiththiru.platform.about.model.AppInfo
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.widgets.CustAvatar

@Composable
internal fun AppInfoSection(
    appInfo: AppInfo,
    visibility: AboutVisibility?,
    modifier: Modifier = Modifier,
) {
    val showAppIcon = visibility?.showAppIcon ?: true

    Column(
        modifier =
            modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        if (showAppIcon && appInfo.appIcon != null) {
            CustAvatar(
                painter = appInfo.appIcon,
                contentDescription = "${appInfo.appName} Logo",
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        CustText(
            text = appInfo.appName,
            modifier = Modifier.padding(bottom = 8.dp),
        )

        if (appInfo.appDescription != null) {
            CustText(
                text = appInfo.appDescription,
                modifier = Modifier.padding(bottom = 8.dp),
            )
        }

        CustText(
            text = "Version ${appInfo.versionName} (${appInfo.versionCode})",
            modifier = Modifier.padding(bottom = 4.dp),
        )

        CustText(
            text = "${appInfo.buildType} Build",
        )
    }
}
