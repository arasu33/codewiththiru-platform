package com.codewiththiru.platform.moreapps.presentation.integration

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel

interface MoreAppsNavigationProvider {
    fun openStore(app: MoreAppModel)
    fun shareApp(app: MoreAppModel)
}
