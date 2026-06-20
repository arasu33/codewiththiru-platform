package com.codewiththiru.ai.memory

interface MemoryStore {
    suspend fun save(key: String, data: String)
    suspend fun load(key: String): String?
    suspend fun delete(key: String)
}
