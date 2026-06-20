# Feature Flag Guidelines

## Overview
Governance over feature flags prevents technical debt and ensures clear ownership.

## Lifecycle Statuses
* **DRAFT**: In development, disabled by default.
* **ACTIVE**: Standard flag behavior.
* **DEPRECATED**: Flag is scheduled for removal. Warns on evaluation.
* **ARCHIVED**: Flag is dead. Always returns default value.

## Metadata & Ownership
* `FeatureFlagMetadata`: Maps a key to an `owner` and `lifecycle`.
* `FeatureFlagOwner`: Requires a `teamName` and `contactEmail`.
* No flag should be introduced without an owner.

## Evaluation
* `FeatureFlagEvaluator`: Intercepts evaluations. If a flag is DEPRECATED, it logs a warning. If ARCHIVED, it short-circuits to the default value.
