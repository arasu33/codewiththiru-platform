// Helper script to resolve GitHub Packages publishing credentials and repository endpoint
val gprUser: String? = (project.findProperty("gpr.user") as? String) ?: System.getenv("GPR_USER")
val gprKey: String? = (project.findProperty("gpr.key") as? String) ?: System.getenv("GPR_KEY")

ext["gprUser"] = gprUser ?: ""
ext["gprKey"] = gprKey ?: ""

ext["gprRepoUrl"] = "https://maven.pkg.github.com/arasu33/codewiththiru-platform"
