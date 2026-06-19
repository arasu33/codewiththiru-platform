# Migration Guide

Since this is a greenfield module within `codewiththiru-platform`, no direct migrations from a legacy system are expected at this time.

## Adopting Material 3
When building new UI, ensure you use `androidx.compose.material3.*` components exclusively. Avoid importing legacy `androidx.compose.material.*` components to prevent styling conflicts.
