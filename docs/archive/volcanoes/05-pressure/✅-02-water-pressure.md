# Pressure Plan — Hydrostatic Water Pressure

**Goal:** implement continuous underwater pressure rather than fixed depth bands.

**Core relation:** `P_total = P_atmosphere_surface + rho * g * depth` represented in gameplay-friendly atm/kPa units.

- [x] Unit-test pressure at surface, moderate depth and deep water for monotonicity and configured water density/gravity constants.
- [x] Determine water depth from the entity eye/body location and nearest connected surface using bounded/cached logic; avoid scanning to world top every tick.
- [x] Convert excessive pressure exposure into staged discomfort, movement/neurological penalties and barotrauma damage with grace periods.
- [x] Do not apply external water pressure to entities confirmed inside a sealed dry volume.
- [x] Make thresholds configurable and protection capability-based.

**Acceptance:** ordinary swimming remains normal, deep diving becomes progressively hazardous, and pressure math is independent of a particular ocean/worldgen mod.

## Closure evidence

- `PressureService` implements the continuous hydrostatic relation with configurable gravity/density and explicit atm conversion; surface/moderate/deep behavior is covered by `HydrostaticPressureTest`.
- Connected-water depth lookup is bounded and cached; the runtime does not scan blindly to world top every tick.
- Exposure progresses through normal/grace/impairment/barotrauma behavior; movement, neurological effects and pulsed damage are covered by `PressureEntityEffectPolicyTest` and exposure-model tests.
- `PressureEnvironmentResolver` removes external hydrostatic exposure only for a reliable sealed dry interior and otherwise fails closed to the real outside pressure.
- `PRESSURE_RATING` is resolved as a modular capability and applied against actual overpressure without changing physical environmental pressure.
- Exact pre-closeout verification: workflow `33263562636` GREEN on `4a7af9778b11ed8548142708838225aee46fec25`.
