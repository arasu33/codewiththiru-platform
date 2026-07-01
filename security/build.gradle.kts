plugins {
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.security"
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
}
