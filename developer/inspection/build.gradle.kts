plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
}

group = "com.codewiththiru.platform.developer"

dependencies {
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.junit)
}
