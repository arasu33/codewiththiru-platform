package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object CustSectionHeaderDefaults {
    val horizontalPadding: Dp = 16.dp
    val verticalPadding: Dp = 16.dp
    val subtitleTopPadding: Dp = 4.dp

    val titleColor: Color
        @Composable get() = MaterialTheme.colorScheme.primary

    val subtitleColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val titleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium

    val subtitleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium
}
