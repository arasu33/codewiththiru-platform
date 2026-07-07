package com.codewiththiru.platform.feedback.provider

import com.codewiththiru.platform.feedback.model.FeedbackPayload

interface FeedbackAttachmentProvider {
    suspend fun resolveAttachment(uriOrData: String): ByteArray?
}

interface FeedbackSubmissionPolicy {
    fun canSubmit(payload: FeedbackPayload): Boolean
}

class DefaultFeedbackSubmissionPolicy : FeedbackSubmissionPolicy {
    override fun canSubmit(payload: FeedbackPayload): Boolean {
        return true // Add actual policy logic like network state here
    }
}
