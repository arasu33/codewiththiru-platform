@file:Suppress("MagicNumber", "MaxLineLength")

package com.codewiththiru.platform.feedback.model

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class FeedbackVisibility(
    val requireEmail: Boolean = false,
    val showNameField: Boolean = true,
    val allowAttachments: Boolean = true,
    val showCategorySelector: Boolean = true
)

data class FeedbackAttachmentsConfig(
    val maxAttachments: Int = 3,
    val maxSizeBytes: Long = 5 * 1024 * 1024, // 5MB
    val allowedTypes: List<AttachmentType> = listOf(AttachmentType.SCREENSHOT, AttachmentType.LOG, AttachmentType.DIAGNOSTICS)
)

data class FeedbackValidationConfig(
    val maxSubjectLength: Int = 100,
    val maxDescriptionLength: Int = 1000,
    val emailRegex: Regex = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}\$")
)

data class FeedbackOfflineConfig(
    val autoSaveDrafts: Boolean = true,
    val clearDraftOnSuccess: Boolean = true
)

data class FeedbackPrivacyConfig(
    val redactPii: Boolean = true,
    val dataRetentionDays: Int = 30
)

data class DraftStorageConfig(
    val encrypted: Boolean = true,
    val migratePlaintext: Boolean = true
)

data class FeedbackThemeConfig(
    val headerStyle: TextStyle? = null,
    val labelStyle: TextStyle? = null,
    val inputStyle: TextStyle? = null,
    val cardElevation: Dp = 1.dp
)

@ConsistentCopyVisibility
data class FeedbackConfig private constructor(
    val categories: List<FeedbackCategory>,
    val visibility: FeedbackVisibility,
    val themeConfig: FeedbackThemeConfig,
    val attachmentsConfig: FeedbackAttachmentsConfig,
    val validationConfig: FeedbackValidationConfig,
    val offlineConfig: FeedbackOfflineConfig,
    val privacyConfig: FeedbackPrivacyConfig,
    val draftStorageConfig: DraftStorageConfig
) {
    class Builder {
        private var categories: List<FeedbackCategory> = emptyList()
        private var visibility: FeedbackVisibility = FeedbackVisibility()
        private var themeConfig: FeedbackThemeConfig = FeedbackThemeConfig()
        private var attachmentsConfig: FeedbackAttachmentsConfig = FeedbackAttachmentsConfig()
        private var validationConfig: FeedbackValidationConfig = FeedbackValidationConfig()
        private var offlineConfig: FeedbackOfflineConfig = FeedbackOfflineConfig()
        private var privacyConfig: FeedbackPrivacyConfig = FeedbackPrivacyConfig()
        private var draftStorageConfig: DraftStorageConfig = DraftStorageConfig()

        fun setCategories(categories: List<FeedbackCategory>) = apply { this.categories = categories }
        fun setVisibility(visibility: FeedbackVisibility) = apply { this.visibility = visibility }
        fun setThemeConfig(themeConfig: FeedbackThemeConfig) = apply { this.themeConfig = themeConfig }
        fun setAttachmentsConfig(attachmentsConfig: FeedbackAttachmentsConfig) = apply { this.attachmentsConfig = attachmentsConfig }
        fun setValidationConfig(validationConfig: FeedbackValidationConfig) = apply { this.validationConfig = validationConfig }
        fun setOfflineConfig(offlineConfig: FeedbackOfflineConfig) = apply { this.offlineConfig = offlineConfig }
        fun setPrivacyConfig(privacyConfig: FeedbackPrivacyConfig) = apply { this.privacyConfig = privacyConfig }
        fun setDraftStorageConfig(draftStorageConfig: DraftStorageConfig) = apply { this.draftStorageConfig = draftStorageConfig }

        fun build(): FeedbackConfig {
            require(categories.isNotEmpty()) { "At least one FeedbackCategory must be provided" }
            return FeedbackConfig(
                categories = categories,
                visibility = visibility,
                themeConfig = themeConfig,
                attachmentsConfig = attachmentsConfig,
                validationConfig = validationConfig,
                offlineConfig = offlineConfig,
                privacyConfig = privacyConfig,
                draftStorageConfig = draftStorageConfig
            )
        }
    }
}
