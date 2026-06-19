plugins {
    id("io.gitlab.arturbosch.detekt")
}

detekt {
    parallel = true
    config.setFrom(rootProject.layout.projectDirectory.file("config/detekt/detekt.yml"))
    buildUponDefaultConfig = true
    allRules = false
}

tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {
    reports {
        xml.required.set(true)
        html.required.set(true)
        txt.required.set(false)
        sarif.required.set(true)
    }
}
