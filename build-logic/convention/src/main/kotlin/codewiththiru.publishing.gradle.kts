plugins {
    id("maven-publish")
    id("signing")
}

val gprUser: String? = (project.findProperty("gpr.user") as? String) ?: System.getenv("GITHUB_ACTOR") ?: System.getenv("GPR_USER")
val gprKey: String? = (project.findProperty("gpr.key") as? String) ?: System.getenv("GITHUB_TOKEN") ?: System.getenv("GPR_KEY")

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
                        description.set("Android library component of the CodeWithThiru platform.")
                        url.set("https://github.com/arasu33/codewiththiru-platform")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                id.set("arasu33")
                                name.set("Thiru")
                                email.set("info@codewiththiru.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:git://github.com/arasu33/codewiththiru-platform.git")
                            developerConnection.set("scm:git:ssh://git@github.com/arasu33/codewiththiru-platform.git")
                            url.set("https://github.com/arasu33/codewiththiru-platform")
                        }
                    }
                }
            }
        }
    }
} else if (pluginManager.hasPlugin("org.jetbrains.kotlin.jvm")) {
    val javaPlugin = project.extensions.getByType<SourceSetContainer>()
    val sourcesJar = tasks.register<Jar>("sourcesJar") {
        archiveClassifier.set("sources")
        from(javaPlugin["main"].allSource)
    }

    afterEvaluate {
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
                        description.set("Kotlin core library component of the CodeWithThiru platform.")
                        url.set("https://github.com/arasu33/codewiththiru-platform")
                        licenses {
                            license {
                                name.set("The Apache License, Version 2.0")
                                url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                            }
                        }
                        developers {
                            developer {
                                id.set("arasu33")
                                name.set("Thiru")
                                email.set("info@codewiththiru.com")
                            }
                        }
                        scm {
                            connection.set("scm:git:git://github.com/arasu33/codewiththiru-platform.git")
                            developerConnection.set("scm:git:ssh://git@github.com/arasu33/codewiththiru-platform.git")
                            url.set("https://github.com/arasu33/codewiththiru-platform")
                        }
                    }
                }
            }
        }
    }
} else if (pluginManager.hasPlugin("java-platform")) {
    publishing {
        publications {
            create<MavenPublication>("bom") {
                from(components.findByName("javaPlatform"))
                groupId = project.group.toString()
                artifactId = project.name
                version = project.version.toString()

                pom {
                    name.set("CodeWithThiru Platform BOM")
                    description.set("Bill of Materials (BOM) for the CodeWithThiru platform modules.")
                    url.set("https://github.com/arasu33/codewiththiru-platform")
                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("arasu33")
                            name.set("Thiru")
                            email.set("info@codewiththiru.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/arasu33/codewiththiru-platform.git")
                        developerConnection.set("scm:git:ssh://git@github.com/arasu33/codewiththiru-platform.git")
                        url.set("https://github.com/arasu33/codewiththiru-platform")
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
            url = uri("https://maven.pkg.github.com/arasu33/codewiththiru-platform")
            credentials {
                username = gprUser ?: ""
                password = gprKey ?: ""
            }
        }
        maven {
            name = "LocalRepo"
            url = uri("${rootProject.rootDir}/build/repo")
        }
    }
}

signing {
    val signingKey = System.getenv("GPG_SIGNING_KEY")
    val signingPassword = System.getenv("GPG_SIGNING_PASSWORD")
    if (!signingKey.isNullOrEmpty() && !signingPassword.isNullOrEmpty()) {
        useInMemoryPgpKeys(signingKey, signingPassword)
        sign(publishing.publications)
    }
}
