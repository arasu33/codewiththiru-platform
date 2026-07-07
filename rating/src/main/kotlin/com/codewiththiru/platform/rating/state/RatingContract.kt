package com.codewiththiru.platform.rating.state

import com.codewiththiru.platform.rating.model.RatingPromptType

data class RatingUiState(
    val promptType: RatingPromptType = RatingPromptType.Dialog,
    val isVisible: Boolean = false,
    val selectedStars: Int = 0,
    val isSubmitting: Boolean = false,
)

sealed interface RatingAction {
    data class StarSelected(
        val stars: Int,
    ) : RatingAction

    data object SubmitClicked : RatingAction

    data object DismissClicked : RatingAction

    data object PromptShown : RatingAction
}

sealed interface RatingEffect {
    data class LaunchPlayReview(
        val onComplete: () -> Unit,
    ) : RatingEffect

    data class RedirectToFeedback(
        val stars: Int,
        val onComplete: () -> Unit,
    ) : RatingEffect

    data object ClosePrompt : RatingEffect
}
