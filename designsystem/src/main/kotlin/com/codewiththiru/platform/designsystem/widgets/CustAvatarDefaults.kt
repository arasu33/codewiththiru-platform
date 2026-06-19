package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

enum class AvatarSize(val dimension: Dp) {
    Small(32.dp),
    Medium(48.dp),
    Large(72.dp)
}

/**
 * Default metric tokens structuring visual bindings for [CustAvatar].
 */
object CustAvatarDefaults {
    val containerColor: Color
        @Composable get() = MaterialTheme.colorScheme.primaryContainer

    val contentColor: Color
        @Composable get() = MaterialTheme.colorScheme.onPrimaryContainer

    val initialsStyleSmall: TextStyle
        @Composable get() = MaterialTheme.typography.labelSmall

    val initialsStyleMedium: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium

    val initialsStyleLarge: TextStyle
        @Composable get() = MaterialTheme.typography.headlineMedium
}
