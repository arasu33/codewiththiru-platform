package com.codewiththiru.platform.feedback.provider

import android.content.Context
import com.codewiththiru.platform.feedback.model.FeedbackPayload

class PlainTextDraftStorage(
    private val context: Context,
) : FeedbackDraftStorageStrategy {
    override suspend fun saveDraft(payload: FeedbackPayload) {
        val provider = DataStoreDraftProvider(context)
        provider.saveDraft(payload)
    }

    override suspend fun loadDraft(): FeedbackPayload? {
        val provider = DataStoreDraftProvider(context)
        return provider.loadDraft()
    }

    override suspend fun clearDraft() {
        val provider = DataStoreDraftProvider(context)
        provider.clearDraft()
    }
}
