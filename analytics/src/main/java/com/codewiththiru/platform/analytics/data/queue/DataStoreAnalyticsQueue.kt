package com.codewiththiru.platform.analytics.data.queue

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.codewiththiru.platform.analytics.config.AnalyticsConfig
import com.codewiththiru.platform.analytics.domain.event.AnalyticsEvent
import java.util.UUID
import kotlinx.coroutines.flow.first
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.booleanOrNull
import kotlinx.serialization.json.doubleOrNull
import kotlinx.serialization.json.longOrNull

private val dataStoreCache = java.util.concurrent.ConcurrentHashMap<String, DataStore<Preferences>>()

@Suppress("TooGenericExceptionCaught")
internal class DataStoreAnalyticsQueue(
    private val context: Context,
    private val config: AnalyticsConfig,
    queueName: String = "analytics_queue",
) : AnalyticsQueue {
    private val dataStore =
        dataStoreCache.computeIfAbsent(queueName) {
            androidx.datastore.preferences.core.PreferenceDataStoreFactory.create(
                produceFile = { java.io.File(context.filesDir, "datastore/$queueName.preferences_pb") },
            )
        }

    private val json = Json { ignoreUnknownKeys = true }
    private val queueKey = stringPreferencesKey("analytics_events")

    private suspend fun getEntities(): MutableList<AnalyticsEventEntity> =
        try {
            val prefs: androidx.datastore.preferences.core.Preferences = dataStore.data.first()
            val jsonStr = prefs[queueKey]
            if (jsonStr.isNullOrEmpty()) {
                mutableListOf()
            } else {
                json.decodeFromString(jsonStr)
            }
        } catch (e: Exception) {
            Log.e("DataStoreAnalyticsQueue", "Corruption detected during read", e)
            dataStore.edit { it.remove(queueKey) }
            mutableListOf()
        }

    override suspend fun enqueue(event: AnalyticsEvent) {
        val entity =
            AnalyticsEventEntity(
                eventId = UUID.randomUUID().toString(),
                eventName = event.name,
                timestamp = System.currentTimeMillis(),
                parameters = AnyValueSerializer.toParametersMap(event.parameters),
            )

        try {
            dataStore.edit { prefs ->
                val jsonStr = prefs[queueKey]
                val list =
                    if (jsonStr.isNullOrEmpty()) {
                        mutableListOf()
                    } else {
                        json.decodeFromString<MutableList<AnalyticsEventEntity>>(jsonStr)
                    }
                list.add(entity)

                while (list.size > config.maxQueuedEvents) {
                    list.removeAt(0)
                }

                prefs[queueKey] = json.encodeToString(list)
            }
        } catch (e: Exception) {
            Log.e("DataStoreAnalyticsQueue", "Error during enqueue", e)
            clear()
        }
    }

    override suspend fun dequeue(batchSize: Int): List<AnalyticsEvent> = peek(batchSize)

    override suspend fun peek(count: Int): List<AnalyticsEvent> {
        val entities = getEntities()
        return entities.take(count).map { entity ->
            AnalyticsEvent(
                name = entity.eventName,
                parameters =
                    entity.parameters.mapValues {
                        // Simple unwrap for primitives to avoid JsonPrimitive wrapper exposing in Any?
                        val value = it.value
                        if (value is JsonPrimitive) {
                            if (value.isString) {
                                value.content
                            } else {
                                value.booleanOrNull ?: value.longOrNull ?: value.doubleOrNull ?: value.content
                            }
                        } else {
                            value
                        }
                    },
            )
        }
    }

    override suspend fun remove(events: List<AnalyticsEvent>) {
        if (events.isEmpty()) return
        try {
            dataStore.edit { prefs ->
                val jsonStr = prefs[queueKey]
                if (jsonStr.isNullOrEmpty()) return@edit

                val list = json.decodeFromString<MutableList<AnalyticsEventEntity>>(jsonStr)

                // We remove exactly the number of items passed in, matching from the front.
                for (eventToRemove in events) {
                    val index = list.indexOfFirst { it.eventName == eventToRemove.name }
                    if (index != -1) {
                        list.removeAt(index)
                    }
                }

                prefs[queueKey] = json.encodeToString(list)
            }
        } catch (e: Exception) {
            Log.e("DataStoreAnalyticsQueue", "Error during remove", e)
            clear()
        }
    }

    override suspend fun clear() {
        try {
            dataStore.edit { it.remove(queueKey) }
        } catch (e: Exception) {
            Log.e("DataStoreAnalyticsQueue", "Error during clear", e)
        }
    }

    override suspend fun size(): Int = getEntities().size
}
