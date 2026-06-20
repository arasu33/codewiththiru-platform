package com.codewiththiru.ai.chat

data class ConversationMessage(
    val id: String,
    val role: Role,
    val text: String,
    val timestamp: Long
) {
    enum class Role {
        USER,
        ASSISTANT,
        SYSTEM
    }
}
