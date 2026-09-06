# Volcano Plan — Geothermal Features and Hot Springs

**Goal:** create natural hot springs, geysers, fumaroles and hydrothermal deposits from the same tectonic/geology model.

**Planned types:** `GeothermalActivityService`, `GeothermalFeatureType`, `HotSpringFeature`, `FumaroleSource`.

- [x] Test geothermal potential increases near magma chambers, hotspots and selected boundary types.
- [x] Generate features only in suitable new chunks with water/terrain predicates and minimum spacing.
- [x] Support hot springs, geysers, fumaroles, sulfurous vents and mud-pot-like variants through configured feature profiles.
- [x] Feed heat to the Cold Sweat adapter and geothermal gases to the canonical Atmosphere adapter.
- [x] Create optional hydrothermal `GeologicalDeposit` entries for RNS rather than inventing a separate scanner.

**Acceptance:** geothermal features have causal placement, remain useful without optional mods and enrich those mods when adapters are present.

## Completion evidence

- Core geothermal/hot-springs runtime: PR #27, merge `f4d0c928c17febd3d2c6c11f036f5e5cac47aab1`; exact reconciled workflow `33171235992` GREEN through unit tests, diff sanity, NeoForge build, JAR verification, GameTests and dedicated-server smoke.
- Geothermal gases -> Atmosphere: PR #48, merge `3fbe7d655f4ac75d291b5ec4502418fbdf89520f`; exact-head pipeline GREEN. Stable source identity/replay/removal is preserved and no second atmospheric authority is introduced.
- Volcanic/geothermal heat -> Cold Sweat 2.4.2: PR #49, merge `e5e2bf54dee60142db30cd54d057e164178e6f66`; standard CI `33183106506` GREEN and exact-host acceptance `33183104281` GREEN. LAVA, PYROCLASTIC and GEOTHERMAL producers are covered through the bounded canonical heat service.
- Hydrothermal deposits -> Create: Rock & Stone 1.3.1-1.21.1-6: PR #50, merge `e5577094c259232e1c787163af7029f3ca880601`, established the first exact-host `DepositRegistry` add/remove/replay/restart integration. Its earlier native-worldgen ownership-handoff framing is superseded by the final coexistence contract in PR #82 (`7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`) plus ownership-safety hardening PR #84 (`c26e97c136b543f1fa0ef2ebb12044d10d8af816`): RNS keeps prospecting and native worldgen authority for Cu/Fe/Au/Sn/Ni/Zn/Ag; Volcanoes physically generates and projects only bounded, physically proven hydrothermal Cu/Fe/Au locations; Volcanoes-owned RNS custom records carry the authoritative source UUID for restart-safe rebind/removal.

Destroy integration is tracked separately under Stage 06 Integrations and the Atmosphere pending inventory; it is not claimed as part of this Stage03 completion.

All Stage03 Task06 cross-stage entries have met their objective removal conditions. Stage 03 Volcanoes is complete after this documentation closeout.
