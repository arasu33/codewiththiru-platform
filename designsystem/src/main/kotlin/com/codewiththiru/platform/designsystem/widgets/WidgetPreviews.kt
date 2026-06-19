@file:Suppress("FunctionNaming", "UnusedPrivateMember")

package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.components.CustText
import com.codewiththiru.platform.designsystem.preview.DarkPreview
import com.codewiththiru.platform.designsystem.preview.FoldablePreview
import com.codewiththiru.platform.designsystem.preview.LandscapePreview
import com.codewiththiru.platform.designsystem.preview.LightPreview
import com.codewiththiru.platform.designsystem.preview.RtlPreview
import com.codewiththiru.platform.designsystem.preview.TabletPreview
import com.codewiththiru.platform.designsystem.theme.CustTheme

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@LandscapePreview
@Composable
private fun CustLoadingPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustLoading()
            CustLoading(message = "Loading your data...")
            CustLoading(type = LoadingType.Linear)
            CustLoading(type = LoadingType.Linear, message = "Processing...")
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@LandscapePreview
@Composable
private fun CustShimmerPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustShimmerBox(modifier = Modifier.width(200.dp).height(24.dp))
            CustShimmerBox(modifier = Modifier.width(150.dp).height(24.dp))
            CustShimmerBox(modifier = Modifier.width(100.dp).height(24.dp))
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@LandscapePreview
@Composable
private fun CustEmptyStatePreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustEmptyState(
                title = "No Data Found",
                message = "We couldn't find any items matching your criteria.",
                actionButton = {
                    com.codewiththiru.platform.designsystem.components.CustButton(onClick = {}) {
                        com.codewiththiru.platform.designsystem.components.CustText("Clear Filters")
                    }
                }
            )
            CustEmptyState(
                title = "Simply Empty",
                illustration = { CustShimmerBox(modifier = Modifier.width(120.dp).height(120.dp)) }
            )
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@LandscapePreview
@Composable
private fun CustErrorStatePreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustErrorState(
                title = "Connection Failed",
                message = "Please check your network and try again.",
                errorCode = "ERR_NET_01",
                onRetry = {}
            )
            CustErrorState(
                title = "Fatal Error",
                message = "Something went terribly wrong."
            )
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustBadgePreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustBadge(count = 1) { CustText("Single Notification") }
            CustBadge(count = 99) { CustText("Max Notifications") }
            CustBadge(count = 150) { CustText("Over limit") }
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustChipPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            CustAssistChip(label = "Enabled Assist", onClick = {})
            CustAssistChip(label = "Disabled Assist", onClick = {}, enabled = false)
            CustFilterChip(label = "Selected Filter", onClick = {}, selected = true)
            CustFilterChip(label = "Unselected Filter", onClick = {}, selected = false)
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustAvatarPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp), modifier = Modifier.padding(16.dp)) {
            // Initials
            CustAvatar(initials = "JD", size = AvatarSize.Small)
            CustAvatar(initials = "TH", size = AvatarSize.Medium)
            CustAvatar(initials = "WK", size = AvatarSize.Large)
            
            // Placeholder
            CustAvatar(size = AvatarSize.Medium)
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustSectionHeaderPreview() {
    CustTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CustSectionHeader(title = "Settings")
            CustSectionHeader(title = "About", subtitle = "Version 1.0.0")
            CustSectionHeader(
                title = "Updates",
                action = { CustText("View All") }
            )
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustInfoRowPreview() {
    CustTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            CustInfoRow(label = "Version", value = "1.0.0")
            CustInfoRow(
                label = "Developer",
                value = "CodeWithThiru",
                icon = { CustShimmerBox(modifier = Modifier.width(24.dp).height(24.dp)) }
            )
            CustInfoRow(
                label = "Privacy Policy",
                onClick = {},
                trailingContent = { CustText(">") }
            )
            CustInfoRow(
                label = "Rate App",
                onClick = {}
            )
        }
    }
}
