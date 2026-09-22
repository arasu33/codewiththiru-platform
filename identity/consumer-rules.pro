# Consumer ProGuard rules for :identity module
# These rules are automatically applied to consumer apps via consumerProguardFiles

# Keep identity data models for JSON/JWT deserialization
-keepclassmembers class com.codewiththiru.identity.model.** {
    <fields>;
    <init>(...);
}

# Keep OAuth provider classes if loaded dynamically
-keep class com.codewiththiru.identity.provider.** { *; }

# Keep serializable session classes
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
