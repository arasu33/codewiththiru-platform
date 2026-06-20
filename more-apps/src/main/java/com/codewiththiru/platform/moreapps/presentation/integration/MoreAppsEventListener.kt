package com.codewiththiru.platform.moreapps.presentation.integration

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel

interface MoreAppsEventListener {
    fun onAppAction(app: MoreAppModel, action: AppActionType)
    fun onInstallError(app: MoreAppModel, error: Throwable)
    fun onNavigateBack()
}
