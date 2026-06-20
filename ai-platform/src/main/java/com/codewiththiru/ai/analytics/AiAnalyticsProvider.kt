package com.codewiththiru.ai.analytics

interface AiAnalyticsProvider {
    fun trackPromptUsage(providerId: String, tokenCount: Int, durationMs: Long)
    fun trackError(providerId: String, errorType: String)
}
