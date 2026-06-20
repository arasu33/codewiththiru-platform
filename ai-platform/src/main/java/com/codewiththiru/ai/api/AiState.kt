package com.codewiththiru.ai.api

sealed class AiState {
    object Uninitialized : AiState()
    object Initializing : AiState()
    object Ready : AiState()
    data class Error(val message: String) : AiState()
}
