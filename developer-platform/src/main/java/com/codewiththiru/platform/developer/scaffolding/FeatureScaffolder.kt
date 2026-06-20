package com.codewiththiru.platform.developer.scaffolding

import com.codewiththiru.platform.developer.generator.GenerationContext

interface FeatureScaffolder {
    suspend fun scaffoldFeature(templateId: String, context: GenerationContext): Boolean
}

class DefaultFeatureScaffolder : FeatureScaffolder {
    override suspend fun scaffoldFeature(templateId: String, context: GenerationContext): Boolean {
        // Creates UI, ViewModel, Repository for a feature
        return true
    }
}
