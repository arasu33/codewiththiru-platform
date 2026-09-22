# Consumer ProGuard rules for :billing module
# These rules are automatically applied to consumer apps via consumerProguardFiles

# Keep Play Billing client classes (uses reflection for AIDL)
-keep class com.android.vending.billing.** { *; }
-keep class com.android.billingclient.** { *; }

# Keep billing response models (used in JSON/Parcelable deserialization)
-keepclassmembers class com.codewiththiru.billing.model.** {
    <fields>;
    <init>(...);
}

# Keep serializable billing state classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Suppress warnings for billing AIDL interfaces
-dontwarn com.android.vending.**
