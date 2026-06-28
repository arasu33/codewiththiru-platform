package com.codewiththiru.platform.game.ai

import com.codewiththiru.platform.core.result.CustResult
import com.codewiththiru.platform.game.engine.GameDifficulty

interface HintProvider<TState, TMove> {
    suspend fun getNextBestMove(state: TState): CustResult<TMove>
}

interface DifficultyPredictor<TState> {
    suspend fun predictIdealDifficulty(state: TState): CustResult<GameDifficulty>
}

interface RecommendationEngine<TState, TRecommendation> {
    suspend fun getRecommendations(
        state: TState,
        limit: Int = 3,
    ): CustResult<List<TRecommendation>>
}
