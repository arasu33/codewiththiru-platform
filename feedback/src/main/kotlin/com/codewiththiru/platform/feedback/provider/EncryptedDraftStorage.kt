package com.codewiththiru.platform.feedback.provider

import android.content.Context
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackPayload

class EncryptedDraftStorage(
    private val context: Context,
) : FeedbackDraftStorageStrategy {
    private val masterKey by lazy {
        MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
    }

    private val sharedPreferences by lazy {
        EncryptedSharedPreferences.create(
            context,
            "encrypted_feedback_drafts",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM,
        )
    }

    override suspend fun saveDraft(payload: FeedbackPayload) {
        sharedPreferences
            .edit()
            .putString("draft_category_id", payload.category.id)
            .putString("draft_category_name", payload.category.displayName)
            .putString("draft_subject", payload.subject)
            .putString("draft_description", payload.description)
            .putString("draft_email", payload.userEmail)
            .putString("draft_name", payload.userName)
            .apply()
    }

    @Suppress("ReturnCount")
    override suspend fun loadDraft(): FeedbackPayload? {
        val catId = sharedPreferences.getString("draft_category_id", null) ?: return null
        val catName = sharedPreferences.getString("draft_category_name", null) ?: return null

        return FeedbackPayload(
            category = FeedbackCategory(catId, catName),
            subject = sharedPreferences.getString("draft_subject", "") ?: "",
            description = sharedPreferences.getString("draft_description", "") ?: "",
            userEmail = sharedPreferences.getString("draft_email", null),
            userName = sharedPreferences.getString("draft_name", null),
            attachments = emptyList(),
        )
    }

    override suspend fun clearDraft() {
        sharedPreferences.edit().clear().apply()
    }
}
