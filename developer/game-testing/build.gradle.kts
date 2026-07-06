plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
}

group = "com.codewiththiru.platform.developer"

dependencies {
    api(project(":developer:test-utils"))
    api(project(":core"))
    api(project(":game-common"))
    api(project(":game-save"))
    api(project(":game-statistics"))
    api(project(":game-achievements"))
    api(project(":game-rewards"))
    api(project(":game-profile"))
    api(project(":game-settings"))
    api(project(":game-sync"))
    api(project(":game-leaderboard"))
    api(project(":game-events"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
