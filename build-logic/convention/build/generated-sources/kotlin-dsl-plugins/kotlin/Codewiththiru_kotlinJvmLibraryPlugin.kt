/**
 * Precompiled [codewiththiru.kotlin-jvm-library.gradle.kts][Codewiththiru_kotlin_jvm_library_gradle] script plugin.
 *
 * @see Codewiththiru_kotlin_jvm_library_gradle
 */
public
class Codewiththiru_kotlinJvmLibraryPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Codewiththiru_kotlin_jvm_library_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
