package com.codewiththiru.platform.feedback.model

data class FeedbackCategory(
    val id: String,
    val displayName: String,
)

enum class AttachmentType { SCREENSHOT, LOG, DIAGNOSTICS }

data class Attachment(
    val type: AttachmentType,
    val uriOrData: String,
)

data class FeedbackPayload(
    val category: FeedbackCategory,
    val subject: String,
    val description: String,
    val userEmail: String?,
    val userName: String?,
    val attachments: List<Attachment>,
)

sealed interface FeedbackSubmissionResult {
    data object Success : FeedbackSubmissionResult

    data class NetworkError(
        val retryAvailable: Boolean,
    ) : FeedbackSubmissionResult

    data class ValidationError(
        val message: String,
    ) : FeedbackSubmissionResult

    data class UnknownError(
        val throwable: Throwable?,
    ) : FeedbackSubmissionResult
}
