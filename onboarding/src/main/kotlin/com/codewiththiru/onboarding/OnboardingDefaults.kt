package com.codewiththiru.onboarding

import com.codewiththiru.onboarding.model.OnboardingPage

/**
 * Standard fallbacks and defaults for the Onboarding module.
 */
object OnboardingDefaults {
    /**
     * Default set of onboarding pages suitable as a generic template.
     */
    fun defaultPages(): List<OnboardingPage> =
        listOf(
            OnboardingPage(
                id = "welcome",
                title = "Welcome",
                description = "Discover powerful features designed to simplify and accelerate your daily workflow.",
            ),
            OnboardingPage(
                id = "customize",
                title = "Fully Customizable",
                description = "Tailor every feature and setting to match your exact personal and professional needs.",
            ),
            OnboardingPage(
                id = "get_started",
                title = "Ready to Begin",
                description = "Jump right in and start exploring. You can adjust your preferences anytime in Settings.",
            ),
        )
}
