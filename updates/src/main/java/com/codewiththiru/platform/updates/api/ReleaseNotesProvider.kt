package com.codewiththiru.platform.updates.api

/**
 * Abstraction for fetching release notes.
 * Implementations could read from a local JSON/XML resource, Remote Config, or a REST API.
 */
interface ReleaseNotesProvider {
    /**
     * Fetches the release notes for a given version code.
     * @param versionCode The version code to get notes for.
     * @return The release notes string, or null if unavailable.
     */
    suspend fun getReleaseNotes(versionCode: Int): String?
}
