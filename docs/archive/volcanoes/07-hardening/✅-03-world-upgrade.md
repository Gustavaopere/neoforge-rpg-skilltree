# Hardening Plan — World Persistence and Upgrade

**Goal:** preserve existing worlds and future Volcanoes save compatibility.

- [x] Version all SavedData payloads for plates/stress, sites, chambers and deposits.
- [x] Unit-test round-trip serialization and migration from the first released unversioned schema (treated as v1) to schema v2.
- [x] Existing chunks are never automatically reshaped to add physical volcanoes.
- [x] Provide explicit admin/debug command for registering/detecting volcano sites in existing terrain; command previews before mutation and apply requires the one-shot preview token.
- [x] Corrupt/unknown saved entries are isolated with actionable logging rather than crashing world load where safe; future schema versions load fail-closed/read-only so normal runtime mutation cannot overwrite unfamiliar data.

## Closed contract

### Persisted schemas

The first released payloads had no explicit schema field and are therefore treated as **v1** during load. Current writes are **v2**:

- `TectonicRegionState` (`volcanoes_tectonic_stress`) writes `schema_version=2` for the persisted regional plate/stress state.
- `VolcanoSavedData` keeps its existing shared storage name/layout for compatibility while independently versioning logical site and chamber families with `sites_schema_version=2` and `chambers_schema_version=2`.
- `DepositRegistry` (`volcanoes_geological_deposits`) writes `schema_version=2`.

No storage key was renamed and the released unversioned NBT shape remains readable. A future schema newer than v2 is not guessed: the corresponding SavedData loads empty/read-only with an error log and rejects runtime writes, avoiding destructive downgrade overwrites.

### Corruption recovery

Known-schema list entries are decoded independently. Invalid stress/deposit/site entries are skipped with index/schema diagnostics. A corrupt embedded magma chamber or eruption is omitted without discarding an otherwise valid volcano site. Valid neighboring entries survive the same load.

### Existing-world terrain safety

Physical volcano shaping remains exclusively in normal feature worldgen. `VolcanoWorldgenRuntime.shouldQueueSiteRegistration(...)` continues to admit only `event.isNewChunk()` in the Overworld, so loading an already-generated chunk never queues automatic volcano registration or terrain shaping.

The explicit operator flow is:

- `/volcanoes world_upgrade preview <chunk_x> <chunk_z>` — resolves the canonical site candidate, performs spacing/existing-state preflight and emits a bounded one-shot token. It mutates neither terrain nor SavedData.
- `/volcanoes world_upgrade apply <token>` — re-resolves the candidate and validates dimension, world seed, expiry and candidate identity before registering **site metadata only** through `VolcanoSavedData.register(...)`. It never invokes `VolcanoWorldgenFeature` or any terrain mutator.

Preview tokens expire after 1,200 game ticks, are consumed on apply attempts and are bounded in memory.

## Verification

TDD RED was captured on PR #90 before production support existed: Performance Hardening Acceptance run `33348850851` failed at `compileTestJava` because `ExistingWorldVolcanoAdminSession` was absent. The same log also exposed and led to correction of an invalid test fixture enum before GREEN validation.

Code-head `40e7d562ca7c9f99c9f9de2566e205a5a522581a` passed:

- Volcanoes CI `33349268687` — unit tests, diff sanity, NeoForge build/JAR, GameTests and dedicated-server smoke;
- Performance Hardening Acceptance `33349268707`;
- Create Sable Acceptance `33349268718`;
- Cold Sweat Heat Acceptance `33349268708`;
- RNS Hydrothermal Acceptance `33349268676`;
- MineColonies Claim Acceptance `33349268681`.

Full-pack save/reload and the worldgen matrix are also required on the final exact PR head before merge.

**Acceptance:** adding Volcanoes to an existing world does not rewrite existing terrain or colonies; released unversioned state migrates to the explicit v2 schemas and survives serialization/restart paths, while future/invalid data fails safely instead of being silently rewritten.
