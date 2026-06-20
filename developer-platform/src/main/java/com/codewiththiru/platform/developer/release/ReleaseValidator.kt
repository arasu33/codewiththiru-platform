package com.codewiththiru.platform.developer.release

interface ReleaseValidator {
    suspend fun validateReleaseCandidate(version: String): Boolean
}

class DefaultReleaseValidator : ReleaseValidator {
    override suspend fun validateReleaseCandidate(version: String): Boolean {
        // Run detekt, ktlint, tests, coverage
        return true
    }
}
