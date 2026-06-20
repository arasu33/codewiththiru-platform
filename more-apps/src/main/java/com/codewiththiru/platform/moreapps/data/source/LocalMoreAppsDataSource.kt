package com.codewiththiru.platform.moreapps.data.source

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel

interface LocalMoreAppsDataSource : MoreAppsDataSource {
    suspend fun saveApps(apps: List<MoreAppModel>)
}
