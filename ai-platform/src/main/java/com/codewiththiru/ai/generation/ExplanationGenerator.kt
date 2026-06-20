package com.codewiththiru.ai.generation

interface ExplanationGenerator {
    suspend fun explainConcept(concept: String): String
}
