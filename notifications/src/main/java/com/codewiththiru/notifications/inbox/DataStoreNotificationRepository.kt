package com.codewiththiru.notifications.inbox

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

private val Context.inboxDataStore by preferencesDataStore(name = "notification_inbox")

interface NotificationRepository {
    val inboxItems: Flow<List<NotificationInboxItem>>
    suspend fun saveNotification(item: NotificationInboxItem)
    suspend fun markAsRead(notificationId: String)
    suspend fun markAsArchived(notificationId: String)
    suspend fun deleteNotification(notificationId: String)
    suspend fun clearAll()
}

class DataStoreNotificationRepository(private val context: Context) : NotificationRepository {
    private val INBOX_KEY = stringPreferencesKey("inbox_items")
    private val json = Json { ignoreUnknownKeys = true }

    override val inboxItems: Flow<List<NotificationInboxItem>> = context.inboxDataStore.data.map { prefs ->
        val jsonString = prefs[INBOX_KEY] ?: "[]"
        try {
            json.decodeFromString(jsonString)
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            emptyList()
        }
    }

    override suspend fun saveNotification(item: NotificationInboxItem) {
        context.inboxDataStore.edit { prefs ->
            val current = getList(prefs[INBOX_KEY]).toMutableList()
            // Avoid duplicates
            if (current.none { it.payload.id == item.payload.id }) {
                current.add(0, item) // Add to top
                // Limit to 100 items max
                if (current.size > 100) current.removeAt(current.lastIndex)
                prefs[INBOX_KEY] = json.encodeToString(current)
            }
        }
    }

    override suspend fun markAsRead(notificationId: String) {
        context.inboxDataStore.edit { prefs ->
            val current = getList(prefs[INBOX_KEY]).map {
                if (it.payload.id == notificationId) it.copy(isRead = true) else it
            }
            prefs[INBOX_KEY] = json.encodeToString(current)
        }
    }

    override suspend fun markAsArchived(notificationId: String) {
        context.inboxDataStore.edit { prefs ->
            val current = getList(prefs[INBOX_KEY]).map {
                if (it.payload.id == notificationId) it.copy(isArchived = true) else it
            }
            prefs[INBOX_KEY] = json.encodeToString(current)
        }
    }

    override suspend fun deleteNotification(notificationId: String) {
        context.inboxDataStore.edit { prefs ->
            val current = getList(prefs[INBOX_KEY]).filter { it.payload.id != notificationId }
            prefs[INBOX_KEY] = json.encodeToString(current)
        }
    }

    override suspend fun clearAll() {
        context.inboxDataStore.edit { it.clear() }
    }

    private fun getList(jsonString: String?): List<NotificationInboxItem> {
        if (jsonString.isNullOrBlank()) return emptyList()
        return try {
            json.decodeFromString(jsonString)
        } catch (e: kotlinx.coroutines.CancellationException) {
        throw e
    } catch (e: kotlin.coroutines.cancellation.CancellationException) {
        throw e
    } catch (e: Exception) {
            emptyList()
        }
    }
}
