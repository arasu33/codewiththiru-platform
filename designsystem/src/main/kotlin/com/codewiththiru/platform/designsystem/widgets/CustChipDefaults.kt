package com.codewiththiru.platform.designsystem.widgets

import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.SelectableChipColors
import androidx.compose.runtime.Composable

/**
 * Token resolutions orchestrating internal state structures inside [CustAssistChip] and [CustFilterChip].
 */
object CustChipDefaults {
    @Composable
    fun assistChipColors(): ChipColors = AssistChipDefaults.assistChipColors()

    @Composable
    fun filterChipColors(): SelectableChipColors = FilterChipDefaults.filterChipColors()
}
