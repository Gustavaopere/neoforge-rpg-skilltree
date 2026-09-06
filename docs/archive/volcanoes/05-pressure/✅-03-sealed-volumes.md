# Pressure Plan — Sealed Volumes

**Goal:** allow submarines, airships and future sealed rooms to carry internal atmosphere/pressure distinct from outside conditions.

- [x] Define `EnclosedEnvironmentProvider` SPI returning sealed state, internal pressure and optional `AtmosphereState` for an entity/location.
- [x] External providers are queried in priority order; absent providers fall back to world atmosphere/water pressure.
- [x] Sable/Create vehicle adapters may provide internal state only after API behavior is verified; no reflection loop on every tick.
- [x] Model breach/flood state only when the host mod exposes reliable data; otherwise fail closed to external pressure rather than grant unsafe immunity.
- [x] Cache provider resolution per entity/vehicle for a short bounded lifetime.

**Acceptance:** the core pressure system supports sealed environments without depending on any one submarine/ship mod.

## Closure evidence

- `EnclosedEnvironmentProvider`, `EnclosedEnvironment`, `EnclosedEnvironmentQuery` and `EnclosedEnvironmentResolver` form a host-neutral SPI with internal pressure plus optional internal atmosphere.
- Resolver ordering is deterministic; absent providers return outside-environment behavior.
- Unreliable, flooded, throwing or linkage-broken authoritative providers fail closed to external pressure instead of granting immunity or falling through to a second external authority.
- Cache entries are bounded, scoped by occupant/vehicle/sample block, expire on TTL and support explicit invalidation.
- Concrete Create/Sable vehicle interior adapters remain conditional Stage 06 integration work until a reliable host contract is verified; Stage 05 intentionally does not invent breach/seal semantics.
- Exact pre-closeout verification: workflow `33263562636` GREEN on `4a7af9778b11ed8548142708838225aee46fec25`.
