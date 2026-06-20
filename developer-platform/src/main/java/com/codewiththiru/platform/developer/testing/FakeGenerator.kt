package com.codewiththiru.platform.developer.testing

interface FakeGenerator {
    suspend fun generateFakeImplementation(interfaceName: String): Boolean
}

class DefaultFakeGenerator : FakeGenerator {
    override suspend fun generateFakeImplementation(interfaceName: String): Boolean {
        return true
    }
}
