package com.codewiththiru.ai.chat

data class Conversation(
    val id: String,
    val title: String,
    val messages: List<ConversationMessage>,
    val context: ConversationContext
)
