# Consumer ProGuard rules for :security-platform module
# These rules are automatically applied to consumer apps via consumerProguardFiles

# Keep AndroidX Security Crypto classes
-keep class androidx.security.crypto.** { *; }

# Keep security data models for JSON deserialization
-keepclassmembers class com.codewiththiru.security.model.** {
    <fields>;
    <init>(...);
}

# Suppress OkHttp warnings if not fully used
-dontwarn okhttp3.**
-dontwarn okio.**
-dontwarn javax.annotation.**
