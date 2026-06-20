package com.codewiththiru.platform.feedback.provider

import android.content.Context
import androidx.datastore.preferences.core.edit
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackPayload
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

class PlainTextDraftStorage(private val context: Context) : FeedbackDraftStorageStrategy {

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
