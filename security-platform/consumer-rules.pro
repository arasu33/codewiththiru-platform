# Proguard rules for security-platform

# Obfuscate internal implementation
-repackageclasses com.codewiththiru.security.internal

# Keep public API methods and classes
-keep public class com.codewiththiru.security.encryption.** {
    public *;
}

# Warn or obfuscate all other non-public members
-keepclassmembers class com.codewiththiru.security.** {
    public *;
}
