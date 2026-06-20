package com.codewiththiru.platform.developer.scaffolding

import com.codewiththiru.platform.developer.generator.GenerationContext

interface ProjectScaffolder {
    suspend fun scaffoldNewProject(context: GenerationContext): Boolean
}

class DefaultProjectScaffolder(
    private val moduleScaffolder: ModuleScaffolder,
    private val featureScaffolder: FeatureScaffolder
) : ProjectScaffolder {
    override suspend fun scaffoldNewProject(context: GenerationContext): Boolean {
        moduleScaffolder.scaffoldModule("base_module", context)
        featureScaffolder.scaffoldFeature("base_feature", context)
        return true
    }
}
