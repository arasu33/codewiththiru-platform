package com.codewiththiru.platform.updates.api

/**
 * Abstraction for validating custom APK signatures.
 * Used for enterprise or direct distribution updates where Play Store signatures don't apply.
 */
interface ApkSignatureValidator {
    /**
     * Checks if the APK at the given path has a valid signature.
     * @param apkPath The absolute path to the downloaded APK.
     * @return true if the signature matches expected certificates.
     */
    suspend fun isValid(apkPath: String): Boolean
}
