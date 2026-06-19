plugins {
    `kotlin-dsl`
}

group = "com.codewiththiru.platform.buildlogic"

dependencies {
    implementation(libs.android.gradle.plugin)
    implementation(libs.kotlin.gradle.plugin)
    implementation(libs.detekt.gradle.plugin)
    implementation(libs.ktlint.gradle.plugin)
    implementation(libs.dokka.gradle.plugin)
}
