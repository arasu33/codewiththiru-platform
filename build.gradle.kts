plugins {
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.binary.compatibility.validator)
}

allprojects {
    group = "com.codewiththiru.platform"
    version = "1.0.0"
}
