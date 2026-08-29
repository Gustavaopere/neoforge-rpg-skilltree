# Stage 10.08 Compendium World Geography Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build server-authoritative BIOME, STRUCTURE and DIMENSION pages plus verifiable geographic relations/discovery for the Compêndio Natural.

**Architecture:** Keep world descriptors/providers/coverage pure Java under `compendium/world`; keep Minecraft registry/world access under `runtime/compendium`. Build one immutable geographic snapshot from dynamic registries and actual dimension biome sources, then atomically publish it. Reuse `CompendiumDiscoveryRuntime`; biome/dimension discovery remains server-observed, while structure discovery must be confirmed by server structure state at the player's position.

**Tech Stack:** Java 21, Minecraft 1.21.1, NeoForge 21.1, GitHub Actions.

**Spec:** `plans/10-compendio-natural/08-biomas-estruturas-dimensoes.md`

## Global constraints

- No hard dependency on YUNG, Integrated Structures, Structory, Cataclysm, MineColonies or any optional worldgen suite.
- Registry/resource IDs are canonical identity; translated names never identify entries.
- Do not infer exact worldgen chance, coordinates or portal recipes from names/config guesses.
- Vanilla climate values must be labeled as vanilla climate, never as TFC physical temperature.
- Replacement/override under the same registry ID must remain one Compendium entry.
- No full registry/structure scan per tick; structure discovery checks are bounded/throttled and server-side.
- World snapshot publication is atomic; invalid candidate does not replace the previous snapshot.

---

### Task 1: Pure world descriptors and providers

**Files:** create `compendium/world/BiomeDescriptor.java`, `BiomeClimateFacts.java`, `BiomeProvider.java`, `StructureDescriptor.java`, `StructurePlacementSummary.java`, `StructureProvider.java`, `DimensionDescriptor.java`, `DimensionProvider.java`, `WorldCatalogSnapshot.java`, `WorldCatalogCoverage.java`; tests `compendium/world/BiomeProviderTest.java`, `StructureProviderTest.java`, `DimensionProviderTest.java`; runner `scripts/compendium/test_world.sh`; workflow `.github/workflows/compendium-world.yml`.

- [ ] Write tests first for vanilla/modded IDs, climate omission vs exact values, cave category only from explicit evidence, structure biome relations, duplicate structure ID replacement without duplicate page, optional dimensions present/absent, immutable snapshot and complete coverage.
- [ ] Open draft PR and record focused RED caused by missing world contracts.
- [ ] Implement minimum pure descriptors/providers and require focused GREEN.

### Task 2: Runtime registry collector and atomic catalog

**Files:** create `runtime/compendium/RuntimeWorldCatalogCollector.java`, `RuntimeCompendiumWorldCatalog.java`, `CompendiumWorldCatalogEvents.java`, validators `scripts/compendium/verify_world_runtime.py`, `verify_world_catalog_publication.py`; modify `RpgSkillTreeMod.java`.

- [ ] Add validators first and record RED because runtime collector/catalog do not exist.
- [ ] Collect BIOME and STRUCTURE from `server.registryAccess()` dynamic registries; collect dimensions from actual server level keys; derive dimension-biome membership from each level's `BiomeSource.possibleBiomes()`; derive structure-biome membership from `Structure.biomes()`.
- [ ] Build complete candidate snapshot, validate identities/coverage, then publish atomically once startup/reload state is valid.
- [ ] Require focused GREEN and full NeoForge build/JAR/smoke GREEN.

### Task 3: Cross indexes and source-aware worldgen semantics

**Files:** pure tests plus world provider/index classes as needed.

- [ ] Test and implement bidirectional indexes Dimension↔Biome and Structure↔Biome; derive Structure↔Dimension only when biome-set intersection proves participation.
- [ ] Ensure one registry ID yields one page even when a datapack/mod replaces its implementation; optional namespaces require no adapter unless generic data is insufficient.
- [ ] Keep placement/frequency contextual/absent unless structurally derivable.

### Task 4: Geographic discovery

**Files:** create `compendium/world/WorldDiscoveryPolicy.java`, `runtime/compendium/CompendiumWorldDiscoveryEvents.java` or extend the existing discovery feed minimally; tests `compendium/world/WorldDiscoveryTest.java`; validator `scripts/compendium/verify_world_discovery.py`.

- [ ] Test biome/dimension monotonic discovery and server-confirmed structure discovery; forged structure ID/path must not be accepted.
- [ ] Reuse existing BIOME_ENTRY and DIMENSION_ENTRY flows.
- [ ] Add bounded structure presence validation at the player's server position, with no client-provided identity and no scan of every registry entry each tick.
- [ ] Require full NeoForge compile and dedicated-server smoke GREEN.

### Task 5: Review, merge and closure

- [ ] Review complete diff against the 10.08 acceptance and verify no scope creep into UI/editorial/optional API wiring.
- [ ] Fetch fresh `main`, reconcile any concurrent commits, and rerun World + Ecology + Flora + Entities + Discovery + full RPG CI on the final mergeable tree.
- [ ] Merge implementation with expected head SHA and verify all push workflows on `main`.
- [ ] From fresh `main`, create closure branch, rename plan to `✅-08-biomas-estruturas-dimensoes.md`, update `plans/STATUS.md`, merge closure PR and verify post-closure CI.
