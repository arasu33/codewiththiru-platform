# 🏛️ Platform Governance (`:platform-governance`)

Enterprise Governance Engine for the CodeWithThiru ecosystem.

## Features
- **Architectural Rules & Verification**: Validates decoupling, dependency isolation, and circular dependency prevention.
- **Compliance & Privacy Checks**: Enforces GDPR, CCPA, and data minimization policies across components.
- **Performance & Binary Budgets**: Rules to inspect APK size growth and startup overhead.
- **Accessibility & Quality Certification**: Verifies accessibility attributes and test coverage thresholds.

## Quick Start

### 1. Add Dependencies
```kotlin
dependencies {
    implementation(platform("com.codewiththiru.platform:platform-bom:1.5.1"))
    implementation("com.codewiththiru.platform:platform-governance")
}
```

Refer to `docs/` for specific governance specifications (`ACCESSIBILITY_GOVERNANCE.md`, `SECURITY_GOVERNANCE.md`, `PERFORMANCE_GOVERNANCE.md`).
