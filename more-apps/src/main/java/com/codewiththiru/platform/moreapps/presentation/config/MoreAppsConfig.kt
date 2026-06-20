package com.codewiththiru.platform.moreapps.presentation.config

data class MoreAppsConfig(
    val displayType: MoreAppsDisplayType = MoreAppsDisplayType.Grid,
    val enableSearch: Boolean = true,
    val enableCategories: Boolean = true,
    val cachePolicy: CachePolicy = CachePolicy.CacheFirst,
    val emptyStateConfig: MoreAppsEmptyStateConfig
)
