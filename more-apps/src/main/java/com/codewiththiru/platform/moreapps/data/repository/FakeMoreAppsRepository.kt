package com.codewiththiru.platform.moreapps.data.repository

import com.codewiththiru.platform.moreapps.domain.model.MoreAppModel
import com.codewiththiru.platform.moreapps.domain.model.MoreAppsResult
import com.codewiththiru.platform.moreapps.domain.repository.MoreAppsRepository

class FakeMoreAppsRepository : MoreAppsRepository {
    var appsResult: MoreAppsResult = MoreAppsResult.Empty
    var appsByPage: MutableMap<Int, MoreAppsResult> = mutableMapOf()

    override suspend fun getApps(): MoreAppsResult = appsResult

    override suspend fun getAppsPage(page: Int, pageSize: Int): MoreAppsResult {
        return appsByPage[page] ?: MoreAppsResult.Empty
    }

    override suspend fun getFeaturedApps(): MoreAppsResult = appsResult

    override suspend fun searchApps(query: String): MoreAppsResult = appsResult

    override suspend fun getAppsByCategory(category: String): MoreAppsResult = appsResult
}
