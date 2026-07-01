plugins {
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.widgets"
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
}
