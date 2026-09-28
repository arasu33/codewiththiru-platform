# 🌿 Git Branching Strategy & Repository Topology

This document outlines the branching model, branch lifecycle management, and repository hygiene standards for the **CodeWithThiru Platform SDK**.

---

## 1. Branch Audit & Redundancy Analysis

An audit of the repository branches reveals the following topology:

| Branch Name | Role | Status / Observation | Recommendation |
|---|---|---|---|
| `main` | Default & Production Release | Primary source of truth. Stable code. | **Retain as default public branch.** Protect with branch rules. |
| `develop` | Integration Branch | Standard GitFlow integration branch for active development. | **Retain as integration branch.** Protect with CI checks. |
| `dev` | Duplicate Integration Branch | Points to identical commit as `develop`. Duplicate naming anti-pattern. | **Deprecate & Delete.** Causes contributor confusion. |
| `prod` | Production Mirror | Points to identical commit as `main`. Redundant in standard GitHub Flow. | **Consolidate with `main` or reserve strictly for production CD deployment triggers.** |
| `release/1.0.x` | Stale Release Branch | Stale branch rebased to tip of `main`. | **Delete.** Historical releases should be preserved via git tags (`v1.0.x`), not permanent branches. |
| `release/2.0` | Stale Release Branch | Stale branch rebased to tip of `main`. | **Delete.** Next major release branch should only be created when v2.0 stabilization begins. |
| `gh-pages` | Documentation Hosting | Contains MkDocs statically generated documentation. | **Retain.** Managed automatically by GitHub Actions. |

---

## 2. Standard Enterprise Branching Model

We follow a **Modified GitFlow / GitHub Flow hybrid** optimized for modular SDKs and open-source collaboration:

```
main (v1.5.0) ──●──────────────●──────────────● (v1.6.0)
                 \            / \            /
                  \          /   \          /
develop ───────────●────────●─────●────────●────
                    \      /       \      /
feature/*            ●────●         ●────●
```

### 1. `main` (Production & Public Releases)
- **Purpose**: Stable, production-ready releases.
- **Default Branch**: This is the default branch seen by public consumers.
- **Protection**:
  - Direct pushes **strictly prohibited**.
  - Pull requests required with at least 1 approving review.
  - Required status checks: GitHub Actions CI (build, lint, unit tests).
- **Tags**: Every merge to `main` must be accompanied by an annotated semantic tag (e.g., `v1.5.0`).

### 2. `develop` (Integration Branch)
- **Purpose**: Aggregation branch for ongoing feature development, snapshot builds, and integration testing.
- **Protection**:
  - Direct pushes prohibited for external contributors.
  - Pull requests required.
  - Required status checks: Fast-pass CI checks.

### 3. Ephemeral Branches (Short-Lived)

#### `feature/<feature-name>`
- **Source**: `develop`
- **Target**: `develop`
- **Naming**: `feature/biometric-auth`, `feature/compose-widgets`
- **Lifecycle**: Deleted immediately after PR merge.

#### `bugfix/<issue-name>`
- **Source**: `develop`
- **Target**: `develop`
- **Naming**: `bugfix/fix-admob-crash`
- **Lifecycle**: Deleted immediately after PR merge.

#### `release/v<version>`
- **Source**: `develop`
- **Target**: `main` and `develop`
- **Purpose**: Stabilization, version bumping, and changelog finalization prior to tagging.
- **Lifecycle**: Merged to `main` (tagged), back-merged to `develop`, then deleted.

#### `hotfix/v<version>`
- **Source**: `main`
- **Target**: `main` and `develop`
- **Purpose**: Urgent security or crash fixes in published libraries.
- **Lifecycle**: Merged to `main` (tagged), back-merged to `develop`, then deleted.

---

## 3. Semantic Versioning & Tagging Policy

The platform strictly adheres to **[Semantic Versioning 2.0.0](https://semver.org/)**:

```
MAJOR.MINOR.PATCH (e.g., 1.5.0)
```
- **MAJOR**: Breaking API changes or structural architectural overhauls.
- **MINOR**: Backward-compatible new modules, APIs, or features.
- **PATCH**: Backward-compatible bug fixes and security patches.

### Tag Naming Convention
- Semantic tags must use the prefix `v`: `v1.5.0`.
- Release notes must be attached to the GitHub Release associated with the tag.

---

## 4. Branch Cleanup Execution Guide

To clean up redundant branches from the remote repository, run the following commands:

```bash
# 1. Delete redundant integration branch
git push origin --delete dev
git branch -D dev

# 2. Delete stale release branches (anchored by git tags)
git push origin --delete release/1.0.x
git branch -D release/1.0.x

git push origin --delete release/2.0
git branch -D release/2.0

# 3. Synchronize local references
git fetch --prune
```

---

## 5. Branch Synchronization Checklist

When releasing a new version:
1. Ensure all PRs are merged into `develop` and CI passes.
2. Fast-forward / merge `develop` into `main`.
3. Create annotated Git tag on `main`: `git tag -a v1.5.0 -m "Release v1.5.0"`
4. Push `main` and tags: `git push origin main --tags`
5. (Optional) Fast-forward `prod` if used as a deployment webhook: `git checkout prod && git merge main && git push origin prod`
