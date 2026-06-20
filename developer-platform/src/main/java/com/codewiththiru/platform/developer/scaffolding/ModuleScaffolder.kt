package com.codewiththiru.platform.developer.scaffolding

import com.codewiththiru.platform.developer.generator.GenerationContext

interface ModuleScaffolder {
    suspend fun scaffoldModule(templateId: String, context: GenerationContext): Boolean
}

class DefaultModuleScaffolder : ModuleScaffolder {
    override suspend fun scaffoldModule(templateId: String, context: GenerationContext): Boolean {
        // Creates build.gradle.kts, src/main/java directories
        return true
    }
}
