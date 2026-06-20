# Analytics Module (`:analytics`)

The Analytics module provides a provider-agnostic, offline-first infrastructure for tracking events, screens, and user properties across the `codewiththiru-platform`.

## Features
- **Strict Typed Events**: Prevents schema regressions via `AnalyticsEvent`.
- **Offline Persistence**: Queues events when the network is down.
- **Provider Agnostic**: Fans out to multiple destinations simultaneously.
- **Privacy First**: Enforces PII scrubbing and strict consent checks.

## Quick Start
See the [API_GUIDE.md](API_GUIDE.md) for integration details.
