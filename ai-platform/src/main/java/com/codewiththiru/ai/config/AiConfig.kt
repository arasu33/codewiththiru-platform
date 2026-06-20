package com.codewiththiru.ai.config

import com.codewiththiru.ai.api.AiEnvironment

data class AiConfig(
    val environment: AiEnvironment = AiEnvironment.Cloud,
    val primaryProvider: String = "gemini",
    val fallbackProvider: String = "openai",
    val timeoutMs: Long = 30000L
)
