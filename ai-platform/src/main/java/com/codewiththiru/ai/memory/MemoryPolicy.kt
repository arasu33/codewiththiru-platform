package com.codewiththiru.ai.memory

data class MemoryPolicy(
    val retentionPeriodDays: Int,
    val maxMemoryItems: Int
)
