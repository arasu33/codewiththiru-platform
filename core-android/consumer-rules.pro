# Consumer proguard rules for core-android module
-dontwarn com.codewiththiru.platform.**
-keepclassmembers class * implements java.io.Serializable { *; }
-keepclassmembers class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator CREATOR;
}
