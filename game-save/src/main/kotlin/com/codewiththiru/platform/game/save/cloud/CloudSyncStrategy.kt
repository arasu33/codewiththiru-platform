package com.codewiththiru.platform.game.save.cloud

import com.codewiththiru.platform.game.save.api.SaveMetadata
import com.codewiththiru.platform.game.save.api.SaveSlot

/**
 * Strategy for synchronizing saves with a cloud provider.
 */
interface CloudSyncStrategy {
    /**
     * Uploads the payload to the cloud for the given slot.
     */
    suspend fun upload(
        slot: SaveSlot,
        metadata: SaveMetadata,
        payload: ByteArray,
    ): Boolean

    /**
     * Downloads the payload from the cloud for the given slot.
     */
    suspend fun download(slot: SaveSlot): Pair<SaveMetadata, ByteArray>?

    /**
     * Fetches metadata for the cloud save without downloading the full payload.
     * Useful for conflict resolution.
     */
    suspend fun fetchMetadata(slot: SaveSlot): SaveMetadata?
}
