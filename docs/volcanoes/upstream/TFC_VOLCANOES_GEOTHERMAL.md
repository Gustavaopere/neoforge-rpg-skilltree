# TFC Volcanoes 2.2.1 — Geothermal Dependency Map

This map records the Stage 03 provenance boundary for `plans/03-volcanoes/06-geothermal-hot-springs.md`.

## Verified reference

- Published JAR: `TFCVolcanoes-1.21.1-2.2.1.jar`
- File ID: `8710292`
- Verified SHA-256: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`
- Foundation inspection workflow: `32898537754`
- Inspection artifact: `tfc-volcanoes-2.2.1-upstream-inspection`, artifact `9582448664`
- Artifact digest: `sha256:9631742e4ab527c322c0c2e9ab3a69cd7b11e8b5f31ab4d1d40067e65679bfbe`
- Upstream classification: `docs/upstream/TFC_VOLCANOES_CLASSIFICATION.md`
- License/provenance policy: `docs/upstream/NOTICE.md`

The Foundation inspection classifies the geothermal-related source units below as PORT/ADAPT references. Stage 03 uses them as behavioral/provenance references only; the current geothermal implementation is original code over Volcanoes-owned geology, tectonics, magma, persistence and safety contracts. The upstream JAR is not a compile or runtime dependency.

## `common/entities/Geyser.java` — ADAPT

Useful upstream concept retained:

- an active geothermal feature may produce intermittent geyser behavior rather than remaining purely decorative;
- server authority owns whether the effect is active;
- presentation is separate from the underlying geothermal source.

Volcanoes replacement:

- no persistent `Geyser` entity is ported;
- `GeothermalSourceRegistry` is the persistent authority;
- `GeothermalGeyserCycle` derives a deterministic bounded period/phase from stable source identity;
- `GeothermalNativeEffects` discovers only nearby due geysers through the bounded heat/source indexes;
- `GeothermalWorldgenRuntime` publishes particles, bounded entity exposure and a TTL heat pulse;
- restart activation, scheduled-pulse dedupe and lifecycle cleanup prevent replay/double-fire.

The period/phase algorithm, observer sampling, recovery window and pulse-history model are Volcanoes-original and are not a port of the upstream entity implementation.

## Geyser particle/rendering units — PORT reference, not copied

Foundation classifies the following as substantially standalone references:

- `client/particles/GeyserOptions.java`;
- `client/particles/GeyserParticle.java`;
- `client/render/entity/GeyserParticleRenderer.java`;
- `common/entities/GeyserParticle.java`.

Stage 03 does not copy these classes. Native geothermal presentation currently uses vanilla server particles (`SPLASH` and `CLOUD`) so common/server code has no custom client renderer dependency. A future client polish pass may revisit these references only with separate provenance/API review.

## `mixin/fluids/HotWaterBlockMixin.java` — ADAPT reference, not ported

Useful concept:

- geothermal/hot-water locations can have local gameplay effects rather than being ordinary decorative water.

Dropped/replaced assumptions:

- no TFC hot-water block or TFC fluid ownership is imported;
- no mixin into TFC hot-water classes exists in Volcanoes;
- current Stage 03 hot springs are bounded vanilla surface expressions plus persistent geothermal metadata;
- environmental/body-temperature effects are exported through neutral `VolcanicHeatService` and consumed by the canonical exact-version Cold Sweat adapter;
- atmospheric gas/concentration effects remain owned by canonical Stage 04 Atmosphere.

## `util/FluidBlockEventHelpers.java` / `util/TFCVHelpers.java` — ADAPT

The Foundation inventory identifies these helpers as high-value references for hot-spring random ticks, local mineral behavior and fluid/entity reactions.

Concepts retained only where they fit the standalone design:

- geothermal activity should be causally tied to volcanic/thermal context;
- surface expression and local effects should remain finite/local;
- hydrothermal activity may project geological/mineral metadata;
- active geothermal features may expose heat to nearby entities/integrations.

Dependencies and ownership explicitly replaced:

- `TremorEvent` / TFC volcano queries -> Volcanoes `TectonicService`, deterministic `VolcanoWorldgenResolver`, `MagmaChamberFactory` and `GeothermalActivityService`;
- TFC rock/ore registries -> canonical `GeologicalDeposit`, `DepositRegistry` and Volcanoes resource tags;
- TFC hot-water/random-tick ownership -> deterministic chunk-owned `GeothermalWorldgenFeature` plus persistent `GeothermalSourceRegistry`;
- TFC climate/environment authority -> neutral Stage 03 heat/gas outputs consumed by the canonical Atmosphere/Cold Sweat/Destroy adapters;
- direct block scanning for mineral regeneration -> deterministic `HydrothermalDepositProjector`, with the canonical RNS adapter consuming physically proven Cu/Fe/Au deposit lifecycle as owner-marked custom prospecting locations rather than inventing another scanner.

No TFC `RegistryRock`, `RegistryOre`, TFC `Climate`, TFC WorldTracker or `net.dries007.tfc.*` runtime dependency is introduced.

## Hydrothermal deposits — concept adapted, representation replaced

TFC Volcanoes contains mineral-sheet/regeneration behavior around volcanic fluids. Volcanoes does not port that block-oriented scanner.

Stage 03 instead:

- deterministically decides whether an eligible geothermal placement yields hydrothermal metadata;
- writes one stable `GeologicalDeposit` with `DepositOrigin.HYDROTHERMAL`;
- places the deposit volume fully below the sampled surface;
- uses `DepositRegistry` as geological authority;
- exposes lifecycle events consumed by the canonical exact-version RNS projection for bounded, physically proven Cu/Fe/Au bodies;
- never makes RNS or another ore mod authoritative for Stage 03 geology.

This preserves the useful geological relationship while removing TFC block/ore registry ownership and repeated world scanning.

## Placement / geothermal-potential model — Volcanoes-original

The following Stage 03 behavior is not claimed as a TFC algorithm port:

- `GeothermalActivityService` weighting of tectonic context, boundary proximity, stress, volcanic potential and magma-chamber contribution;
- deterministic chunk-centered candidate lattice and minimum-spacing model;
- five profile families (`HOT_SPRING`, `GEYSER`, `FUMAROLE`, `SULFUROUS_VENT`, `MUD_POT`);
- terrain/slope/shallow-water predicates;
- surface-Y anchoring;
- persistent source identity and transactional worldgen queue;
- bounded heat index, expiry deadlines, observer sampling and pulse recovery.

These are independently implemented to satisfy the Volcanoes plan and its existing tectonic/geology contracts.

## Current Stage 03 safety / ownership boundaries

- Geothermal worldgen never force-loads neighboring chunks.
- Visible mutation is bounded by the configured feature radius (currently at most four blocks) and remains inside the owner chunk.
- Worldgen reserves metadata capacity before block mutation; successful mutation is committed and persisted on the server tick.
- Optional integration mods never become geothermal or geological authority.
- Cold Sweat consumes bounded `VolcanicHeatService` through the canonical Stage 06 adapter.
- Atmosphere consumes stable `GeothermalSource` gas lifecycle through the canonical Stage 04 bridge.
- Destroy remains the canonical optional pollution/acid-rain authority when installed.
- RNS consumes only physically proven Cu/Fe/Au `DepositRegistry` lifecycle through the canonical PR #82 coexistence bridge, with PR #84 owner-marker hardening. RNS retains prospecting and native worldgen authority for Cu/Fe/Au/Sn/Ni/Zn/Ag; Volcanoes never disables or transfers that native worldgen authority. Restart rebind/removal of a Volcanoes custom RNS location requires the matching durable host-local source UUID marker.
- No `net.dries007.tfc.*` or `tfcregistryapi.*` import is permitted in production.

## Stage 03 provenance conclusion

Task 06 adapts the upstream **concepts** of hot springs, active geysers and hydrothermal/mineral consequences, but does not port TFC world ownership, registries, hot-water mixins, custom geyser entities or renderer classes. The standalone implementation is intentionally expressed through Volcanoes-owned deterministic services and bounded server-side contracts. PR #82/#84 activate RNS prospecting coexistence for Volcanoes-owned physical hydrothermal Cu/Fe/Au bodies without transferring or disabling RNS native worldgen.
