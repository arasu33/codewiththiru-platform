package com.codewiththiru.platform.growth.onboarding

data class OnboardingFlow(
    val id: String,
    val steps: List<OnboardingStep>,
    val policy: OnboardingPolicy = OnboardingPolicy.DEFAULT
)
