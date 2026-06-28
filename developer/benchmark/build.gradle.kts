plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
}

group = "com.codewiththiru.platform.developer"
version = "1.0.0"

dependencies {
    api(project(":developer:game-testing"))
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
}
