plugins {
    id("codewiththiru.kotlin-jvm-library")
    id("codewiththiru.publishing")
    id("codewiththiru.detekt")
    id("codewiththiru.ktlint")
    alias(libs.plugins.dokka)
    alias(libs.plugins.kotlin.serialization)
    jacoco
}

group = "com.codewiththiru.platform"
version = "1.0.0"

tasks.jacocoTestReport {
    dependsOn(tasks.test)
    reports {
        xml.required.set(true)
        html.required.set(true)
    }
}

tasks.dokkaHtml {
    outputDirectory.set(layout.buildDirectory.dir("dokka"))
}

dependencies {
    implementation(project(":core"))
    implementation(project(":analytics-api"))
    implementation(project(":ads-api"))
    implementation(project(":billing-api"))
    implementation(project(":remote-config-api"))

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.serialization.json)

    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.kotlinx.coroutines.test)
}
