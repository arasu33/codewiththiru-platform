@'
# CodeWithThiru Platform Vision

## Mission

Create a reusable Android platform that accelerates development of high-quality applications.

## Goals

- Reusable modules
- Consistent UX
- Production-ready architecture
- Easy integration
- Strong testing
- High accessibility
- Enterprise-grade quality

## Supported Apps

- Learning Apps
- Games
- Productivity Apps
- Utility Apps
- Educational Apps

## Non Goals

- App-specific business logic
- Tight coupling between modules
- Hardcoded branding

## Principles

- Modular
- Decoupled
- Testable
- Scalable
- Maintainable
- Developer Friendly
'@ | Set-Content .\docs\01_PLATFORM_VISION.md



@'
# Architecture Rules

## Dependency Flow

core
↑
all modules

## Forbidden

- Circular dependencies
- Feature to feature dependencies

Examples:

ads -> analytics ❌
feedback -> ads ❌
about -> coupons ❌

## Allowed

ads -> core ✅
analytics -> core ✅
feedback -> core ✅

## Requirements

- SOLID
- Clean Architecture
- Dependency Inversion
- Composition over inheritance
- Feature isolation
'@ | Set-Content .\docs\02_ARCHITECTURE_RULES.md


@'
# API Guidelines

## Public Naming

Use:

CustButton
CustCard
CustDialog
CustAnalytics
CustAds

Avoid:

Button
Card
Ads

## API Design

- Explicit APIs
- Small interfaces
- Strong typing
- Backward compatibility

## Config Objects

Every module must expose configuration objects.

Example:

CustAdsConfig
CustAboutConfig
CustFeedbackConfig
'@ | Set-Content .\docs\03_API_GUIDELINES.md




@'
# Semantic Versioning

MAJOR.MINOR.PATCH

Examples:

1.0.0
1.1.0
1.1.1

## Major

Breaking changes

## Minor

New features

## Patch

Bug fixes

## Rules

Never publish unstable APIs as stable.
'@ | Set-Content .\docs\04_VERSIONING.md


@'
# Release Process

1. Development
2. Unit Testing
3. UI Testing
4. Review
5. Documentation
6. Tagging
7. Publishing

Checklist:

- Tests passing
- Lint passing
- Documentation updated
- Changelog updated
- Version updated
'@ | Set-Content .\docs\05_RELEASE_PROCESS.md


@'
# Module Standards

Every module must provide:

- Public API
- README
- Unit Tests
- Sample Usage
- Changelog

Every module must support:

- Dark Theme
- Accessibility
- Localization
- RTL

No module should depend on another feature module.
'@ | Set-Content .\docs\06_MODULE_STANDARDS.md


@'
# Dependency Rules

Allowed Shared Module

- core

Forbidden

- ads -> analytics
- coupons -> ads
- onboarding -> feedback

All feature modules must remain independent.
'@ | Set-Content .\docs\07_DEPENDENCY_RULES.md



@'
# Testing Strategy

## Unit Tests

Minimum Coverage:

80%

## UI Tests

Critical Flows

## Performance Tests

Startup
Memory
Recomposition

## Regression Tests

Before every release.
'@ | Set-Content .\docs\08_TESTING_STRATEGY.md



@'
# Documentation Guidelines

Each module requires:

- Overview
- Installation
- Configuration
- Usage
- Examples
- Troubleshooting
- Changelog

Documentation must stay updated.
'@ | Set-Content .\docs\09_DOCUMENTATION_GUIDELINES.md



@'
# GitHub Packages

Publishing Target

com.codewiththiru.platform

Examples

implementation("com.codewiththiru.platform:core:1.0.0")
implementation("com.codewiththiru.platform:design-system:1.0.0")

Requirements

- Semantic Versioning
- Release Notes
- Changelog
- Git Tags
'@ | Set-Content .\docs\10_GITHUB_PACKAGES.md