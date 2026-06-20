package com.codewiththiru.platform.growth.onboarding

data class OnboardingStep(
    val id: String,
    val isRequired: Boolean = true,
    val contentId: String? = null
)
