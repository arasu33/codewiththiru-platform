package com.codewiththiru.platform.rating.model

import com.codewiththiru.platform.rating.policy.RatingCooldownPolicy
import com.codewiththiru.platform.rating.policy.RatingTriggerRules

/**
 * Defines the core configuration for the Rating Engine.
 */
data class RatingConfig private constructor(
    val triggerRules: RatingTriggerRules,
    val cooldownPolicy: RatingCooldownPolicy,
    val uiCustomization: RatingUiCustomization,
    val promptType: RatingPromptType,
    val playReviewThreshold: Int,
    val enableRemoteOverrides: Boolean
) {
    class Builder {
        private var triggerRules = RatingTriggerRules()
        private var cooldownPolicy = RatingCooldownPolicy()
        private var uiCustomization = RatingUiCustomization()
        private var promptType = RatingPromptType.Dialog
        private var playReviewThreshold = 5
        private var enableRemoteOverrides = false

        fun setTriggerRules(rules: RatingTriggerRules) = apply { this.triggerRules = rules }
        fun setCooldownPolicy(policy: RatingCooldownPolicy) = apply { this.cooldownPolicy = policy }
        fun setUiCustomization(customization: RatingUiCustomization) = apply { this.uiCustomization = customization }
        fun setPromptType(type: RatingPromptType) = apply { this.promptType = type }
        fun setPlayReviewThreshold(threshold: Int) = apply { this.playReviewThreshold = threshold }
        fun setEnableRemoteOverrides(enable: Boolean) = apply { this.enableRemoteOverrides = enable }

        fun build(): RatingConfig {
            return RatingConfig(
                triggerRules = triggerRules,
                cooldownPolicy = cooldownPolicy,
                uiCustomization = uiCustomization,
                promptType = promptType,
                playReviewThreshold = playReviewThreshold,
                enableRemoteOverrides = enableRemoteOverrides
            )
        }
    }
}
