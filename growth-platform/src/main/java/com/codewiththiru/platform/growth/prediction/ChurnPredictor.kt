package com.codewiththiru.platform.growth.prediction

interface ChurnPredictor {
    suspend fun predictChurnRisk(userId: String): Float
}

class DefaultChurnPredictor : ChurnPredictor {
    override suspend fun predictChurnRisk(userId: String): Float {
        // ML Model dummy
        return 0.2f
    }
}
