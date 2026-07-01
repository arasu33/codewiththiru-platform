plugins {
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.consent"
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
}
