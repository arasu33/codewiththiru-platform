# Consumer Compatibility Matrix

The CodeWithThiru Platform SDK strictly guarantees backward compatibility. This matrix outlines which SDK versions have been validated against specific consumer applications.

## AquaSort

| AquaSort Version | Required Platform SDK Version | Status | Notes |
| :--- | :--- | :--- | :--- |
| **Upcoming (Next)** | `1.1.x` | Pending Validation | Preparing for new feature integrations. |
| **Current (Prod)** | `1.0.4` | **Verified** | Standard integration. Fully functional. |
| **Legacy** | `1.0.x` | **Verified** | AquaSort must consume only stable 1.0.x releases for hotfixes. |

**Important Rule:** AquaSort must ONLY consume stable releases. Never depend on unreleased `develop` branch snapshots in production environments.
