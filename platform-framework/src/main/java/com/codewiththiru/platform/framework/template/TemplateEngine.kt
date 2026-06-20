package com.codewiththiru.platform.framework.template

data class AppTemplate(
    val templateId: String,
    val templateType: Type,
    val defaultFeatures: List<String>
) {
    enum class Type {
        LEARNING, GAME, UTILITY, AI, SUBSCRIPTION, HYBRID
    }
}

interface TemplateManager {
    fun applyTemplate(appId: String, templateId: String)
}

interface TemplateRegistry {
    fun registerTemplate(template: AppTemplate)
    fun getTemplate(templateId: String): AppTemplate?
}

interface TemplateGenerator {
    fun generateAppFromTemplate(template: AppTemplate): String
}
