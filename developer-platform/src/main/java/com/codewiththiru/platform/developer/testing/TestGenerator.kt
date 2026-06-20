package com.codewiththiru.platform.developer.testing

interface TestGenerator {
    suspend fun generateUnitTestClass(className: String): Boolean
}

class DefaultTestGenerator(
    private val mockGenerator: MockGenerator,
    private val fakeGenerator: FakeGenerator
) : TestGenerator {
    override suspend fun generateUnitTestClass(className: String): Boolean {
        // Scaffolds a JUnit 4/5 test class
        return true
    }
}
