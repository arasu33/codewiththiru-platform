package com.codewiththiru.platform.developer.release

data class ReleaseChecklist(
    val id: String,
    val items: List<String>,
    val isComplete: Boolean = false
)
