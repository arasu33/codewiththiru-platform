package com.codewiththiru.platform.designsystem.components

import androidx.compose.material3.AlertDialogDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.window.DialogProperties

object CustDialogDefaults {
    val shape: Shape
        @Composable get() = AlertDialogDefaults.shape

    val containerColor: Color
        @Composable get() = AlertDialogDefaults.containerColor

    val iconContentColor: Color
        @Composable get() = AlertDialogDefaults.iconContentColor

    val titleContentColor: Color
        @Composable get() = AlertDialogDefaults.titleContentColor

    val textContentColor: Color
        @Composable get() = AlertDialogDefaults.textContentColor

    val tonalElevation: Dp = AlertDialogDefaults.TonalElevation

    fun properties(): DialogProperties = DialogProperties()
}
