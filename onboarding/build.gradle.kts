plugins {
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.onboarding"
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
}
