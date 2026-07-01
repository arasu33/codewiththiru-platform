plugins {
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.settings"
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
}
