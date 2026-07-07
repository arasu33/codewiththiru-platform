@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.about.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codewiththiru.platform.about.R
import com.codewiththiru.platform.about.model.DeviceInfo
import com.codewiththiru.platform.designsystem.widgets.CustInfoRow
import com.codewiththiru.platform.designsystem.widgets.CustSectionHeader

@Composable
internal fun DeviceSection(
    deviceInfo: DeviceInfo,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustSectionHeader(title = stringResource(id = R.string.about_section_device))

        CustInfoRow(
            label = stringResource(id = R.string.about_label_model),
            value = "${deviceInfo.manufacturer} ${deviceInfo.model}",
        )

        CustInfoRow(
            label = stringResource(id = R.string.about_label_android_version),
            value = "${deviceInfo.osVersion} (API ${deviceInfo.apiLevel})",
        )
    }
}
