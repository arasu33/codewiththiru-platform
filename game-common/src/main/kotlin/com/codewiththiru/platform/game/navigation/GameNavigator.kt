package com.codewiththiru.platform.game.navigation

interface GameNavigator {
    fun navigateToGameHome(gameId: String)

    fun navigateToPlayground(gameId: String)

    fun navigateToLeaderboard(gameId: String)
}
