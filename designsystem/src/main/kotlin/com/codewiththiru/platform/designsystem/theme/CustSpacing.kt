package com.codewiththiru.platform.designsystem.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import com.codewiththiru.platform.designsystem.tokens.SpacingTokens

@Immutable
data class CustSpacing(
    val none: Dp = SpacingTokens.None,
    val extraSmall: Dp = SpacingTokens.ExtraSmall,
    val small: Dp = SpacingTokens.Small,
    val medium: Dp = SpacingTokens.Medium,
    val large: Dp = SpacingTokens.Large,
    val extraLarge: Dp = SpacingTokens.ExtraLarge,
    val extraExtraLarge: Dp = SpacingTokens.ExtraExtraLarge,
    val extraExtraExtraLarge: Dp = SpacingTokens.ExtraExtraExtraLarge,
)

val LocalCustSpacing = staticCompositionLocalOf { CustSpacing() }
