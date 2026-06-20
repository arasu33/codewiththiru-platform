# Security Audit

- Payload validation via `NotificationSecurityValidator` ensures no malicious deep links are routed.
- Content relies strictly on HTTPS or custom `codewiththiru://` schemes.
- No leakage of PII through unencrypted payloads.
