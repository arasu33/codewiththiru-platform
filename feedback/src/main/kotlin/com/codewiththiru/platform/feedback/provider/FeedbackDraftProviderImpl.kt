package com.codewiththiru.platform.feedback.provider

import android.content.Context
import com.codewiththiru.platform.feedback.model.DraftStorageConfig
import com.codewiththiru.platform.feedback.model.FeedbackPayload

class FeedbackDraftProviderImpl(
    context: Context,
    config: DraftStorageConfig
) : FeedbackDraftProvider {

    private val encryptedStorage = EncryptedDraftStorage(context)
    private val plainTextStorage = PlainTextDraftStorage(context)

    private val primaryStorage: FeedbackDraftStorageStrategy = if (config.encrypted) {
        encryptedStorage
    } else {
        plainTextStorage
    }

    init {
        if (config.encrypted && config.migratePlaintext) {
            // Need a coroutine scope to migrate, or we do it lazily on load
        }
    }

    override suspend fun saveDraft(payload: FeedbackPayload) {
        primaryStorage.saveDraft(payload)
    }

    override suspend fun loadDraft(): FeedbackPayload? {
        // Lazy migration if needed
        if (primaryStorage is EncryptedDraftStorage) {
            val legacyDraft = plainTextStorage.loadDraft()
            if (legacyDraft != null) {
                encryptedStorage.saveDraft(legacyDraft)
                plainTextStorage.clearDraft()
                return legacyDraft
            }
        }
        return primaryStorage.loadDraft()
    }

    override suspend fun clearDraft() {
        encryptedStorage.clearDraft()
        plainTextStorage.clearDraft()
    }
}
