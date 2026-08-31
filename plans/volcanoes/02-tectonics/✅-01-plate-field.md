# Tectonics Complete — Plate Field

**Goal:** generate stable plate IDs, motion vectors and hotspots from world seed and coordinates.

**Implemented types:** `PlateId`, `PlateVector`, `PlateSample`, `PlateField`, `VoronoiPlateField`.

- [x] Unit-test determinism for fixed seeds/coordinates and continuity within plate interiors.
- [x] Generate large Voronoi-like cells from seeded region centers; each plate receives a normalized horizontal motion vector.
- [x] Expose nearest plate center, plate ID, motion vector and distance to nearest boundary without loading chunks.
- [x] Generate sparse independent hotspot fields so intraplate volcanoes are possible.
- [x] Cache only coarse plate metadata; coordinate sampling remains cheap and deterministic.

## Runtime contract

- Plate geometry is a pure function of `worldSeed + x + z`.
- Coarse Voronoi cells use a 16,384-block scale with deterministic jittered centers.
- Motion vectors are normalized and derived independently per plate.
- Boundary distance is analytical from the nearest and second-nearest plate centers; sampling never loads chunks or terrain.
- Hotspots use a separate sparse deterministic field, so intraplate volcanism is not forced onto plate boundaries.
- Only generated plate metadata is kept in a bounded access-ordered cache; there is no per-block persistent plate data.

## TDD / verification

- Initial RED: `d22b4a488bcbf920e7d2521b7ff7568e2199f73a`, workflow `32918118743` — expected unit-test failure before the plate-field API existed.
- Expanded RED: `3a44596e7f894df4c2cb50d0b619a89f4383715a`, workflow `32918300543` — expected failure with geometry/hotspot/cache contract in place.
- GREEN implementation: `6808a381a7b3c3491384c9e179388021966d1343`, workflow `32918492274` — unit tests, diff sanity, NeoForge build, JAR verification and dedicated-server smoke all passed.

**Acceptance:** satisfied. Sampling requires only seed+x+z, remains stable across restarts and allocates no per-block persistent data.
