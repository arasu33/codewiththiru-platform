package com.codewiththiru.platform.coupons.presentation.entry.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun CouponErrorDialog(
    reason: String,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Invalid Code") },
        text = { Text(reason) },
        confirmButton = {
            Button(onClick = onDismiss) {
                Text("Try Again")
            }
        },
    )
}
