package com.codewiththiru.platform.moreapps.presentation.integration

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel
import com.codewiththiru.platform.moreapps.domain.model.MoreAppsCategory

interface MoreAppsAnalyticsProvider {
    fun logScreenView(screenName: String)
    fun logAppAction(app: MoreAppModel, action: AppActionType, position: Int, section: String)
    fun logSearchPerformed(query: String)
    fun logCategorySelected(category: MoreAppsCategory)
}
