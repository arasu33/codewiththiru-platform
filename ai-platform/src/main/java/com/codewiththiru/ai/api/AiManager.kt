package com.codewiththiru.ai.api

import kotlinx.coroutines.flow.StateFlow

interface AiManager {
    val state: StateFlow<AiState>
    
    suspend fun initialize()
    suspend fun generateText(prompt: String): AiResult<String>
}
