@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProvideTextStyle
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = CustTopBarDefaults.topAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    TopAppBar(
        title = {
            TopBarTitleLayout(title = title, subtitle = subtitle)
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustCenterAlignedTopBar(
    title: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: @Composable (() -> Unit)? = null,
    navigationIcon: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    windowInsets: WindowInsets = TopAppBarDefaults.windowInsets,
    colors: TopAppBarColors = CustTopBarDefaults.centerAlignedTopAppBarColors(),
    scrollBehavior: TopAppBarScrollBehavior? = null,
) {
    CenterAlignedTopAppBar(
        title = {
            TopBarTitleLayout(title = title, subtitle = subtitle)
        },
        modifier = modifier,
        navigationIcon = navigationIcon,
        actions = actions,
        windowInsets = windowInsets,
        colors = colors,
        scrollBehavior = scrollBehavior,
    )
}

@Composable
private fun TopBarTitleLayout(
    title: @Composable () -> Unit,
    subtitle: @Composable (() -> Unit)?,
) {
    if (subtitle == null) {
        ProvideTextStyle(MaterialTheme.typography.titleLarge) {
            title()
        }
    } else {
        Column {
            ProvideTextStyle(MaterialTheme.typography.titleLarge) {
                title()
            }
            ProvideTextStyle(MaterialTheme.typography.titleSmall) {
                subtitle()
            }
        }
    }
}
