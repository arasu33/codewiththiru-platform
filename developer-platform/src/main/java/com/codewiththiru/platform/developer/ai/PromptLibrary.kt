package com.codewiththiru.platform.developer.ai

data class PromptLibrary(
    val templates: Map<String, String> = mapOf(
        "code_review" to "Review the following Kotlin code for SOLID principles:\n%s",
        "arch_review" to "Analyze the module architecture for coupling:\n%s"
    )
)
