# 📦 Platform Bill of Materials (`:platform-bom`)

The **Bill of Materials (BOM)** standardizes and aligns dependency versions across all 51 modules of the CodeWithThiru Platform SDK.

---

## 🎯 Why Use the BOM?

1. **Zero Version Mismatch**: Prevents binary incompatibilities caused by using mismatched versions of sibling modules.
2. **Simplified Gradle Scripts**: Specify the version once in the BOM import. All individual module dependencies omit their version tag.
3. **Painless Upgrades**: Update one version string (`1.5.0` ➔ `1.6.0`) to upgrade the entire platform suite simultaneously.

---

## 🚀 Usage

Add the BOM to your app module's `build.gradle.kts` using Gradle's `platform()` syntax:

```kotlin
dependencies {
    // 1. Declare the Platform BOM with the desired release version
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.0"))

    // 2. Add individual modules without specifying versions
    implementation("com.codewiththiru.platform:core")
    implementation("com.codewiththiru.platform:core-android")
    implementation("com.codewiththiru.platform:designsystem")
    implementation("com.codewiththiru.platform:widgets")
    implementation("com.codewiththiru.platform:settings")
    implementation("com.codewiththiru.platform:analytics")
    implementation("com.codewiththiru.platform:security")
}
```
