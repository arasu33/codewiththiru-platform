plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
    alias(libs.plugins.kotlin.serialization)
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)
}
