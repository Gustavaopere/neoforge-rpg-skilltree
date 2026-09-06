# TFC Volcanoes 2.2.1 — Ash / Bomb / Pyroclastic Dependency Map

This map is the Stage 03 prerequisite for adapting `plans/03-volcanoes/05-ash-pyroclastics.md`.

## Verified reference

- Published JAR: `TFCVolcanoes-1.21.1-2.2.1.jar`
- File ID: `8710292`
- Verified SHA-256: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`
- Foundation inspection workflow: `32898537754`
- Inspection artifact: `tfc-volcanoes-2.2.1-upstream-inspection`, artifact `9582448664`
- Artifact digest: `sha256:9631742e4ab527c322c0c2e9ab3a69cd7b11e8b5f31ab4d1d40067e65679bfbe`

The source units below were inspected from that decompiled artifact. This document maps behavior and dependencies; it does not copy implementation code. Any later substantial adaptation must continue to follow `docs/upstream/NOTICE.md`.

## `common/entities/PyroclasticBomb.java` — ADAPT

Useful behavior to retain conceptually:

- server-authoritative ballistic projectile;
- randomized launch azimuth/elevation;
- finite fuse/lifetime and impact/explosion presentation;
- optional local ejecta/scoria-style terrain result;
- smoke-trail presentation separated from projectile authority.

Upstream TFC / Registry API dependencies that must not cross into production:

- `TFCBlocks`, `Rock`, `RockCategory`, `RegistryRock`;
- `Ore`, `RegistryOre`, `OreRegistryHelper`;
- TFC `Helpers` and TFC graded-ore/magma-block tables.

Upstream Volcanoes-local dependencies that also require adaptation rather than blind copying:

- `TFCVEntities` registry;
- `TFCVParticles` / `SmokeTrailOptions` presentation;
- `Config`;
- `TFCVHelpers` block/ore/scoria helpers.

Safety correction required by this project:

- do not use unrestricted `Level.ExplosionInteraction.TNT` as the terrain authority;
- no random ore payload injection from TFC registries;
- terrain interaction must be separately budgeted, capped and protection-aware;
- geology outputs must use Volcanoes-owned `RockProfile` / tags / resource contracts when needed.

## `util/FluidBlockEventHelpers.java` bomb spawning — ADAPT

Useful behavior to retain conceptually:

- eruption intensity gates bomb production;
- deterministic/randomized attempt gating;
- spawn source near the active vent/lava surface;
- launch velocity sampled from azimuth + bounded elevation/speed.

Dependencies to replace:

- `TremorEvent` / `TremorData` → canonical `EruptionSignal`;
- TFC rock intensity/replenishment factors → Volcanoes eruption profile/chamber/geology contracts only where causally justified;
- `TremorEvent.getVolcanoNoise` → authoritative persisted `VolcanoSite`/`EruptionSignal.source`;
- `RockRegionHelper` / `RegistryRock` → Volcanoes geology contracts;
- direct lava-surface polling is not required for the canonical eruption source and must not become a second eruption trigger.

The reusable mathematical concept is the bounded ballistic launch vector. Spawn cadence and count must consume the existing `EruptionScheduler.WorkGrant` rather than introducing another scheduler.

## `common/entities/PyroclasticFlow.java` — ADAPT

Useful behavior to retain conceptually:

- gravity/slope-biased horizontal propagation;
- drag and finite lifetime;
- radius growth followed by decay/termination;
- blocked/stuck termination;
- persistent trailing hazard concept;
- heat/particulate entity exposure;
- visual cloud particles separated from server movement authority.

Dependencies to remove/replace:

- TFC `WorldTracker` and `WorldTrackerExt` chunk suppression → no equivalent ownership is imported;
- `PileBlock` ash placement → Stage 03 bounded ash-deposition policy using tags;
- `TFCVHelpers.tryCharNearbyBlocks` → no implicit wildfire/charring ownership in this task;
- `Config` → Volcanoes-owned bounded constants/config later;
- direct blindness/fire/air manipulation → a Volcanoes pyroclastic exposure contract so heat and particulate effects remain explicit and testable.

The upstream flow samples `Heightmap.Types.MOTION_BLOCKING_NO_LEAVES` around the current position to derive a downhill vector. That terrain-following concept is valid, but the Stage 03 core must keep movement calculation separate from world mutation and chunk loading.

## `common/entities/PyroclasticFlowSegment.java` — ADAPT

Useful behavior to retain conceptually:

- a decaying trailing hazard with radius shrinkage;
- finite removal when radius falls below a threshold;
- continuing heat/particulate exposure and presentation behind the moving head.

Dependencies to remove/replace are the same as the flow head: TFC `WorldTracker`, `PileBlock`, TFC charring helpers and direct effect mutation.

A literal linked-list entity chain is not a required compatibility contract. Stage 03 may represent trail segments as bounded immutable/server-state samples if that preserves the useful gameplay behavior with less entity/chunk overhead.

## Ash / plume presentation sources

`client/particles/AshParticle.java` is substantially vanilla-client particle behavior and is classified `PORT`.

`client/particles/PlumeParticle.java` is `ADAPT` because its wind authority is TFC `Climate.get(...).getWind(...)`. Stage 03 must not copy TFC climate ownership. Visual plume drift may later consume a Volcanoes environment/transport input, while authoritative atmospheric concentration remains an Atmosphere source rather than a client particle count.

## Cross-stage Atmosphere boundary

`prep/04-atmosphere` explicitly records `ATM-03-ASH-WIRING`: Stage 03 must provide the final ash-emission/plume contract distinguishing:

1. authoritative atmospheric particulate emission;
2. client visual particles;
3. bounded surface deposition.

Stage 04 already has independent atmosphere source/index mechanics but intentionally does not import Stage 03 across its frozen bootstrap boundary. Therefore Stage 03 will define a narrow ash-emission descriptor with stable identity, source, strength/radius/lifetime semantics. It must not copy the preparatory Atmosphere implementation or invent CO2/SO2 species ratios.

## Stage 03 implementation boundaries

The ash/pyroclastics round must therefore satisfy all of the following:

- one canonical `EruptionSink`-driven runtime; no second eruption lifecycle or cadence;
- aggregate work remains inside the partitioned `EruptionScheduler.WorkGrant` supplied to this consumer;
- ash concentration emission, visual plume work and surface deposition are distinct outputs;
- unloaded chunks are never force-loaded for deposition or terrain effects;
- surface deposition is tag/config driven and bounded;
- bomb trajectories are authoritative but terrain mutation is separately capped and protection-aware;
- pyroclastic movement is terrain-following and finite, while entity exposure and terrain mutation are explicit bounded outputs;
- no `net.dries007.tfc.*`, `tfcregistryapi.*`, TFC WorldTracker, TFC Climate or TFC Registry API dependency is introduced.
