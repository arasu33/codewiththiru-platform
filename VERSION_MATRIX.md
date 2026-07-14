# CodeWithThiru Platform Version Matrix

The CodeWithThiru Platform SDK is verified against specific versions of the Android ecosystem. Consumers should attempt to match these versions to guarantee stability.

| Platform SDK | Recommended Kotlin | Recommended Compose BOM | Recommended Gradle Plugin | Minimum Android SDK | Target Android SDK |
| :--- | :--- | :--- | :--- | :--- | :--- |
| **1.0.4+** | `2.4.0` | `2026.06.01` | `9.2.1` | `24` | `35` |
| **1.0.x** | `2.4.0` | `2026.06.01` | `9.2.1` | `24` | `35` |

## Upgrading the Platform

When upgrading the platform in a consumer application, ensure that you also update the Kotlin and Compose Compiler versions to match the matrix above, as Compose stability is tightly coupled to the Kotlin version.
