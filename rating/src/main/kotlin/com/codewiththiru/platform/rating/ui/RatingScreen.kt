@file:Suppress("FunctionNaming")

package com.codewiththiru.platform.rating.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.codewiththiru.platform.rating.model.RatingConfig
import com.codewiththiru.platform.rating.model.RatingPromptType
import com.codewiththiru.platform.rating.state.RatingAction
import com.codewiththiru.platform.rating.state.RatingUiState
import com.codewiththiru.platform.rating.ui.components.RatingDialog

@Composable
fun RatingScreen(
    uiState: RatingUiState,
    config: RatingConfig,
    onAction: (RatingAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    LaunchedEffect(Unit) {
        onAction(RatingAction.PromptShown)
    }

    when (uiState.promptType) {
        RatingPromptType.Dialog -> {
            Dialog(
                onDismissRequest = { onAction(RatingAction.DismissClicked) },
            ) {
                RatingDialog(
                    selectedStars = uiState.selectedStars,
                    isSubmitting = uiState.isSubmitting,
                    uiCustomization = config.uiCustomization,
                    onAction = onAction,
                    modifier = modifier,
                )
            }
        }
        RatingPromptType.BottomSheet -> {
            // Future implementation
        }
        RatingPromptType.Fullscreen -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                RatingDialog(
                    selectedStars = uiState.selectedStars,
                    isSubmitting = uiState.isSubmitting,
                    uiCustomization = config.uiCustomization,
                    onAction = onAction,
                    modifier = modifier.padding(16.dp),
                )
            }
        }
    }
}
