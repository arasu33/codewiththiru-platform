package com.codewiththiru.platform.developer.generator

import com.codewiththiru.platform.developer.templates.TemplateEngine

interface CodeGenerator {
    suspend fun generate(template: GenerationTemplate, context: GenerationContext): Boolean
}

class DefaultCodeGenerator(private val templateEngine: TemplateEngine) : CodeGenerator {
    override suspend fun generate(template: GenerationTemplate, context: GenerationContext): Boolean {
        val renderedContent = templateEngine.render(template.content, context)
        // Write to template.destinationPath in actual impl
        return true
    }
}
