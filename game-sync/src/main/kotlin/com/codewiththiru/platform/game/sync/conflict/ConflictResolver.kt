package com.codewiththiru.platform.game.sync.conflict

/**
 * Determines which payload survives when a remote mutation and a local mutation collide.
 */
interface ConflictResolver {
    /**
     * @param localJson The offline modified local payload.
     * @param remoteJson The server's current payload.
     * @param localTimestampMs Time of local modification.
     * @param remoteTimestampMs Time of remote modification.
     * @return The final JSON payload that should be saved (and subsequently synced).
     */
    fun resolve(
        collection: String,
        localJson: String,
        remoteJson: String,
        localTimestampMs: Long,
        remoteTimestampMs: Long,
    ): String
}

/**
 * Default implementation where the most recent timestamp always wins.
 */
class NewestWinsResolver : ConflictResolver {
    override fun resolve(
        collection: String,
        localJson: String,
        remoteJson: String,
        localTimestampMs: Long,
        remoteTimestampMs: Long,
    ): String {
        return if (localTimestampMs >= remoteTimestampMs) localJson else remoteJson
    }
}
