# Consumer ProGuard rules for :notifications module
# These rules are automatically applied to consumer apps via consumerProguardFiles

# Keep Firebase Messaging service classes (instantiated via AndroidManifest)
-keep class com.google.firebase.messaging.** { *; }

# Keep notification data models for JSON deserialization
-keepclassmembers class com.codewiththiru.notifications.model.** {
    <fields>;
    <init>(...);
}

# Keep WorkManager worker classes for scheduling
-keep class com.codewiththiru.notifications.worker.** {
    public <init>(android.content.Context, androidx.work.WorkerParameters);
}

# Suppress warnings for optional Firebase messaging dependencies
-dontwarn com.google.firebase.messaging.**
