# Security Policy

## Supported Versions
| Version | Supported          |
| ------- | ------------------ |
| >=0.3.0 | :white_check_mark: |
| <0.3.0  | :x: (PII stored in plaintext) |

## Data Encryption
As of version 0.3.0, the `:feedback` module utilizes `androidx.security:security-crypto` version `1.0.0`. 
All persistent drafts containing sensitive user information (names, emails, submission text) are written directly to `EncryptedSharedPreferences` using:
- **Keys**: `AES256_SIV`
- **Values**: `AES256_GCM`

## Data Redaction
The `FeedbackDataRedactor` interface (default implementation provided) explicitly intercepts all diagnostic and log attachments to strip potential Personally Identifiable Information (PII) before transmission. This includes:
- Email Addresses
- Telephone Numbers

To report a vulnerability, please reach out directly to the core architecture team. Do not open public PRs for security exploits.
