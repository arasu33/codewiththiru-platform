package com.codewiththiru.platform.developer.generator

data class GenerationContext(
    val packageName: String,
    val moduleName: String,
    val featureName: String,
    val additionalArgs: Map<String, String> = emptyMap()
)
