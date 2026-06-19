package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Default internal values bound to the [CustErrorState] architecture.
 */
object CustErrorStateDefaults {
    val iconSpacing: Dp = 16.dp
    val titleSpacing: Dp = 8.dp
    val errorCodeSpacing: Dp = 8.dp
    val retrySpacing: Dp = 24.dp

    val iconColor: Color
        @Composable get() = MaterialTheme.colorScheme.error

    val titleColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurface

    val messageColor: Color
        @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant

    val errorCodeColor: Color
        @Composable get() = MaterialTheme.colorScheme.error

    val titleStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleLarge

    val messageStyle: TextStyle
        @Composable get() = MaterialTheme.typography.bodyMedium

    val errorCodeStyle: TextStyle
        @Composable get() = MaterialTheme.typography.labelSmall
}
