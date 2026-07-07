package com.codewiththiru.platform.moreapps.domain.model

data class MoreAppModel(
    val id: String,
    val packageName: String,
    val title: String,
    val description: String,
    val iconUrl: String,
    val bannerUrl: String? = null,
    val rating: Float? = null,
    val downloadCount: String? = null,
    val categories: List<MoreAppsCategory> = emptyList(),
    val isFeatured: Boolean = false,
    val installStatus: InstallStatus = InstallStatus.Unknown,
    val source: MoreAppsSource = MoreAppsSource.Manual,
)
