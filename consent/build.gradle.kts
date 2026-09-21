plugins {
    id("codewiththiru.publishing")
    id("codewiththiru.android-library")
}

android {
    namespace = "com.codewiththiru.consent"
}

dependencies {
    implementation(project(":core"))
    implementation(project(":designsystem"))
}


