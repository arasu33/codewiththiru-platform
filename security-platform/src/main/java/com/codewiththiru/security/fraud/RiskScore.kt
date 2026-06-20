package com.codewiththiru.security.fraud

data class RiskScore(
    val score: Double,
    val level: Level
) {
    enum class Level {
        LOW,
        MEDIUM,
        HIGH,
        CRITICAL
    }
}
