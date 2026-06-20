package com.codewiththiru.ai.repository

import com.codewiththiru.ai.api.AiState
import kotlinx.coroutines.flow.StateFlow

interface AiRepository {
    val state: StateFlow<AiState>
    suspend fun initialize()
}
