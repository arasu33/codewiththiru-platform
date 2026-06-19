@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import com.codewiththiru.platform.designsystem.components.CustText

@Composable
fun CustSectionHeader(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    action: @Composable (() -> Unit)? = null
) {
    val semanticDesc = if (subtitle != null) "$title. $subtitle" else title

    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(
                horizontal = CustSectionHeaderDefaults.horizontalPadding,
                vertical = CustSectionHeaderDefaults.verticalPadding
            )
            .semantics(mergeDescendants = true) {
                heading()
                contentDescription = semanticDesc
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.weight(1f)) {
            CustText(
                text = title,
                style = CustSectionHeaderDefaults.titleStyle,
                color = CustSectionHeaderDefaults.titleColor
            )
            if (subtitle != null) {
                Spacer(modifier = Modifier.height(CustSectionHeaderDefaults.subtitleTopPadding))
                CustText(
                    text = subtitle,
                    style = CustSectionHeaderDefaults.subtitleStyle,
                    color = CustSectionHeaderDefaults.subtitleColor
                )
            }
        }
        
        if (action != null) {
            action()
        }
    }
}
