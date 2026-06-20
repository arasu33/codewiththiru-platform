package com.codewiththiru.platform.growth.retention

interface CohortAnalyzer {
    suspend fun getCohortReport(cohortId: String): CohortReport
    suspend fun assignUserToCohort(userId: String, cohortId: String)
}

class DefaultCohortAnalyzer : CohortAnalyzer {
    override suspend fun getCohortReport(cohortId: String): CohortReport {
        return CohortReport(
            cohortId = cohortId,
            startDate = System.currentTimeMillis(),
            metrics = listOf(RetentionMetric(1, 80, 100), RetentionMetric(7, 40, 100))
        )
    }

    override suspend fun assignUserToCohort(userId: String, cohortId: String) {
        // Track cohort assignment
    }
}
