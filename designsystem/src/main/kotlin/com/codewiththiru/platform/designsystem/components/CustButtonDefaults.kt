package com.codewiththiru.platform.designsystem.components

import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable

object CustButtonDefaults {
    @Composable
    fun buttonColors(): ButtonColors = ButtonDefaults.buttonColors()

    @Composable
    fun outlinedButtonColors(): ButtonColors = ButtonDefaults.outlinedButtonColors()

    @Composable
    fun textButtonColors(): ButtonColors = ButtonDefaults.textButtonColors()
}
