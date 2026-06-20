package com.codewiththiru.remoteconfig.cache

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.doublePreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

val Context.remoteConfigDataStore: DataStore<Preferences> by preferencesDataStore(name = "remote_config_cache")

class DataStoreRemoteConfigCache(
    private val context: Context
) : RemoteConfigCache {

    override suspend fun save(key: String, value: String) {
        context.remoteConfigDataStore.edit { preferences ->
            preferences[stringPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Boolean) {
        context.remoteConfigDataStore.edit { preferences ->
            preferences[booleanPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Int) {
        context.remoteConfigDataStore.edit { preferences ->
            preferences[intPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Long) {
        context.remoteConfigDataStore.edit { preferences ->
            preferences[longPreferencesKey(key)] = value
        }
    }

    override suspend fun save(key: String, value: Double) {
        context.remoteConfigDataStore.edit { preferences ->
            preferences[doublePreferencesKey(key)] = value
        }
    }

    override suspend fun getString(key: String): String? {
        return context.remoteConfigDataStore.data.map { preferences ->
            preferences[stringPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun getBoolean(key: String): Boolean? {
        return context.remoteConfigDataStore.data.map { preferences ->
            preferences[booleanPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun getInt(key: String): Int? {
        return context.remoteConfigDataStore.data.map { preferences ->
            preferences[intPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun getLong(key: String): Long? {
        return context.remoteConfigDataStore.data.map { preferences ->
            preferences[longPreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun getDouble(key: String): Double? {
        return context.remoteConfigDataStore.data.map { preferences ->
            preferences[doublePreferencesKey(key)]
        }.firstOrNull()
    }

    override suspend fun clear() {
        context.remoteConfigDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}
