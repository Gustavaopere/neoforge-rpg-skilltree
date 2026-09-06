# Foundation Plan — Domain Contracts

**Goal:** define interfaces that decouple volcanism from TFC and optional mods.

**Implemented types:** `geology/RockCategory`, `geology/RockProfile`, `geology/RockProfileResolver`, `tectonics/TectonicContext`, `tectonics/TectonicSample`, `tectonics/TectonicService`, `environment/AtmosphereState`, `environment/AtmosphereService`, `pressure/PressureSample`, `pressure/PressureService`, `volcano/VolcanoState`, `volcano/VolcanoSite`, `volcano/VolcanoService`.

## Contract tests first

- [x] Test `RockProfile` rejects permeability outside its documented normalized range, rejects negative thermal values and supplies an immutable generic fallback.
- [x] Test `TectonicSample` can represent plate interior, convergent, divergent, transform and hotspot contexts without Minecraft world access.
- [x] Test `AtmosphereState` clamps invalid gas/particulate inputs and derives oxygen partial pressure as `totalPressure * oxygenFraction`.
- [x] Test `PressureSample` distinguishes atmospheric, hydrostatic and total external pressure.
- [x] Test all services expose deterministic vanilla-safe fallback implementations without optional mod dependencies.
- [x] Architecture tests forbid production imports from `net.dries007.tfc.*` and `tfcregistryapi.*`.

## TDD evidence

The contract RED was verified in workflow run `32897612111`, which failed with 45 compile errors because the contract packages/types did not exist. A later RED verified the missing TFC Registry API boundary. Final branch HEAD `0115fd1ba022b30ef81bd7dec60bd7ffd96a2987` then passed push workflow `32901372365`, PR workflow `32902286035`, and post-merge `main` workflow `32903071520` attempt 2.

## Acceptance criteria

Feature branches depend on these contracts rather than calling TFC/Terralith/Destroy/Create directly. Optional adapters may enrich results but cannot become required for base behavior.

**Acceptance status:** COMPLETE — merged in PR #2 as `bee79bc77688118e78e73deeb0cb3c06f0b7288b`.
