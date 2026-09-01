# Pressure Plan — Atmospheric Pressure

**Goal:** provide a datapack-configurable pressure curve compatible with high-altitude Aeronautics gameplay and oxygen partial pressure.

- [x] Unit-test interpolation through configured altitude-pressure control points.
- [x] Default Overworld curve uses approximately 1 atm near sea level and monotonically decreases with altitude; exact curve lives in data, not hard-coded gameplay branches.
- [x] `AtmosphereState.totalPressure` consumes this service and respiration uses oxygen partial pressure.
- [x] Add adapter point for FlyHigher/Aeronautics/Sable pressure APIs so one authoritative curve can be selected when those mods expose it.
- [x] Nether/End/custom dimensions use data-defined baselines with safe fallback.

**Acceptance:** changing the pressure datapack changes high-altitude breathing and external pressure consistently without recompiling.

## Closure evidence

- Datapack profiles are loaded by `AtmosphericPressureDataLoader`/`AtmosphericPressureRegistry`; bundled profiles exist for Overworld, Nether and End, while undefined custom dimensions use the neutral safe fallback.
- `AtmosphericPressureResolver` selects one highest-priority applicable external authority and fails back to the built-in datapack model on host failure/invalid output instead of mixing pressure models.
- `PressureAtmosphereBaselineProvider` is the canonical Atmosphere pressure bridge, so altitude pressure and oxygen partial pressure share the same authority.
- Interpolation, malformed-data rejection, provider priority/fail-closed behavior and runtime reload behavior are covered by the Stage 05 pressure tests.
- Exact pre-closeout verification: workflow `33263562636` GREEN on `4a7af9778b11ed8548142708838225aee46fec25`.
