# Health Monitoring

## Observability
The `HealthMonitor` tracks the real-time operational status of the configuration pipeline.

### HealthSnapshot
Generated periodically, it contains:
- **status**: `HEALTHY`, `DEGRADED`, or `UNHEALTHY`
- **uptime**: Time since initialization
- **metrics**: Dump of the `RemoteConfigMetrics`
- **activeThreats**: List of unquarantined security events

### RemoteConfigLogger
Automatically funnels error states, fallback triggers, and quarantine events to the underlying `:analytics` module.
