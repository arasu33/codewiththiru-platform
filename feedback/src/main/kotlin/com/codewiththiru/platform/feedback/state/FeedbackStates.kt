package com.codewiththiru.platform.feedback.state

import com.codewiththiru.platform.feedback.model.FeedbackCategory

sealed interface FeedbackUiState {
    data object Idle : FeedbackUiState

    data object Submitting : FeedbackUiState

    data class Success(
        val message: String,
    ) : FeedbackUiState

    data class Error(
        val reason: String,
    ) : FeedbackUiState
}

data class FeedbackFormState(
    val category: FeedbackCategory?,
    val subject: String = "",
    val description: String = "",
    val email: String = "",
    val name: String = "",
    val subjectError: String? = null,
    val descriptionError: String? = null,
    val emailError: String? = null,
)
