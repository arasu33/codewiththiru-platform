package com.codewiththiru.ai.memory

interface MemoryManager {
    suspend fun storeMemory(key: String, content: String)
    suspend fun retrieveMemory(key: String): String?
    suspend fun clearMemory()
}
