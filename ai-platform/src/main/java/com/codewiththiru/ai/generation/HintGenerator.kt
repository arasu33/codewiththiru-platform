package com.codewiththiru.ai.generation

interface HintGenerator {
    suspend fun getHintForQuestion(questionId: String): String
}
