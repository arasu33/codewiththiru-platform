package com.codewiththiru.platform.growth.activation

data class ActivationScore(
    val userId: String,
    val score: Int,
    val level: ActivationLevel
)

enum class ActivationLevel {
    NEW,
    WARM,
    ACTIVE,
    POWER_USER
}
