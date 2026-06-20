package com.codewiththiru.platform.about.state

import com.codewiththiru.platform.about.model.AboutConfig

/**
 * Defines the UI state lifecycle bounds for the About module.
 */
sealed interface AboutUiState {
    data object Loading : AboutUiState
    data class Success(val config: AboutConfig) : AboutUiState
    data class Error(val message: String) : AboutUiState
}
