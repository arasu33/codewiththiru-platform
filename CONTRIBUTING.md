# Contributing to CodeWithThiru Platform

Thank you for your interest in contributing to the CodeWithThiru Platform! This document provides guidelines for contributing to this enterprise-grade Android monorepo.

## Code of Conduct

Please be respectful and constructive in issues and pull requests.

## Workflow

1. Fork the repository and create a new branch.
2. Ensure your code compiles and tests pass locally.
3. Submit a Pull Request targeting the `main` or `develop` branch.
4. Request review from a Core Team member.

## Code Quality Standards

This repository enforces strict code quality checks:
- **Ktlint**: Run `./gradlew ktlintCheck` before committing.
- **Detekt**: Run `./gradlew detekt` to ensure there are no smell violations.
- **Testing**: All new features must include unit tests. High coverage is expected for core components.

## Module Creation

If you are adding a new module:
1. Ensure it fits into the `core`, `feature`, `infrastructure`, or `game-*` categorization.
2. Use the standard build logic convention plugins (e.g., `codewiththiru.android-library`).
3. Add a `consumer-rules.pro` for ProGuard/R8.
4. Document the module in `MODULE_STATUS.md`.

## Pull Request Process

- Ensure the CI build passes (Build, Lint, Test, API Check).
- Provide a clear PR description detailing *why* the change was made and *how* it was tested.
- Update `CHANGELOG.md` if the change is significant.
