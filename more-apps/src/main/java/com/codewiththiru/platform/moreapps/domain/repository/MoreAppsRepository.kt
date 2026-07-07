package com.codewiththiru.platform.moreapps.domain.repository

import com.codewiththiru.platform.moreapps.domain.model.MoreAppsResult

interface MoreAppsRepository {
    suspend fun getApps(): MoreAppsResult

    suspend fun getAppsPage(
        page: Int,
        pageSize: Int,
    ): MoreAppsResult

    suspend fun getFeaturedApps(): MoreAppsResult

    suspend fun searchApps(query: String): MoreAppsResult

    suspend fun getAppsByCategory(category: String): MoreAppsResult
}
