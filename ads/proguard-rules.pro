# Proguard rules for ads module

# Keep Google Mobile Ads SDK components
-keep class com.google.android.gms.ads.** { *; }

# Keep our public API models if they are used via reflection or serialization
-keep class com.codewiththiru.ads.config.AdsConfig { *; }
-keep class com.codewiththiru.ads.api.AdType { *; }
-keep class com.codewiththiru.ads.api.AdsEnvironment { *; }

# Prevent shrinking of consent module since it relies on UserMessagingPlatform
-keep class com.google.android.ump.** { *; }
