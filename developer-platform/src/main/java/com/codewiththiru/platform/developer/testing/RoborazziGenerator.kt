package com.codewiththiru.platform.developer.testing

interface RoborazziGenerator {
    suspend fun generateScreenshotTest(composableName: String): Boolean
}

class DefaultRoborazziGenerator : RoborazziGenerator {
    override suspend fun generateScreenshotTest(composableName: String): Boolean {
        return true
    }
}
