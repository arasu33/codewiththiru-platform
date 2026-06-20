package com.codewiththiru.ai.chat

data class ConversationContext(
    val systemPrompt: String,
    val activeFeatures: List<String>
)
