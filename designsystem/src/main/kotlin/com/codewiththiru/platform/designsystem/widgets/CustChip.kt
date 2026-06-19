@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codewiththiru.platform.designsystem.components.CustText

@Composable
fun CustAssistChip(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    AssistChip(
        onClick = onClick,
        label = { CustText(text = label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = icon,
        colors = CustChipDefaults.assistChipColors()
    )
}

@Composable
fun CustFilterChip(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true
) {
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { CustText(text = label) },
        modifier = modifier,
        enabled = enabled,
        leadingIcon = icon,
        colors = CustChipDefaults.filterChipColors()
    )
}
