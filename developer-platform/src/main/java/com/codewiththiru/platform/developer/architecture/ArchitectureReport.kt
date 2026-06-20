package com.codewiththiru.platform.developer.architecture

data class ArchitectureReport(
    val moduleName: String,
    val score: ArchitectureScore,
    val violations: List<String>
)
