# TFC Volcanoes Upstream Reference

## Canonical baseline

- Project: **TFC Volcanoes** by Verph
- Project ID: `962578`
- Target release: `TFCVolcanoes-1.21.1-2.2.1.jar`
- File ID: `8710292`
- Minecraft: `1.21.1`
- Loader: NeoForge
- Release date: 2026-08-22
- CurseMaven coordinate used by CI: `curse.maven:tfc-volcanoes-962578:8710292`
- Public repository: `Verph/TFC-Volcanoes`
- Upstream license: BSD 2-Clause; see [`NOTICE.md`](NOTICE.md)
- Published JAR SHA-256 verified by Foundation CI: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`
- Inspection workflow run: `32898537754`
- Inspection artifact: `tfc-volcanoes-2.2.1-upstream-inspection`, artifact id `9582448664`
- Inspection artifact ZIP digest: `sha256:9631742e4ab527c322c0c2e9ab3a69cd7b11e8b5f31ab4d1d40067e65679bfbe`

The public repository contained the upstream BSD license but not a complete current implementation source tree at Foundation time. Foundation CI therefore downloads the exact published 2.2.1 JAR, records its hash and class manifest, and decompiles it with CFR solely as an implementation reference for this private project. The upstream JAR is **not** a compile or runtime dependency of Volcanoes.

The inspection produced **169 binary classes** represented by **111 top-level decompiled Java source units**. The exhaustive class-by-class decision is stored in [`TFC_VOLCANOES_CLASSIFICATION.md`](TFC_VOLCANOES_CLASSIFICATION.md): **30 `PORT`, 47 `ADAPT`, 34 `DROP`**. Nested and anonymous binary classes inherit the classification of their enclosing source unit.

## Port policy

- `PORT`: behavior is substantially standalone and may be carried forward after namespace/API review. Any substantially copied implementation must preserve BSD provenance.
- `ADAPT`: useful behavior exists, but TFC/TFC Registry API/world ownership must be replaced with Volcanoes-owned contracts or safer NeoForge hooks.
- `DROP`: the upstream class itself must not be carried forward. A concept may only be independently reimplemented later if an explicit Volcanoes plan requires it.

## Behavioral symbol map

| Concern | Primary upstream symbols | Decision | Volcanoes destination / replacement |
|---|---|---|---|
| Eruption controller and lifecycle | `TremorEvent`, `TremorSavedData`, `TFCFForgeEventHandler` | ADAPT/PORT | `volcano` lifecycle/service + persistent SavedData. Retain buildup/plateau/fade, intensity and eruption scheduling concepts; remove direct TFC world queries. |
| Tremor / earthquake feedback | `TremorEvent.applyToPlayer`, `ClientShakeData`, `ShakePacket`, `PacketHandler` | ADAPT/PORT | `tectonics` seismic events; default gameplay remains non-destructive. |
| TFC collapse / landslide damage | `TFCVCollapseRecipe`, `WorldTrackerMixin`, `WorldTrackerAccessor`, `TFCFallingBlockEntityMixin` | DROP | No TFC collapse subsystem. Future terrain damage, if any, must be opt-in, bounded, natural-block-only and structure/claim aware. |
| Pyroclastic bombs | `PyroclasticBomb`, bomb particles, `FluidBlockEventHelpers.trySpawn*Bombs` | ADAPT/PORT | eruption projectile module. Replace `RegistryRock`, `RegistryOre` and TFC grade payloads with internal geology/resources and safety policy. |
| Pyroclastic flows | `PyroclasticFlow`, `PyroclasticFlowSegment`, flow particles | ADAPT/PORT | eruption/pyroclastic simulation. Replace `WorldTracker`; all block mutation goes through Volcanoes safety gates. |
| Ash / smoke / plume | ash, smoke, trail and plume particle families; `trySpawnSurfaceSmoke`, `trySpawnPlume` | PORT/ADAPT | eruption visuals + atmosphere. TFC `Climate` wind becomes environment/atmosphere input. |
| Wildfire / charring | `TFCVHelpers` charring methods, `CharredBlock`, vanilla fire/mud/bamboo hooks, renderer tint hooks | ADAPT | tag/data-driven wildfire/charring; do not patch TFC vegetation class-by-class. |
| TFC vegetation/agriculture | TFC crops, soils, leaves, saplings, seasonal plants, TFC torch mixins, `TreeHelpers` | DROP | Outside project ownership. Generic natural-block effects are independently data-driven instead. |
| Mineral regeneration / sheets | `computeMineralScore`, `tryPlaceMineralSheet`, `Mineral*`, `ItemRegionHelper` | ADAPT | geology/resource deposits; replace TFC Registry API with internal resource classification and optional RNS adapter. |
| Hot springs / geysers | `handleHotSpringRandomTick`, `Geyser`, geyser particles/renderers, `HotWaterBlockMixin` | ADAPT/PORT | geothermal module using Volcanoes-owned fluids/blocks/events. |
| Rock-dependent lava physics | `TFCVHelpers.getSlopeFindDistance/getDropOff/getTickDelay/getIntensityFactor`, `FlowingFluidMixin`, `LavaFluidMixin`, `LiquidBlockMixin` | ADAPT | `RockProfile` + lava module. This is a high-value upstream algorithm family. |
| Rock regions / igneous assignment | `RockRegionHelper`, `DensityCellular2D`, `VoronoiRegionMap` | ADAPT/PORT | geology/strata spatial fields using `RockProfileResolver`; replace TFC `RegistryRock` and `OpenSimplex2D`. |
| Volcano center/worldgen queries | `TremorEvent.getRegionGenerator/getHotspot*/getVolcano*`, `RegionChunkDataGeneratorAccessor`, `CinderFeatureNoise` | DROP/ADAPT | Own `TectonicService` + `VolcanoService`; Terralith + Tectonic + BWG remains Overworld authority. |
| TFC climate queries | TFC `Climate` use in plume/smoke/pile helpers | DROP and replace | Volcanoes environment contracts; optional Cold Sweat integration later. |
| TFC Registry API | `RegistryRock`, `RegistryOre`, `OreRegistryHelper`, `TFCVInteractionManager` | DROP and replace | internal registries/tags/datapacks plus optional adapters. |
| Rendering compatibility | vanilla/NeoForge renderer mixins plus Sodium-targeted mixins/accessor | ADAPT | Optional client compatibility only; isolate brittle targets and fail closed if renderer internals change. |
| Debug/export tooling | `TFCVCommands`, region map exporters, intensity/volcano map export | ADAPT/DROP | Keep useful admin commands; recreate visualization exporters independently only if later needed. |

## Central algorithm notes

### `TremorEvent`

`TremorEvent` is the principal behavioral reference, not a drop-in standalone class. Useful concepts include event identity, buildup/plateau/fade timing, peak intensity, inner/outer radii, distance falloff, eruption flags, neighbor triggering, plume timing, player shake/sound feedback and NBT persistence.

Its TFC-owned queries are explicitly replaced: `getRegionGenerator`, `getHotspotType/Name/Id`, `getHotspotCenters`, `getVolcanoTypes`, `getVolcanoNoise`, `getVolcanoRarity`, `getVolcanoCenters`, `discoverVolcanoCenter` and `discoverVolcanoCenterNether` must consume Volcanoes-owned `TectonicService`/`VolcanoService` instead. `tryTriggerBlockCollapses` and `tryLinearCascade` are not part of the default standalone earthquake model.

### `FluidBlockEventHelpers` + `TFCVHelpers`

These contain the highest-value adaptation targets for lava/geothermal behavior: rock-dependent slope search, drop-off, tick delay and intensity; lava/hot-spring random ticks; mineral scoring/deposition; pyroclastic bomb spawning and initial velocity; fluid-surface lookup; charring/fire spread; and fluid/entity reactions. All TFC fluid, rock, ore, climate and item-container assumptions must be removed before reuse.

### Spatial helpers

`VoronoiRegionMap` is generic and classified `PORT`. `DensityCellular2D` is nearly standalone but depends on TFC `OpenSimplex2D`, so it is `ADAPT`. `RockRegionHelper` and `ItemRegionHelper` provide useful deterministic-region patterns but currently carry TFC Registry API payloads; the Geology round must substitute `RockProfile` and Volcanoes-owned resource descriptors.

### Pyroclastic and geothermal entities

`PyroclasticBomb` keeps useful projectile/explosion/scoria-deposition behavior but must lose `RegistryRock`/`RegistryOre` coupling and must obey Volcanoes safety policy. `PyroclasticFlow` and `PyroclasticFlowSegment` keep movement, segment and damage concepts but must not depend on `WorldTracker`; any block mutation must be bounded and protection-aware. `Geyser` is largely standalone but its direct `TremorEvent` coupling becomes a Volcanoes seismic/volcano service input.

## Hard boundaries for implementation

1. Production source may not import `net.dries007.tfc.*` or `tfcregistryapi.*`.
2. No substantially copied/adapted source lands without provenance and license treatment from `NOTICE.md`.
3. TFC worldgen/plate/climate ownership is not copied. Volcanoes-owned deterministic services provide those inputs.
4. TFC collapse/landslide/agriculture/metallurgy systems are not dependencies and are not silently recreated.
5. Renderer-specific/mixin-specific adaptations must fail closed when a target changes.
6. Feature branches adapting an upstream algorithm must cite the exact source unit(s) from the exhaustive classification so provenance remains traceable without rediscovering the JAR.
