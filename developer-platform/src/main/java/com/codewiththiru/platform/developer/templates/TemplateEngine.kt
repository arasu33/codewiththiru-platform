package com.codewiththiru.platform.developer.templates

import com.codewiththiru.platform.developer.generator.GenerationContext

interface TemplateEngine {
    fun render(templateContent: String, context: GenerationContext): String
}

class DefaultTemplateEngine : TemplateEngine {
    override fun render(templateContent: String, context: GenerationContext): String {
        var output = templateContent
        output = output.replace("\${packageName}", context.packageName)
        output = output.replace("\${moduleName}", context.moduleName)
        output = output.replace("\${featureName}", context.featureName)
        context.additionalArgs.forEach { (key, value) ->
            output = output.replace("\${$key}", value)
        }
        return output
    }
}
