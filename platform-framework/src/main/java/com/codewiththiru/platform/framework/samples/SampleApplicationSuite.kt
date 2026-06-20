package com.codewiththiru.platform.framework.samples

import com.codewiththiru.platform.framework.factory.AppBlueprint
import com.codewiththiru.platform.framework.template.AppTemplate

object SampleApplicationSuite {
    val FLUTTER_TUTORIAL_APP = AppBlueprint(
        appId = "com.codewiththiru.flutter.tutorial",
        requestedFeatures = listOf("core", "analytics", "ads"),
        template = AppTemplate("flutter_tut", AppTemplate.Type.LEARNING, emptyList())
    )

    val KOTLIN_TUTORIAL_APP = AppBlueprint(
        appId = "com.codewiththiru.kotlin.tutorial",
        requestedFeatures = listOf("core", "analytics", "ads", "billing"),
        template = AppTemplate("kotlin_tut", AppTemplate.Type.LEARNING, emptyList())
    )

    val SUDOKU_NEXUS = AppBlueprint(
        appId = "com.codewiththiru.sudoku.nexus",
        requestedFeatures = listOf("core", "analytics", "ads", "growth", "billing"),
        template = AppTemplate("sudoku_template", AppTemplate.Type.GAME, emptyList())
    )

    val CHESS_ACADEMY = AppBlueprint(
        appId = "com.codewiththiru.chess.academy",
        requestedFeatures = listOf("core", "analytics", "ads", "growth", "ai"),
        template = AppTemplate("chess_template", AppTemplate.Type.GAME, emptyList())
    )
}
