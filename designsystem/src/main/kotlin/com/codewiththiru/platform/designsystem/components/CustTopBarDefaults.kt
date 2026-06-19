package com.codewiththiru.platform.designsystem.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
object CustTopBarDefaults {
    @Composable
    fun topAppBarColors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors()

    @Composable
    fun centerAlignedTopAppBarColors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors()
}
