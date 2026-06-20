package com.codewiththiru.remoteconfig.cache

data class CacheMetrics(
    var hitCount: Long = 0,
    var missCount: Long = 0,
    var evictionCount: Long = 0,
    var migrationCount: Long = 0,
    var corruptionCount: Long = 0
) {
    fun recordHit() { hitCount++ }
    fun recordMiss() { missCount++ }
    fun recordEviction() { evictionCount++ }
    fun recordMigration() { migrationCount++ }
    fun recordCorruption() { corruptionCount++ }
}
