package com.codewiththiru.platform.feedback.provider

import com.codewiththiru.platform.feedback.model.FeedbackPayload

interface FeedbackDraftStorageStrategy {
    suspend fun saveDraft(payload: FeedbackPayload)

    suspend fun loadDraft(): FeedbackPayload?

    suspend fun clearDraft()
}
