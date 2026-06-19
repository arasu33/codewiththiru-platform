package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default metric tokens structuring visual bindings for [CustInfoRow].
 */
object CustInfoRowDefaults {
    val horizontalPadding: Dp = 16.dp
    val verticalPadding: Dp = 16.dp
    val iconSpacing: Dp = 16.dp
    val labelValueSpacing: Dp = 8.dp

    val labelColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val valueColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val iconColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val labelStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyLarge

    val valueStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium
}
