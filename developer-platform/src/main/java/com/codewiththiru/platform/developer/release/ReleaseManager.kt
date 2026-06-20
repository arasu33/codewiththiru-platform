package com.codewiththiru.platform.developer.release

interface ReleaseManager {
    suspend fun startRelease(version: String): Boolean
}

class DefaultReleaseManager(
    private val pipeline: ReleasePipeline
) : ReleaseManager {
    override suspend fun startRelease(version: String): Boolean {
        return pipeline.executePipeline(version)
    }
}
