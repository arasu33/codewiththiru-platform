package com.codewiththiru.platform.framework.operations

data class DeploymentProfile(
    val profileId: String,
    val requiresManualApproval: Boolean,
    val enableCanary: Boolean
)

data class ReleaseProfile(
    val versionCode: Int,
    val versionName: String,
    val releaseNotes: String
)

interface PlatformOperationsManager {
    fun manageBuild()
    fun deployRelease(profile: ReleaseProfile, deploymentProfile: DeploymentProfile)
    fun rollback(versionCode: Int)
}
