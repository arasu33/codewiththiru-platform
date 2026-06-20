package com.codewiththiru.platform.framework.factory

import com.codewiththiru.platform.framework.template.AppTemplate

interface AppFactory {
    fun createBlueprint(name: String, template: AppTemplate): AppBlueprint
    fun assembleApp(blueprint: AppBlueprint)
}

data class AppBlueprint(
    val appId: String,
    val requestedFeatures: List<String>,
    val template: AppTemplate
)

interface AppGenerator {
    fun generateFeatures(blueprint: AppBlueprint)
    fun generateNavigation(blueprint: AppBlueprint)
}

interface AppAssembler {
    fun assemble(blueprint: AppBlueprint)
}
