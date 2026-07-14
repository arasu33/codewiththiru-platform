# CodeWithThiru Platform API Guide

Welcome to the CodeWithThiru Platform API Governance Guide. This SDK is consumed by multiple applications, including AquaSort, and must be maintained with strict stability guarantees.

## Core Principles

1. **Internal by Default**
   - Every class, function, and interface must be `internal` unless it explicitly serves a public SDK use case.
   - Do not expose speculative APIs (i.e., APIs without a real, immediate consumer).

2. **Binary Compatibility**
   - The platform strictly enforces binary compatibility using the Kotlin `binary-compatibility-validator`.
   - Breaking changes to the public API will fail the CI/CD pipeline (`apiCheck` task).
   - If a breaking change is strictly required, it must be deferred to the next MAJOR release (e.g., 2.0.0).

3. **Deprecation Over Removal**
   - Instead of deleting an API, mark it with `@Deprecated(message = "...", replaceWith = ReplaceWith("..."))`.
   - Provide a clear migration path.
   - APIs remain deprecated for at least one full MINOR release cycle before being removed in a MAJOR release.

4. **Consistency & Naming**
   - Maintain a consistent naming convention across the SDK.
   - Avoid acronyms unless universally understood (e.g., UI, SDK).

## Adding New APIs

When introducing a new API, consider the following:
- Is it meant for internal platform use? Mark it `internal`.
- Does it require backward compatibility? If so, design it carefully using data classes, sealed classes, or interfaces with default implementations where possible.
- Ensure thorough KDoc documentation is added for public members.
