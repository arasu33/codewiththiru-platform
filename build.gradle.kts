plugins {
    alias(libs.plugins.dependency.analysis)
    alias(libs.plugins.binary.compatibility.validator)
    id("com.github.ben-manes.versions") version "0.51.0"
}

allprojects {
    group = "com.codewiththiru.platform"
    version = (project.findProperty("PLATFORM_VERSION_NAME") as? String) ?: "1.0.0"
}

tasks.register("bumpPatchVersion") {
    notCompatibleWithConfigurationCache("Modifies gradle.properties file")
    doLast { bumpVersion("patch") }
}

tasks.register("bumpMinorVersion") {
    notCompatibleWithConfigurationCache("Modifies gradle.properties file")
    doLast { bumpVersion("minor") }
}

tasks.register("bumpMajorVersion") {
    notCompatibleWithConfigurationCache("Modifies gradle.properties file")
    doLast { bumpVersion("major") }
}

fun bumpVersion(type: String) {
    val propsFile = file("gradle.properties")
    if (!propsFile.exists()) return
    
    val lines = propsFile.readLines()
    var versionName = "1.0.0"
    var versionCode = 1
    
    val updatedLines = lines.map { line ->
        when {
            line.startsWith("PLATFORM_VERSION_NAME=") -> {
                val current = line.substringAfter("=").trim()
                val parts = current.split(".")
                if (parts.size == 3) {
                    var major = parts[0].toInt()
                    var minor = parts[1].toInt()
                    var patch = parts[2].toInt()
                    when (type) {
                        "major" -> { major++; minor = 0; patch = 0 }
                        "minor" -> { minor++; patch = 0 }
                        "patch" -> patch++
                    }
                    versionName = "$major.$minor.$patch"
                    "PLATFORM_VERSION_NAME=$versionName"
                } else {
                    line
                }
            }
            line.startsWith("PLATFORM_VERSION_CODE=") -> {
                versionCode = line.substringAfter("=").trim().toInt() + 1
                "PLATFORM_VERSION_CODE=$versionCode"
            }
            else -> line
        }
    }
    
    propsFile.writeText(updatedLines.joinToString("\n") + "\n")
    println("Bumped to Version: $versionName ($versionCode)")
}

val hookSource = file("scripts/pre-commit")
val hookTarget = file(".git/hooks/pre-commit")
if (hookSource.exists() && file(".git").exists()) {
    try {
        hookSource.copyTo(hookTarget, overwrite = true)
        hookTarget.setExecutable(true, false)
    } catch (e: Exception) {
        println("Could not install git hooks: ${e.message}")
    }
}
