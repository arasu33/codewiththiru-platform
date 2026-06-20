package com.codewiththiru.platform.updates.api

/**
 * Compares app versions (e.g., versionCode, semantic versioning like 1.2.3).
 */
interface VersionComparator {
    /**
     * Compares two version strings.
     * @return a negative integer, zero, or a positive integer as the first version
     *         is less than, equal to, or greater than the second version.
     */
    fun compare(version1: String, version2: String): Int
}
