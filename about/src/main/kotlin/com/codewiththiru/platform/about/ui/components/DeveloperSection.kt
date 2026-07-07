@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.about.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codewiththiru.platform.about.AboutEventListener
import com.codewiththiru.platform.about.R
import com.codewiththiru.platform.about.model.DeveloperInfo
import com.codewiththiru.platform.designsystem.widgets.CustInfoRow
import com.codewiththiru.platform.designsystem.widgets.CustSectionHeader

@Composable
internal fun DeveloperSection(
    developerInfo: DeveloperInfo,
    eventListener: AboutEventListener,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustSectionHeader(title = stringResource(id = R.string.about_section_developer))

        CustInfoRow(
            label = stringResource(id = R.string.about_label_name),
            value = developerInfo.developerName,
        )

        developerInfo.websiteUrl?.let { url ->
            CustInfoRow(
                label = stringResource(id = R.string.about_label_website),
                value = url,
                onClick = { eventListener.onLegalLinkClick(url) }, // Reuse standard link click
            )
        }

        developerInfo.email?.let { email ->
            CustInfoRow(
                label = stringResource(id = R.string.about_label_email),
                value = email,
                onClick = { eventListener.onContactDeveloper(developerInfo) },
            )
        }

        developerInfo.socialLinks.forEach { link ->
            CustInfoRow(
                label = link.platformName,
                value = stringResource(id = R.string.about_value_view),
                onClick = { eventListener.onSocialLinkClick(link) },
            )
        }
    }
}
