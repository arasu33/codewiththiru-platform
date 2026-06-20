# Migration Guide to Platform Governance 1.0.0

There is no prior module for this. To adopt governance:
1. Include `:platform-governance` in your CI/CD pipelines.
2. Ensure every module resolves all `GovernanceManager` violations to obtain at least BRONZE `CertificationLevel`.
