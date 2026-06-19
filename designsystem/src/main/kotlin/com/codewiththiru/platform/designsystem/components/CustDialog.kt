@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.designsystem.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.DialogProperties

@Composable
fun CustDialog(
    onDismissRequest: () -> Unit,
    confirmButton: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    dismissButton: @Composable (() -> Unit)? = null,
    icon: @Composable (() -> Unit)? = null,
    title: @Composable (() -> Unit)? = null,
    content: @Composable (() -> Unit)? = null,
    properties: DialogProperties = CustDialogDefaults.properties()
) {
    AlertDialog(
        onDismissRequest = onDismissRequest,
        confirmButton = confirmButton,
        modifier = modifier,
        dismissButton = dismissButton,
        icon = icon,
        title = title,
        text = content,
        shape = CustDialogDefaults.shape,
        containerColor = CustDialogDefaults.containerColor,
        iconContentColor = CustDialogDefaults.iconContentColor,
        titleContentColor = CustDialogDefaults.titleContentColor,
        textContentColor = CustDialogDefaults.textContentColor,
        tonalElevation = CustDialogDefaults.tonalElevation,
        properties = properties
    )
}

@Composable
fun CustAlertDialog(
    title: String,
    message: String,
    confirmButtonText: String,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    dismissButtonText: String? = null,
    icon: @Composable (() -> Unit)? = null,
    isDestructive: Boolean = false,
    properties: DialogProperties = CustDialogDefaults.properties()
) {
    CustDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        icon = icon,
        title = {
            CustText(text = title)
        },
        content = {
            CustText(text = message)
        },
        confirmButton = {
            CustTextButton(
                onClick = onConfirm,
                colors = if (isDestructive) {
                    ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error)
                } else {
                    CustButtonDefaults.textButtonColors()
                }
            ) {
                CustText(text = confirmButtonText)
            }
        },
        dismissButton = dismissButtonText?.let { text ->
            {
                CustTextButton(
                    onClick = onDismiss,
                    colors = CustButtonDefaults.textButtonColors()
                ) {
                    CustText(text = text)
                }
            }
        },
        properties = properties
    )
}
