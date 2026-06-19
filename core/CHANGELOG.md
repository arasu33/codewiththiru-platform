# Changelog

All notable changes to the CodeWithThiru core module will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0-SNAPSHOT] - 2026-06-19

### Added
- Created `logger` package offering pluggable and thread-safe dynamic `CustLogger`.
- Created `result` package offering functional monadic extensions for `CustResult` (`Success`/`Failure`).
- Created `dispatcher` package offering `CustDispatcherProvider` abstraction for coroutines execution scheduling.
- Automated static styling rules (Detekt, Ktlint) and test reporting (JaCoCo).
