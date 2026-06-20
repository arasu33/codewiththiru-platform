@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.about.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.codewiththiru.platform.about.AboutEventListener
import com.codewiththiru.platform.about.R
import com.codewiththiru.platform.about.model.LegalInfo
import com.codewiththiru.platform.about.provider.LicenseProvider
import com.codewiththiru.platform.designsystem.widgets.CustInfoRow
import com.codewiththiru.platform.designsystem.widgets.CustSectionHeader

@Composable
internal fun LegalSection(
    legalInfo: LegalInfo,
    eventListener: AboutEventListener,
    licenseProvider: LicenseProvider?,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        CustSectionHeader(title = stringResource(id = R.string.about_section_legal))
        
        legalInfo.privacyPolicyUrl?.let { url ->
            CustInfoRow(
                label = stringResource(id = R.string.about_label_privacy_policy),
                value = stringResource(id = R.string.about_value_view),
                onClick = { eventListener.onLegalLinkClick(url) }
            )
        }
        
        legalInfo.termsOfServiceUrl?.let { url ->
            CustInfoRow(
                label = stringResource(id = R.string.about_label_terms_of_service),
                value = stringResource(id = R.string.about_value_view),
                onClick = { eventListener.onLegalLinkClick(url) }
            )
        }
        
        if (legalInfo.showOssLicenses && licenseProvider != null) {
            CustInfoRow(
                label = stringResource(id = R.string.about_label_open_source_licenses),
                value = stringResource(id = R.string.about_value_view),
                onClick = { licenseProvider.showLicenses() }
            )
        }
    }
}
