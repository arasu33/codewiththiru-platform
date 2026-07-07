package com.codewiththiru.platform.feedback.provider

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codewiththiru.platform.feedback.model.FeedbackCategory
import com.codewiththiru.platform.feedback.model.FeedbackPayload
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.feedbackDataStore: DataStore<Preferences> by preferencesDataStore(name = "feedback_drafts")

class DataStoreDraftProvider(
    private val context: Context,
) : FeedbackDraftProvider {
    private object Keys {
        val CATEGORY_ID = stringPreferencesKey("draft_category_id")
        val CATEGORY_NAME = stringPreferencesKey("draft_category_name")
        val SUBJECT = stringPreferencesKey("draft_subject")
        val DESCRIPTION = stringPreferencesKey("draft_description")
        val EMAIL = stringPreferencesKey("draft_email")
        val NAME = stringPreferencesKey("draft_name")
    }

    override suspend fun saveDraft(payload: FeedbackPayload) {
        context.feedbackDataStore.edit { prefs ->
            prefs[Keys.CATEGORY_ID] = payload.category.id
            prefs[Keys.CATEGORY_NAME] = payload.category.displayName
            prefs[Keys.SUBJECT] = payload.subject
            prefs[Keys.DESCRIPTION] = payload.description
            payload.userEmail?.let { prefs[Keys.EMAIL] = it }
            payload.userName?.let { prefs[Keys.NAME] = it }
        }
    }

    override suspend fun loadDraft(): FeedbackPayload? {
        return context.feedbackDataStore.data
            .map { prefs ->
                val catId = prefs[Keys.CATEGORY_ID] ?: return@map null
                val catName = prefs[Keys.CATEGORY_NAME] ?: return@map null

                FeedbackPayload(
                    category = FeedbackCategory(catId, catName),
                    subject = prefs[Keys.SUBJECT] ?: "",
                    description = prefs[Keys.DESCRIPTION] ?: "",
                    userEmail = prefs[Keys.EMAIL],
                    userName = prefs[Keys.NAME],
                    attachments = emptyList(), // Attachments are generally not safe to draft via DataStore simple prefs
                )
            }.firstOrNull()
    }

    override suspend fun clearDraft() {
        context.feedbackDataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
