# Integration Alpha 5 — Malum Spirit Arcana depth pass

Version target: `1.0.0-alpha.5-dev`
Target: Minecraft 1.21.1 / NeoForge 21.1.248
Provider target: Malum 1.8.2
Status: integration Alpha; **not Beta** and not a GitHub Release JAR.

## Research-grounded scope
Malum is not treated as another spell-casting provider. Its character fantasy is Spirit Arcana: breaking souls into spirits, collecting and manipulating those spirits, Soul Ward, Arcane Resonance, Geas/pacts, Spirit Rites and related artifice. Gaze extends that ecosystem; Vestis is primarily cosmetic.

## Included
- optional compile-time integration with the verified Malum 1.8.2 NeoForge artifact (`curse.maven:malum-484064:7307339`);
- registration only when mod id `malum` is loaded;
- Soul Reaping mastery driven by Malum's own `ModifySpiritSpoilsEvent`, which is emitted only after Malum has real spirit-drop data for the shattered soul;
- natural spirit collection mastery driven by Malum's own `CollectSpiritEvent` rather than ordinary item pickup, avoiding drop/re-pickup farming;
- dynamic spirit-affinity evidence read from Malum's `EntitySpiritDropData#getSpiritStacks` at runtime. Item registry ids become addon-safe mastery lanes, e.g. `malum:sacred_spirit` -> `malum:spirit/malum/sacred`; an addon namespace can participate without a hardcoded list;
- reaping reward magnitude scales conservatively with the actual spirit stack count and is capped to prevent boss/compat outliers from exploding mastery;
- creative and NeoForge fake players are excluded;
- shared proc-depth guard remains authoritative for secondary/generated actions;
- canonical stat catalog now recognizes Malum's native gameplay attributes, including Spirit Spoils, Arcane Resonance, Soul Ward, charge mechanics, Geas Limit and malignant aegis/conversion attributes;
- the common OCCULT tree now affects native Malum mechanics when Malum is present:
  - `occult_000`: +1 Spirit Spoils;
  - `occult_001`: +10% base Arcane Resonance;
  - `occult_002`: +2 Soul Ward Capacity;
  - advanced `occult_027`: +1 Geas Limit, allowing one additional active Geas under Malum's own limit rules.

## Why Geas is not another mastery bar
Malum's `GeasSoulData` already owns a persistent set of active Geas and enforces the `malum:geas_limit` attribute. A Geas is therefore a build commitment/state with benefits and drawbacks, closer to a keystone or pact than to XP. This Alpha integrates the capacity stat into the tree but does not clone Malum's Geas system.

## Gaze and Vestis
- Gaze adds progression, Geas, Rites, runes, curios and weapons. Dynamic spirit ids and Malum-native attributes are intentionally addon-safe; deeper Gaze-specific state hooks remain part of Alpha 5.1 when stable APIs are identified.
- Vestis is a cosmetic scythe expansion. It intentionally receives no fake mastery track.

## Deferred to Alpha 5.1
Malum 1.8 moved Spirit Types and Spirit Rites into registries and reworked world rites around Rite Locus/Rite Anchors. No clear public NeoForge event for a completed rite was found during this pass. Rite/infusion progression will not be implemented through a fragile mixin solely to award XP; Alpha 5.1 will use a stable provider hook or explicit, well-tested instrumentation if one is identified.

## Verification gate
This Alpha closes only after core tests, data/effect/runtime validation, full NeoForge Gradle build, JAR structure verification and dedicated-server smoke pass. The smoke server does not load the user's complete modpack, so full 569-mod pack testing remains a later release gate.
