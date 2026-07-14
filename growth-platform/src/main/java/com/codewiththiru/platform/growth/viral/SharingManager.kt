package com.codewiththiru.platform.growth.viral

interface SharingManager {
    suspend fun shareContent(contentId: String, platform: String): Boolean
    suspend fun getShareCount(contentId: String): Int
}

class DefaultSharingManager : SharingManager {
    private val shareCounts = java.util.concurrent.ConcurrentHashMap<String, Int>()

    override suspend fun shareContent(contentId: String, platform: String): Boolean {
        shareCounts[contentId] = (shareCounts[contentId] ?: 0) + 1
        return true
    }

    override suspend fun getShareCount(contentId: String): Int {
        return shareCounts[contentId] ?: 0
    }
}
