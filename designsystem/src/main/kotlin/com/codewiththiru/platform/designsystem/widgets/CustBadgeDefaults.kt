package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle

/**
 * Default internal values bound to the [CustBadge] architecture.
 */
object CustBadgeDefaults {
    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.error

    val contentColor: Color
        @Composable get() = MaterialTheme.colorScheme.onError

    val textStyle: TextStyle
        @Composable get() = MaterialTheme.typography.labelSmall
}
