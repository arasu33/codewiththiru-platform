package com.codewiththiru.platform.developer.ai

interface CodeReviewAssistant {
    suspend fun reviewPullRequest(diff: String): String
}

class DefaultCodeReviewAssistant(private val promptLibrary: PromptLibrary) : CodeReviewAssistant {
    override suspend fun reviewPullRequest(diff: String): String {
        return "LGTM - AI Review"
    }
}
