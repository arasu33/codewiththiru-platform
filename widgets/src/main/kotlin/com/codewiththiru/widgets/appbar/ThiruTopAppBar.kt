@file:Suppress("FunctionNaming", "LongParameterList", "MaxLineLength")

package com.codewiththiru.widgets.appbar

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

/**
 * Legacy branded alias for [PlatformTopAppBar].
 *
 * @deprecated Use [PlatformTopAppBar] instead for generic and customizable branding.
 */
@Deprecated(
    message = "Use PlatformTopAppBar instead for generic branding.",
    replaceWith =
        ReplaceWith(
            "PlatformTopAppBar(title, modifier, subtitle, navigationIcon, " +
                "navigationIconContentDescription, onNavigationIconClick, actions, colors)",
            "com.codewiththiru.widgets.appbar.PlatformTopAppBar",
        ),
)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThiruTopAppBar(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    navigationIcon: ImageVector? = null,
    navigationIconContentDescription: String? = "Navigate back",
    onNavigationIconClick: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors(),
) {
    PlatformTopAppBar(
        title = title,
        modifier = modifier,
        subtitle = subtitle,
        navigationIcon = navigationIcon,
        navigationIconContentDescription = navigationIconContentDescription,
        onNavigationIconClick = onNavigationIconClick,
        actions = actions,
        colors = colors,
    )
}
