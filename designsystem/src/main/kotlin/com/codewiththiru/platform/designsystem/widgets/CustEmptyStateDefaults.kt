package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

object CustEmptyStateDefaults {
    val illustrationSpacing: Dp = 24.dp
    val titleSpacing: Dp = 8.dp
    val actionSpacing: Dp = 24.dp

    val titleColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val messageColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val titleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleLarge

    val messageStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium
}
