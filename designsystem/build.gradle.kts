plugins {
    id("codewiththiru.android-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.dokka)
}

group = "com.codewiththiru.platform"
version = "1.0.0-SNAPSHOT"

android {
    namespace = "com.codewiththiru.platform.designsystem"

    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":core-android"))

    implementation(libs.androidx.core.ktx)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
