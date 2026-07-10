package com.codewiththiru.platform.moreapps.presentation.integration

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

interface MoreAppsImageProvider {
    @Suppress("FunctionNaming")
    @Composable
    fun LoadImage(
        url: String,
        contentDescription: String?,
        modifier: Modifier,
    )
}
