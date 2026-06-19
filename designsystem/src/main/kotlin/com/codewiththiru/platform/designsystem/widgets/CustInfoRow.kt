@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import com.codewiththiru.platform.designsystem.components.CustText

@Composable
fun CustInfoRow(
    label: String,
    modifier: Modifier = Modifier,
    value: String? = null,
    icon: @Composable (() -> Unit)? = null,
    trailingContent: @Composable (() -> Unit)? = null,
    onClick: (() -> Unit)? = null
) {
    val semanticDesc = buildString {
        append(label)
        if (value != null) append(". $value")
    }

    val clickModifier = if (onClick != null) {
        Modifier.clickable(onClick = onClick)
    } else {
        Modifier
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .then(clickModifier)
            .padding(
                horizontal = CustInfoRowDefaults.horizontalPadding,
                vertical = CustInfoRowDefaults.verticalPadding
            )
            .semantics(mergeDescendants = true) {
                contentDescription = semanticDesc
                if (onClick != null) {
                    role = Role.Button
                }
            },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            if (icon != null) {
                icon()
                Spacer(modifier = Modifier.width(CustInfoRowDefaults.iconSpacing))
            }
            CustText(
                text = label,
                style = CustInfoRowDefaults.labelStyle,
                color = CustInfoRowDefaults.labelColor
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.End
        ) {
            if (value != null) {
                CustText(
                    text = value,
                    style = CustInfoRowDefaults.valueStyle,
                    color = CustInfoRowDefaults.valueColor
                )
            }
            if (trailingContent != null) {
                Spacer(modifier = Modifier.width(CustInfoRowDefaults.labelValueSpacing))
                trailingContent()
            }
        }
    }
}
