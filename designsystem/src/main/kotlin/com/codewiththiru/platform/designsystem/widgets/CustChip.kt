@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.AssistChip
import androidx.compose.material3.FilterChip
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.codewiththiru.platform.designsystem.components.CustText

/**
 * Assist chip configuration assisting generic interactions and user workflows dynamically.
 *
 * @param label Text sequence evaluated natively inside the chip geometry.
 * @param onClick Trigger pipeline whenever explicit bound taps are recognized.
 * @param modifier Applied structure properties bridging parent elements.
 * @param icon Icon closure mapping optional start geometry.
 * @param enabled Controls the interactive state of the chip.
 */
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

/**
 * Filter chip configuration enforcing binary selected states.
 * 
 * @param label Text sequence evaluated natively inside the chip geometry.
 * @param selected Controls active visual weighting spanning color mappings defined by [CustChipDefaults].
 * @param onClick Trigger pipeline whenever explicit bound taps are recognized.
 * @param modifier Applied structure properties bridging parent elements.
 * @param icon Icon closure mapping optional start geometry.
 * @param enabled Controls the interactive state of the chip.
 */
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
