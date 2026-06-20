package com.codewiththiru.ads.nativead

import androidx.compose.ui.graphics.Color

/**
 * Defines the style and appearance of the Native Ad.
 * This allows the platform to white-label ads to match the app's theme.
 */
data class NativeAdTemplate(
    val backgroundColor: Color = Color.White,
    val headlineColor: Color = Color.Black,
    val bodyColor: Color = Color.DarkGray,
    val callToActionBackgroundColor: Color = Color.Blue,
    val callToActionTextColor: Color = Color.White,
    val type: TemplateType = TemplateType.SMALL
) {
    enum class TemplateType {
        SMALL,  // E.g., for list items
        MEDIUM  // E.g., for feed items or articles
    }
}
