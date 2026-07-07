package com.codewiththiru.platform.game.events.priority

enum class EventPriority(
    val level: Int,
) {
    CRITICAL(100),
    HIGH(75),
    NORMAL(50),
    LOW(25),
}
