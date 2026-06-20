package com.codewiththiru.platform.developer.documentation

interface ArchitectureDocumentationGenerator {
    suspend fun generateArchitectureDocs(modulePath: String): String
}

class DefaultArchitectureDocumentationGenerator : ArchitectureDocumentationGenerator {
    override suspend fun generateArchitectureDocs(modulePath: String): String {
        return "# Architecture Diagram for $modulePath\n"
    }
}
