package com.codewiththiru.ai.chat

import kotlinx.coroutines.flow.StateFlow

interface ChatManager {
    val activeConversations: StateFlow<List<Conversation>>
    
    suspend fun startConversation(): Conversation
    suspend fun sendMessage(conversationId: String, text: String)
}
