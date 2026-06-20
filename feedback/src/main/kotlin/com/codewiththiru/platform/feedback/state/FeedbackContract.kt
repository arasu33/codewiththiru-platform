package com.codewiththiru.platform.feedback.state

import com.codewiththiru.platform.feedback.model.Attachment
import com.codewiththiru.platform.feedback.model.FeedbackCategory

sealed interface FeedbackAction {
    data class CategorySelected(val category: FeedbackCategory) : FeedbackAction
    data class SubjectChanged(val subject: String) : FeedbackAction
    data class DescriptionChanged(val description: String) : FeedbackAction
    data class EmailChanged(val email: String) : FeedbackAction
    data class NameChanged(val name: String) : FeedbackAction
    data class AttachmentAdded(val attachment: Attachment) : FeedbackAction
    data class AttachmentRemoved(val attachment: Attachment) : FeedbackAction
    data object SubmitClicked : FeedbackAction
    data object DismissErrorClicked : FeedbackAction
}

sealed interface FeedbackEvent {
    data class DraftLoaded(val formState: FeedbackFormState) : FeedbackEvent
    data class ValidationFailed(
        val subjectError: String?,
        val descriptionError: String?,
        val emailError: String?
    ) : FeedbackEvent
    data object SubmissionStarted : FeedbackEvent
    data class SubmissionSuccess(val message: String) : FeedbackEvent
    data class SubmissionError(val reason: String) : FeedbackEvent
}

sealed interface FeedbackEffect {
    data class ShowToast(val message: String) : FeedbackEffect
    data object NavigateBack : FeedbackEffect
}
