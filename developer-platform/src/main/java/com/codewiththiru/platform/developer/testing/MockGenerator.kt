package com.codewiththiru.platform.developer.testing

interface MockGenerator {
    suspend fun generateMockFile(interfaceName: String): Boolean
}

class DefaultMockGenerator : MockGenerator {
    override suspend fun generateMockFile(interfaceName: String): Boolean {
        return true
    }
}
