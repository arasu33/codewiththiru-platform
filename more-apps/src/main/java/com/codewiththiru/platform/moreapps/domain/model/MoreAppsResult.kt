package com.codewiththiru.platform.moreapps.domain.model

sealed interface MoreAppsResult {
    data class Success(val data: List<MoreAppModel>) : MoreAppsResult
    object Loading : MoreAppsResult
    data class Error(val throwable: Throwable) : MoreAppsResult
    object Empty : MoreAppsResult
}
