package com.codewiththiru.notifications.segmentation

import kotlinx.coroutines.flow.Flow

interface SegmentRepository {
    suspend fun saveSegment(segment: UserSegment)
    suspend fun getSegment(id: String): UserSegment?
    fun getAllSegments(): Flow<List<UserSegment>>
}
