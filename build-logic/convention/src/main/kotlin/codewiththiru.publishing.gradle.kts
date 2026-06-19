plugins {
    id("maven-publish")
    id("signing")
}

if (pluginManager.hasPlugin("com.android.library")) {
    configure<com.android.build.api.dsl.LibraryExtension> {
        publishing {
            singleVariant("release") {
                withSourcesJar()
            }
        }
    }

    afterEvaluate {
        publishing {
            publications {
                create<MavenPublication>("release") {
                    from(components.findByName("release"))
                    groupId = project.group.toString()
                    artifactId = project.name
                    version = project.version.toString()

                    pom {
                        name.set("CodeWithThiru Platform ${project.name}")
                        description.set("Android-specific extension of the CodeWithThiru core platform library.")
                        url.set("https://github.com/codewiththiru/platform")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                id.set("codewiththiru")
                                name.set("Thiru")
                                email.set("info@codewiththiru.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:github.com/codewiththiru/platform.git")
                            developerConnection.set("scm:git:ssh://github.com/codewiththiru/platform.git")
                            url.set("https://github.com/codewiththiru/platform/tree/main")
                        }
                    }
                }
            }
        }
    }
} else if (pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")) {
    val javaPlugin = project.extensions.getByType<SourceSetContainer>()
    val sourcesJar by tasks.registering(Jar::class) {
        archiveClassifier.set("sources")
        from(javaPlugin["main"].allSource)
    }

    publishing {
        publications {
            create<MavenPublication>("mavenJava") {
                from(components.findByName("java"))
                artifact(sourcesJar.get())
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                pom {
                    name.set("CodeWithThiru Platform ${project.name}")
                    description.set("Pure Kotlin core library providing essential utilities.")
                    url.set("https://github.com/codewiththiru/platform")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("codewiththiru")
                            name.set("Thiru")
                            email.set("info@codewiththiru.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:github.com/codewiththiru/platform.git")
                        developerConnection.set("scm:git:ssh://github.com/codewiththiru/platform.git")
                        url.set("https://github.com/codewiththiru/platform/tree/main")
                    }
                }
            }
        }
    }
}

publishing {
    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/codewiththiru/platform")
            credentials {
                username = System.getenv("GITHUB_ACTOR") ?: ""
                password = System.getenv("GITHUB_TOKEN") ?: ""
            }
        }
    }
}

signing {
    val signingKey = System.getenv("GPG_SIGNING_KEY")
    val signingPassword = System.getenv("GPG_SIGNING_PASSWORD")
    if (!signingKey.isNullOrEmpty() && !signingPassword.isNullOrEmpty()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        val publication = publishing.publications.findByName("release") ?: publishing.publications.findByName("mavenJava")
        if (publication != null) {
            sign(publication)
        }
    }
}
