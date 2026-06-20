package com.codewiththiru.ai.platform.api

import kotlinx.coroutines.flow.StateFlow

data class AIConfiguration(
    val environment: AIEnvironment,
    val primaryProvider: AIProvider,
    val fallbackProviders: List<AIProvider>
)

enum class AIEnvironment { DEV, PROD }
enum class AIProvider { OPENAI, GEMINI, CLAUDE, OLLAMA, LOCAL_LLM }

sealed class AIState {
    object Idle : AIState()
    object Processing : AIState()
    data class Completed(val result: AIResult) : AIState()
    data class Error(val error: Throwable) : AIState()
}

sealed class AIResult {
    data class Success(val response: String, val metadata: Map<String, String>) : AIResult()
    data class Failure(val reason: String) : AIResult()
}

interface AIPlatformManager {
    val state: StateFlow<AIState>
    fun initialize(config: AIConfiguration)
    suspend fun processRequest(prompt: String): AIResult
}
