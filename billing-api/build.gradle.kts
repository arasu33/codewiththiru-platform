plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
}

dependencies {
    implementation(project(":core"))
    implementation(libs.kotlinx.coroutines.core)
}
