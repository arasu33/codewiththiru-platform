# Feedback Module

The `:feedback` module is an enterprise-grade, offline-first feedback collection SDK for the CodeWithThiru app ecosystem. It operates with zero forced networking dependencies, guaranteeing absolute data sovereignty and security.

## Features
- **Extensible Categories**: Define dynamic categories natively without waiting for library updates.
- **Offline Persistence**: Drafts are automatically encrypted using AndroidX Security Crypto (`AES256_GCM`) and saved locally.
- **Decoupled Submission**: Bring your own network layer via the `FeedbackSubmissionProvider`.
- **MVI Architecture**: Fully decoupled Compose UI driven by a distinct `FeedbackViewModel`.
- **Accessibility First**: Compliant with Android TalkBack, Dynamic Font Sizing, and RTL standards.
- **Data Redaction**: Includes a `FeedbackDataRedactor` to strip out PII (emails/phones) from diagnostics before transmission.

## Setup
See [API_GUIDE.md](API_GUIDE.md) for full integration instructions.
