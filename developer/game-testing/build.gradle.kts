plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
}

group = "com.codewiththiru.platform.developer"

dependencies {
    api(project(":core"))
    api(project(":game-profile"))
    api(project(":game-leaderboard"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
    testImplementation(libs.junit)
}
