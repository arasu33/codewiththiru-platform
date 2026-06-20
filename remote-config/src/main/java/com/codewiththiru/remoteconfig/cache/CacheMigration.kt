package com.codewiththiru.remoteconfig.cache

interface CacheMigration {
    val fromVersion: Int
    val toVersion: Int
    
    suspend fun migrate(payload: String): String
}
