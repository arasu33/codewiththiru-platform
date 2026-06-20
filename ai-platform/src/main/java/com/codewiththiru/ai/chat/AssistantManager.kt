package com.codewiththiru.ai.chat

interface AssistantManager {
    suspend fun processCommand(command: String): String
}
