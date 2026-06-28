package com.codewiththiru.platform.game.statistics.export

import com.codewiththiru.platform.game.statistics.api.StatisticsProfile

/**
 * Interface for exporting statistics to a specific format.
 */
interface StatisticsExporter {
    /**
     * Exports a StatisticsProfile to a string (e.g. JSON, CSV).
     */
    fun exportToString(profile: StatisticsProfile): String

    /**
     * Exports a StatisticsProfile to a binary payload.
     */
    fun exportToBytes(profile: StatisticsProfile): ByteArray
}

/**
 * Interface for importing statistics from a specific format.
 */
interface StatisticsImporter {
    /**
     * Imports a StatisticsProfile from a formatted string.
     */
    fun importFromString(data: String): StatisticsProfile

    /**
     * Imports a StatisticsProfile from a binary payload.
     */
    fun importFromBytes(data: ByteArray): StatisticsProfile
}
