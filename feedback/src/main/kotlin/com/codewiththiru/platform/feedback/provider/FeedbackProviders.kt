package com.codewiththiru.platform.feedback.provider

import com.codewiththiru.platform.feedback.model.FeedbackPayload
import com.codewiththiru.platform.feedback.model.FeedbackSubmissionResult

interface FeedbackSubmissionProvider {
    suspend fun submit(payload: FeedbackPayload): FeedbackSubmissionResult
}

interface FeedbackDraftProvider {
    suspend fun saveDraft(payload: FeedbackPayload)
    suspend fun loadDraft(): FeedbackPayload?
    suspend fun clearDraft()
}

interface FeedbackDiagnosticsProvider {
    fun provideDiagnostics(): Map<String, String>
}
