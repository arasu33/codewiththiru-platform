package com.codewiththiru.governance.accessibility

interface AccessibilityValidator {
    suspend fun validate(moduleName: String): AccessibilityAudit
}

data class AccessibilityRule(
    val id: String,
    val category: Category,
    val passed: Boolean,
    val failureReason: String? = null
) {
    enum class Category {
        WCAG, TALKBACK, RTL, LARGE_FONT, FOLDABLE, TABLET
    }
}

data class AccessibilityAudit(
    val moduleName: String,
    val totalRulesChecked: Int,
    val rulesFailed: Int,
    val passed: Boolean,
    val ruleResults: List<AccessibilityRule>
)
