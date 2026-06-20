package com.codewiththiru.ai.platform.memory

data class UserMemory(val userId: String, val traits: Map<String, String>)
data class SessionMemory(val sessionId: String, val events: List<String>)
data class ContextMemory(val contextId: String, val facts: Map<String, String>)
data class KnowledgeMemory(val topic: String, val mastered: Boolean)

interface MemoryManager {
    suspend fun storeUserTrait(userId: String, key: String, value: String)
    suspend fun getUserMemory(userId: String): UserMemory
    suspend fun clearSession(sessionId: String)
}
