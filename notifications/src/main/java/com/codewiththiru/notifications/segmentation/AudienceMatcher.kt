package com.codewiththiru.notifications.segmentation

import com.codewiththiru.notifications.campaign.CampaignAudience

class AudienceMatcher(
    private val segmentRepository: SegmentRepository,
    private val segmentEvaluator: SegmentEvaluator
) {
    suspend fun isUserInAudience(audience: CampaignAudience): Boolean {
        for (excludeId in audience.excludeSegmentIds) {
            val segment = segmentRepository.getSegment(excludeId)
            if (segment != null && segmentEvaluator.evaluate(segment)) {
                return false
            }
        }

        if (audience.segmentIds.isEmpty()) return true

        for (includeId in audience.segmentIds) {
            val segment = segmentRepository.getSegment(includeId)
            if (segment != null && segmentEvaluator.evaluate(segment)) {
                return true
            }
        }
        
        return false
    }
}
