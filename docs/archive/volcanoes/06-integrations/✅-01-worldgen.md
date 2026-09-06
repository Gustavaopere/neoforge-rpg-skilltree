# Integration Plan — Terralith, Tectonic and BWG

**Status:** COMPLETE

**Goal:** complement the existing Overworld stack without taking ownership of it.

- [x] Verify actual biome/tag IDs from installed versions before shipping integration data.
- [x] Treat Terralith volcanic/crater terrain as a positive volcano-site hint, not a requirement.
- [x] Treat Tectonic only as terrain shaping; do not assume it supplies plate data.
- [x] Allow BWG biomes to host tectonic/volcanic regions through generic biome suitability rules; no hard dependency.
- [x] Add worldgen smoke tests with all three installed and with each absent individually.

## Closure evidence

- Terralith hints are data-driven, optional tag entries for `terralith:volcanic_crater` and `terralith:volcanic_peaks`; Java core does not hard-code Terralith biome IDs.
- Tectonic remains terrain-shaping only; Volcanoes continues to source tectonic semantics from its own tectonic service.
- BWG participates through generic biome suitability and has no hard runtime dependency from Volcanoes.
- Compatibility matrix run `33278686914` on `04089cc26fb3a2d19474bb672a6c52f10898ebb7` completed GREEN for WG-00 through WG-07.
- The exact pack conflict Terralith 2.6.2 + BWG 2.6.0 is exercised with Biolith NeoForge 3.0.14 in WG-05/WG-07, preserving BWG biomes rather than mutating host configuration. Biolith is not required by the other matrix cases and is not a hard dependency of Volcanoes.
- Main Volcanoes CI run `33278686912` on the same SHA completed GREEN.
- Repository search for `tfc:overworld` returned no matches at closure.

**Acceptance:** no `tfc:overworld`; all three mods can coexist while Volcanoes sites/fields remain deterministic and optional. **PASS.**
