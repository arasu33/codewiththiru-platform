# Disaster Recovery Framework

## Overview
Protects the app from bad remote configurations that cause crashes or severe degradation.

## Safe Mode
* `SafeModeConfig`: Allows targeted disabling of systems (Firebase, Experiments, Feature Flags).
* When activated, the system bypasses the problematic layers.

## Fallbacks
1. **Last Known Good Config**: The `RemoteConfigRecoveryManager` can restore the last cached config that didn't trigger crashes.
2. **JSON Fallback**: If network is down or providers fail `MaxFailures` (Circuit Breaker), the system switches to a bundled `remote_config.json`.
3. **Emergency Hardcoded Config**: `EmergencyFallbackConfig` is the ultimate fallback, containing hardcoded maps to disable dangerous features if the JSON and Cache are corrupted.

## Circuit Breaker
* Limits fetch attempts to prevent battery drain or overwhelming a recovering server.
* Exponential backoff starts at 5s, caps at 30m.
