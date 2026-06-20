package com.codewiththiru.platform.moreapps.presentation.state

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel

sealed interface MoreAppsEffect {
    data class OpenStore(val app: MoreAppModel) : MoreAppsEffect
    data class ShareApp(val app: MoreAppModel) : MoreAppsEffect
    object NavigateBack : MoreAppsEffect
    data class ShowError(val message: String) : MoreAppsEffect
}
