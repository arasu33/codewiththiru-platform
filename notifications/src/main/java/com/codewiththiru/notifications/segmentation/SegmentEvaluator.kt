package com.codewiththiru.notifications.segmentation

interface SegmentEvaluator {
    suspend fun evaluate(segment: UserSegment): Boolean
}
