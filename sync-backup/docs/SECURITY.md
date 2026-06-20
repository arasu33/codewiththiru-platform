# Security & Encryption

- Local data uses SQLCipher (or equivalent injected SecureStorage).
- Backups are encrypted via `EncryptionManager` before transit.
- E2E Key Management ensures cloud providers cannot read the payloads.
