package com.codewiththiru.onboarding.model

import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Data model defining a single page/slide in an onboarding walkthrough.
 */
data class OnboardingPage(
    val id: String,
    val title: String,
    val description: String,
    val icon: ImageVector? = null,
    val tag: String? = null,
)
