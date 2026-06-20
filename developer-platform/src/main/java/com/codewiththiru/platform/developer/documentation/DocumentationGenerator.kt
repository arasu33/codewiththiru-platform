package com.codewiththiru.platform.developer.documentation

interface DocumentationGenerator {
    suspend fun generateFullDocs(modulePath: String): Boolean
}

class DefaultDocumentationGenerator(
    private val apiGen: ApiDocumentationGenerator,
    private val archGen: ArchitectureDocumentationGenerator,
    private val validator: KDocValidator
) : DocumentationGenerator {
    override suspend fun generateFullDocs(modulePath: String): Boolean {
        val coverage = validator.validateModule(modulePath)
        if (coverage < 80.0f) {
            println("Documentation coverage too low ($coverage%)")
            return false
        }
        apiGen.generateApiDocs(modulePath)
        archGen.generateArchitectureDocs(modulePath)
        return true
    }
}
