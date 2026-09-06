# 05 — Pressure

**Implementation branch:** `feat/07-pressure`, created after Atmosphere merged.

Pressure is a shared physical layer: atmospheric pressure comes from dimension/altitude; water adds hydrostatic pressure; enclosed vehicles/rooms may override external atmosphere through verified adapters.

## Status

- [x] `✅-01-atmospheric-pressure.md` — datapack pressure authority, dimension profiles, external-provider SPI and Atmosphere bridge.
- [x] `✅-02-water-pressure.md` — continuous hydrostatic pressure, bounded depth lookup, staged exposure and pressure-rating protection.
- [x] `✅-03-sealed-volumes.md` — host-neutral enclosed-environment SPI, bounded cache and fail-closed semantics.
- [x] `✅-04-equipment.md` — modular protection capabilities, armor/Curios aggregation, Create oxygen and canonical filter tags.

Stage 05 does not invent concrete vehicle breach/seal semantics or Destroy-specific filter ratings when the host API does not expose a reliable contract. Those host-specific adapters remain Stage 06 integration work and fail closed in their absence.

Pre-closeout functional verification: workflow `33263562636` GREEN on `4a7af9778b11ed8548142708838225aee46fec25`.
PR: #77 (`feat/07-pressure` → `main`).
