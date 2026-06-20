# Security Architecture

## Overview
Remote Config fetches can be an attack vector. The Enterprise Security Layer analyzes payloads before activating them.

## Validators
1. **SchemaValidator**: Checks structure (e.g. valid JSON).
2. **SignatureValidator**: Uses cryptographic signatures (e.g. JWT or ECDSA) to verify the payload source.
3. **ConfigIntegrityValidator**: Checks SHA-256 hash against expected payload integrity hash.

## Threat Analysis & Quarantine
* `ConfigThreatAnalyzer`: Evaluates the config through validators and scans for malicious strings (e.g. `eval(`, `javascript:`). Assigns a `ConfigRiskLevel` (LOW, MEDIUM, HIGH, CRITICAL).
* `ConfigQuarantineManager`: Blocks and logs HIGH risk configs. Quarantines CRITICAL risk configs. Triggers Analytics events.

## Incident Response
* Security violations increase the error count in `RemoteConfigMetrics`.
* High violation count triggers a system-wide "UNHEALTHY" status in `HealthMonitor`.
