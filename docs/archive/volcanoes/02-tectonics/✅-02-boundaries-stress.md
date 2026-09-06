# Tectonics Complete — Boundaries and Stress

**Goal:** classify plate interactions and accumulate meaningful stress for volcanism/seismicity.

**Implemented types:** `BoundaryType`, `PlateBoundarySample`, `PlateBoundaryClassifier`, `TectonicStressService`, `TectonicRegionState`, `TectonicSample`.

- [x] Test vector pairs classify into `CONVERGENT`, `DIVERGENT`, `TRANSFORM`, or `INTERIOR` using explicit boundary-normal and tangential relative-motion thresholds.
- [x] Compute volcanism potential: high at convergent/subduction-style boundaries and hotspots, moderate at divergent rifts, low at transform/interior by default.
- [x] Persist coarse regional stress, not plate geometry; seismic release relaxes persisted stress.
- [x] Clamp stress and update regions on a long cadence with a strict per-cycle budget rather than per block/tick.
- [x] Expose canonical `TectonicSample` for volcano-site and geothermal consumers.

## Runtime contract

- `PlateSample` includes the nearest neighboring plate, its motion and the exact normal from the selected center toward that neighbor.
- Classification is based on relative motion projected onto the boundary normal/tangent rather than arbitrary biome or terrain labels.
- `TectonicRegionState` is per-dimension NeoForge `SavedData`, stores only normalized coarse stress at an 8,192-block regional scale and serializes deterministically.
- `TectonicStressService` defaults to a 600-tick cadence and at most 16 region updates per evolution pass.
- `TectonicRuntime` is registered on `LevelTickEvent.Post`; substantive work is gated to that long cadence and services are weakly retained per loaded level so the rotating region budget persists without retaining unloaded dimensions.
- Active-player sampling is bounded, and unloaded/unobserved areas receive no per-tick scanning.

## TDD / verification

Boundary classification:
- RED: `15516527ee9ef225274f413e3d42249ffe27eb69`, workflow `32918742680`.
- GREEN: `62f799d64b36a0c6c4a66a10ead94c36fc1a9225`, workflow `32918937635` — full CI GREEN.

Stress evolution/persistence:
- RED: `020f5b8aa37c9d600bc8ed4c7897690a0ed431d9`, workflow `32919206882`.
- GREEN: `01cb41e31ef6791763f3f598024b0464a7ff9374`, workflow `32919368672` — full CI GREEN.

Server orchestration:
- RED: `95c8980cede3f02cbe104efef38ee57e1367d5e9`, workflow `32920613879` — expected failure before `TectonicRuntime` existed.
- GREEN: `9c4c2f2acbb6d34fb5916c7a8782eaa1233274a0`, workflow `32920876929` — unit tests, diff sanity, NeoForge build, JAR verification and dedicated-server smoke all passed.

**Acceptance:** satisfied. Identical deterministic plate geometry combined with saved regional stress produces reproducible behavior while expensive evolution is region-budgeted and cadence-gated.
