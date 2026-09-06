# Geology Plan — Virtual Strata ✅

**Goal:** provide useful geological columns without replacing Terralith/Tectonic/BWG terrain.

**Implemented files:** `geology/GeologyColumn.java`, `Stratum.java`, `StrataService.java`, `DeterministicStrataSampler.java`, `SurfaceRockObservationSampler.java`.

- [x] Write deterministic tests: same world seed + x/z produces identical column; distant regions vary; column layer ordering is valid and covers configured vertical intervals.
- [x] Derive a low-frequency geology region seed independent from biome placement so BWG forests can sit above active geology.
- [x] Bias profiles using actual nearby rock blocks when sampled, but keep the virtual column authoritative for systems that need deep geology without scanning chunks.
- [x] Expose `profileAt(BlockPos)` and `columnAt(x,z)` with bounded cache keyed by geology region.
- [x] Do not replace existing stone en masse in existing or new chunks in this phase.

## Runtime contract

`DeterministicStrataSampler` is a pure world-seed/coordinate sampler. It deliberately does not read biome placement or scan chunks; 8,192-block geology regions provide a stable low-frequency substrate independent from terrain-generator biome choices.

`StrataService` exposes deterministic `columnAt(x,z)` and `profileAt(BlockPos)` queries through a bounded access-ordered cache. Profile IDs are resolved against the current rock-profile registry snapshot so datapack reloads can change physical properties without regenerating the virtual geological column.

Surface observations are bounded and conservative. Recognized nearby rock can bias shallow queries only when there is sufficient strict-majority evidence. Deep queries retain the deterministic virtual profile, so downstream systems do not need invasive chunk scans or block replacement.

## Verification

Final implementation HEAD before this closeout: `cdb93577bea54dcef72afa277367e839a66f00b7`.

GitHub Actions run `32916045328` is GREEN:

- unit tests: success, including deterministic columns, distant-region variation, vertical continuity, bounded cache and shallow-surface-bias behavior;
- `git diff --check`: success;
- NeoForge build: success;
- built JAR verification: success;
- dedicated-server smoke: success.

**Acceptance satisfied:** geology queries are deterministic, cacheable and worldgen-mod agnostic; no chunk-wide stone rewrite occurs in this subsystem.
