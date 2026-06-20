# Troubleshooting Guide

## Configs not updating
1. **Circuit Breaker Active**: If Firebase failed 5 times consecutively, the breaker is open. Wait 30 minutes or clear app data.
2. **Safe Mode**: Check if `SafeModeConfig.isSafeModeEnabled()` returns true. If so, providers are bypassed.
3. **Cache Policy**: Default TTL is 6 hours. Use `forceRefresh()` for immediate updates.

## Experiment Assignments not sticking
Ensure `StickyAssignmentManager` has write access to DataStore. Check if deterministic hashing is producing expected variants based on user IDs.

## Security Violations Triggering
If a legitimate payload is quarantined, ensure it doesn't contain blacklisted keywords (like `javascript:`) or verify the signature key matches the backend payload.
