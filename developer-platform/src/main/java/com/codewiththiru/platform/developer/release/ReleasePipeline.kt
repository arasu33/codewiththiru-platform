package com.codewiththiru.platform.developer.release

interface ReleasePipeline {
    suspend fun executePipeline(version: String): Boolean
}

class DefaultReleasePipeline(private val validator: ReleaseValidator) : ReleasePipeline {
    override suspend fun executePipeline(version: String): Boolean {
        return validator.validateReleaseCandidate(version)
    }
}
