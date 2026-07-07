@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp

/**
 * A horizontal dividing line mapping standard structural demarcations in lists or bounding blocks.
 *
 * @param modifier Structure constraints.
 * @param thickness Stroke height natively resolving across DPI scales.
 * @param color Overriding brush color. Defaults securely to the Material3 outline variant.
 */
@Composable
fun CustHorizontalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    HorizontalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}

/**
 * A vertical dividing line mapping standard structural demarcations in grids or flex rows.
 *
 * @param modifier Structure constraints.
 * @param thickness Stroke width natively resolving across DPI scales.
 * @param color Overriding brush color. Defaults securely to the Material3 outline variant.
 */
@Composable
fun CustVerticalDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) {
    VerticalDivider(
        modifier = modifier,
        thickness = thickness,
        color = color,
    )
}
