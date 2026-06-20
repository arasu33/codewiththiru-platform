package com.codewiththiru.platform.developer.scaffolding

data class ScaffoldTemplate(
    val id: String,
    val name: String,
    val description: String,
    val files: List<String>
)
