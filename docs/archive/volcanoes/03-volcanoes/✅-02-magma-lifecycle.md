# ✅ Volcano Plan — Magma Chamber and Lifecycle

**Status:** implemented and verified on `feat/03-magma-lifecycle`.

**Goal:** model extinct/dormant/active volcanoes and pressure accumulation independently of eruption rendering.

**Implemented types:** `VolcanoState`, `MagmaChamber`, `MagmaComposition`, `VolcanoManager`, `VolcanoTickScheduler`, `VolcanoLifecycleRuntime`.

- [x] Lifecycle transitions use explicit hysteresis. DORMANT activates at 180 MPa, ACTIVE falls dormant at 120 MPa, eruption requires 275 MPa plus gas fraction 0.12, and ERUPTING relaxes back to ACTIVE only after pressure/gas fall below separate lower thresholds.
- [x] `MagmaChamber` models magma volume, pressure, gas fraction, temperature, replenishment rate and stable `MagmaComposition`; all persisted values validate invariants and round-trip through NBT.
- [x] `VolcanoManager` evolves chambers from canonical tectonic samples. Stress, volcanic potential and tectonic context affect supply/buildup; seismic intensity perturbs pressure, gas, temperature and replenishment spatially through `SeismicEvent.intensityAt(...)`.
- [x] Lifecycle data remains in the existing per-level `VolcanoSavedData` under the stable volcano-site UUID. Chambers are optional nested data, so pre-lifecycle saves without chamber state remain loadable; the persisted `VolcanoSite.state` remains the canonical lifecycle state.
- [x] `VolcanoTickScheduler` uses a priority queue plus UUID due-time map rather than scanning all sites every tick. ERUPTING/ACTIVE/near-active sites receive progressively shorter intervals; quiet DORMANT and EXTINCT sites are substantially slower.
- [x] `VolcanoLifecycleRuntime` is server-side and Overworld-only. It discovers newly persisted sites on initialization and on a 24,000-tick rescan cadence, then processes at most 8 due lifecycle updates per tick.
- [x] Tectonic seismic dispatch gained an additive dimension-aware sink while preserving the legacy sink API. The magma runtime reuses `TectonicRuntime.serviceForLevel(...)`, so it does not create a competing tectonic stress service or a second stress source of truth.
- [x] The NeoForge mod entrypoint registers the lifecycle server tick after the volcano worldgen persistence tick and registers the dimension-aware seismic bridge. Dedicated-server smoke validates the wiring without client-class leakage.

## TDD evidence

### Core lifecycle

- RED corrected HEAD: `551cf72e4dcb4f2cef80a36a8d2dbc258a7c800b`.
- RED workflow: `32929089312` — failed at `compileTestJava` because `MagmaComposition`, `MagmaChamber`, `VolcanoManager`, `VolcanoTickScheduler` and lifecycle persistence APIs did not yet exist.
- Core GREEN HEAD: `1f83aa57419019b8f696eee4160f16583dc45ee0`.
- Core GREEN workflow: `32930056060` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

`MagmaLifecycleContractTest` proves chemistry/physical validation, NBT round-trips, state hysteresis, tectonic and seismic forcing, stable-ID persistence, deterministic chamber initialization, spatial seismic influence, priority scheduling and an 80-day deterministic buildup → ACTIVE → ERUPTING → relaxation sequence.

### Server runtime

- RED HEAD: `86a5e0f2438ac8e29447446bf41a18c3cda4c048`.
- RED workflow: `32930380898` — failed only because `VolcanoLifecycleRuntime` and the dimension-aware seismic APIs were absent.
- Runtime GREEN HEAD: `876d7ac8d05ff1bc78946a16b5c7fe9112bc2251`.
- Runtime GREEN workflow: `32930624045` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

`MagmaLifecycleRuntimeContractTest` proves the additive dimension-aware seismic contract, Overworld-only lifecycle execution and the 24,000-tick site-rescan cadence while existing tectonic runtime tests remain green.

## Acceptance

Long deterministic simulations demonstrate stable dormancy, pressure/gas buildup, activation, eruption trigger and post-eruption relaxation. Runtime scheduling is due-time driven; the complete volcano registry is not advanced every tick. Full-registry discovery is intentionally coarse (initialization plus one rescan per Minecraft day), while seismic events may perform a rare spatial reschedule of affected sites.

Eruption rendering, lava behavior, ash and terrain effects remain outside this task and belong to subsequent `03-volcanoes` plan files.
