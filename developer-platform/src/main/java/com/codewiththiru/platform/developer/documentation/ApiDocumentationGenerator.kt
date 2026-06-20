package com.codewiththiru.platform.developer.documentation

interface ApiDocumentationGenerator {
    suspend fun generateApiDocs(modulePath: String): String
}

class DefaultApiDocumentationGenerator : ApiDocumentationGenerator {
    override suspend fun generateApiDocs(modulePath: String): String {
        return "# API Guide for $modulePath\n"
    }
}
