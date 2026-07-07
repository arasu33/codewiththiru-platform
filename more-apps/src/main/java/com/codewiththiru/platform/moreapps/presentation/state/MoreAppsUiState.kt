package com.codewiththiru.platform.moreapps.presentation.state

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel

sealed interface MoreAppsUiState {
    object Loading : MoreAppsUiState

    data class Success(
        val apps: List<MoreAppModel>,
    ) : MoreAppsUiState

    data class Error(
        val message: String,
    ) : MoreAppsUiState

    object Empty : MoreAppsUiState
}
