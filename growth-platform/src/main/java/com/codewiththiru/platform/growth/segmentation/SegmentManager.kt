package com.codewiththiru.platform.growth.segmentation

interface SegmentManager {
    suspend fun getUserSegments(userId: String): List<AudienceSegment>
    suspend fun evaluateSegment(userId: String, segmentId: String): Boolean
}

class DefaultSegmentManager : SegmentManager {
    override suspend fun getUserSegments(userId: String): List<AudienceSegment> {
        return listOf(AudienceSegment("active_users", "Active Users", emptyMap()))
    }

    override suspend fun evaluateSegment(userId: String, segmentId: String): Boolean {
        return true
    }
}
