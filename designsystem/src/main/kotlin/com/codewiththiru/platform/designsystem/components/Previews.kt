@file:Suppress("FunctionNaming", "UnusedPrivateMember")

package com.codewiththiru.platform.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.codewiththiru.platform.designsystem.preview.DarkPreview
import com.codewiththiru.platform.designsystem.preview.FoldablePreview
import com.codewiththiru.platform.designsystem.preview.FontScalePreview
import com.codewiththiru.platform.designsystem.preview.LandscapePreview
import com.codewiththiru.platform.designsystem.preview.LightPreview
import com.codewiththiru.platform.designsystem.preview.RtlPreview
import com.codewiththiru.platform.designsystem.preview.TabletPreview
import com.codewiththiru.platform.designsystem.theme.CustTheme

@LightPreview
@DarkPreview
@FontScalePreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustTextPreview() {
    CustTheme {
        CustText(text = "Hello Design System")
    }
}

@LightPreview
@DarkPreview
@Composable
private fun CustButtonPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
            CustButton(onClick = {}) { CustText("Filled") }
            CustButton(onClick = {}, loading = true) { CustText("Loading") }
            CustButton(onClick = {}, enabled = false) { CustText("Disabled") }
            CustOutlinedButton(onClick = {}) { CustText("Outlined") }
            CustTextButton(onClick = {}) { CustText("Text") }
        }
    }
}

@LightPreview
@DarkPreview
@Composable
private fun CustCardPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
            CustCard(onClick = {}) { CustText("Filled Card", modifier = Modifier.padding(16.dp)) }
            CustOutlinedCard(onClick = {}) { CustText("Outlined Card", modifier = Modifier.padding(16.dp)) }
            CustElevatedCard(onClick = {}) { CustText("Elevated Card", modifier = Modifier.padding(16.dp)) }
        }
    }
}

@LightPreview
@DarkPreview
@RtlPreview
@FontScalePreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustTextFieldPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
            CustTextField(value = "Input", onValueChange = {}, label = { CustText("Label") })
            CustTextField(
                value = "Error",
                onValueChange = {},
                errorText = "Invalid input",
                helperText = "Helper",
                maxLength = 10,
            )
        }
    }
}

@LightPreview
@DarkPreview
@Composable
private fun CustPasswordTextFieldPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(16.dp)) {
            CustPasswordTextField(value = "password123", onValueChange = {})
            CustPasswordTextField(value = "password123", onValueChange = {}, initiallyVisible = true)
        }
    }
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@LightPreview
@DarkPreview
@RtlPreview
@TabletPreview
@FoldablePreview
@Composable
private fun CustTopBarPreview() {
    CustTheme {
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            CustTopBar(
                title = { CustText("Standard TopBar") },
            )
            CustTopBar(
                title = { CustText("With Subtitle") },
                subtitle = { CustText("This is a subtitle") },
            )
            CustCenterAlignedTopBar(
                title = { CustText("Center Aligned") },
                subtitle = { CustText("Subtitle here") },
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
private fun CustAlertDialogPreview() {
    CustTheme {
        CustAlertDialog(
            title = "Delete Account?",
            message = "This action cannot be undone. All your data will be permanently deleted.",
            confirmButtonText = "Delete",
            dismissButtonText = "Cancel",
            onConfirm = {},
            onDismiss = {},
            isDestructive = true,
        )
    }
}
