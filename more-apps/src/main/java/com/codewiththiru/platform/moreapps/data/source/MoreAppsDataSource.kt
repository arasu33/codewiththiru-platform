package com.codewiththiru.platform.moreapps.data.source

import com.codewiththiru.platform.moreapps.domain.model.MoreAppsResult

interface MoreAppsDataSource {
    suspend fun getApps(): MoreAppsResult
}
