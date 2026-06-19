/**
 * Precompiled [codewiththiru.android-library.gradle.kts][Codewiththiru_android_library_gradle] script plugin.
 *
 * @see Codewiththiru_android_library_gradle
 */
public
class Codewiththiru_androidLibraryPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Codewiththiru_android_library_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
