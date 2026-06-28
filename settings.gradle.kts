pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google {
            content {
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
            }
        }
        mavenCentral()
        mavenLocal()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

rootProject.name = "codewiththiru-platform"
include(":core")
include(":core-android")
include(":designsystem")
include(":about")
include(":feedback")
include(":rating")
include(":more-apps")
include(":coupons")
include(":updates")
include(":analytics-api")
include(":analytics")
include(":identity")
include(":ads-api")
include(":ads")
include(":remote-config-api")
include(":remote-config")
include(":notifications-api")
include(":notifications")
include(":billing-api")
include(":billing")
include(":ai-platform")
include(":security-platform")
include(":sync-backup")
include(":gamification")
include(":growth-platform")
include(":observability-platform")
include(":platform-governance")
include(":developer-platform")
include(":platform-framework")
include(":ai-native-platform")
include(":platform-bom")
include(":game-common")
include(":game-save")
include(":game-statistics")
include(":game-achievements")
include(":game-rewards")
include(":game-audio")
include(":game-haptics")
include(":game-challenges")
include(":game-profile")
include(":game-settings")
include(":game-sync")
include(":game-leaderboard")
include(":game-events")
include(":developer:test-utils")
include(":developer:game-testing")
include(":developer:benchmark")
include(":developer:inspection")
include(":sample-app")
