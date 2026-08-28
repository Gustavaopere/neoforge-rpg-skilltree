# Stage 10.05 Compendium Fauna Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement the Stage 10.05 fauna/entity layer so every relevant registered `EntityType` receives a technical base entry without arbitrary spawning, while runtime instance inspection, variants, special vanilla facts and RPG scaling remain explicit, server-safe enrichments.

**Architecture:** Static species facts are collected from registries, `EntityType` metadata and NeoForge's finalized default-attribute view, then materialized as immutable `CompendiumEntry` sections. Runtime inspection uses a separate whitelisted snapshot DTO derived only from an already-existing server entity; it never serializes arbitrary NBT. Special cases are small inspectors/providers composed over the generic snapshot, and RPG scaling contributes a separate `rpg_scaling` section so base species facts can never be confused with effective instance values.

**Tech Stack:** Java 21, NeoForge 1.21.1, Minecraft registries/default attributes, existing Stage 10.03 Compendium API/catalog/provider model, Stage 10.04 discovery contracts, GitHub Actions CI.

**Spec:** `plans/10-compendio-natural/05-fauna-entidades.md`

## Global Constraints

- Minecraft NeoForge 1.21.1 and Java 21.
- Runtime registry identity is authoritative; translation/display names never define identity.
- Do not instantiate arbitrary entity types only to discover metadata.
- Static species facts and current-instance facts are separate data models and sections.
- Missing standard attributes must yield omitted/unavailable facts, not crashes or fabricated zeroes.
- Optional/modded entities must retain a usable generic page when no dedicated adapter exists.
- No arbitrary entity NBT is sent or exposed through common inspection output.
- Server/common code must not depend on client rendering classes.
- RPG scaling facts must be labeled as effective/current-instance facts, never universal species stats.

---

### Task 1: Entity fact vocabulary and generic species entry factory

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityFactKeys.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityGameplayCategory.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntitySpeciesFacts.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntitySpeciesEntryFactory.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntitySpeciesEntryFactoryTest.java`

**Interfaces:**
- Produces immutable `EntitySpeciesFacts` with registry identity, translation key, source mod, gameplay categories, dimensions, mob category and optional numeric base facts.
- Produces `CompendiumEntry` kind `ENTITY` with `identity`, `base_stats` and `dimensions` sections.

- [ ] Write tests proving registry identity is stable, missing attributes are omitted rather than represented as fake zeroes, categories are deterministic, and static facts are marked `REGISTRY`/`EXACT`.
- [ ] Run the focused entity test gate and capture RED because the new types are absent.
- [ ] Implement the minimal records/constants/factory.
- [ ] Run focused tests and capture GREEN.

### Task 2: Registry and finalized default-attribute collection

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityRegistryProvider.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/LivingEntityAttributeProvider.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityDimensionsProvider.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityCatalogCollector.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityRegistryProviderTest.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/LivingEntityAttributeProviderTest.java`

**Interfaces:**
- `EntityRegistryProvider.collect()` enumerates `BuiltInRegistries.ENTITY_TYPE` without spawning entities.
- `LivingEntityAttributeProvider` reads the finalized default `AttributeSupplier` map exposed by NeoForge/common hooks and only emits present attributes.
- `RuntimeEntityCatalogCollector.collectEntries()` returns one generic base `CompendiumEntry` per classifiable entity type.

- [ ] Add tests for vanilla entity metadata, a type with missing standard attributes, deterministic ordering and no entity construction dependency.
- [ ] Run and capture RED.
- [ ] Implement registry/default-attribute/dimensions collection.
- [ ] Run focused tests and NeoForge compile until GREEN.

### Task 3: Runtime instance inspection and security boundary

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityInstanceSnapshot.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityEffectSnapshot.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityInstanceInspector.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntityInspectionPolicy.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityInstanceInspectorTest.java`

**Interfaces:**
- Inspector accepts an existing server-side `Entity` and returns a bounded whitelist snapshot: identity, dimensions, current/max health and current present attributes for living entities, baby/age where applicable, active effects, tame/owner/sit, breeding state, no-AI/invulnerable/silent/leashed and safe generic flags.
- Policy validates maximum squared distance and optional line-of-sight before inspection is returned.
- Snapshot exposes no raw NBT/CompoundTag field.

- [ ] Write tests for adult/baby semantics, tame state, active effects, missing attributes, distance rejection and absence of arbitrary NBT surface.
- [ ] Run RED.
- [ ] Implement minimal whitelisted inspection.
- [ ] Run focused tests and NeoForge build GREEN.

### Task 4: Variants and small vanilla special inspectors

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityVariantSnapshot.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/EntitySpecialInspector.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/entity/VanillaEntitySpecialInspectors.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityVariantProviderTest.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/VanillaEntitySpecialInspectorsTest.java`

**Interfaces:**
- Special inspectors are ordered small functions, each supports a narrow entity family and returns only stable string/number/boolean facts.
- Initial supported vanilla families: horse variant/markings/tame data, panda genes, villager type/profession/level, bee hive-related state when exposed, dolphin moisture, screaming goat, wandering trader despawn delay, generic tameable owner/sit.
- Unsupported entities return an empty contribution and preserve the generic page/inspection.

- [ ] Write RED tests for representative vanilla special cases and unsupported fallback.
- [ ] Implement only stable server/common APIs; omit any fact whose 1.21.1 API is not safely available.
- [ ] Run GREEN and dedicated-server compile checks.

### Task 5: RPG scaling facts remain distinct from species base facts

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/integration/rpg/RpgEntityScalingCompendiumProvider.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/RpgScalingFactsTest.java`

**Interfaces:**
- Converts an existing `EntityScalingSnapshot` plus current entity attribute values into a separate immutable `rpg_scaling` fact section.
- Emits entity level, rarity and effective values only as current-instance/contextual facts.
- Never overwrites `base_stats.max_health`, `base_stats.attack_damage`, armor or other species facts.

- [ ] Write RED tests proving base and effective values coexist and cannot share the same fact keys/section.
- [ ] Implement provider against current canonical scaling snapshot types.
- [ ] Run GREEN and existing world/entity-scaling regression tests.

### Task 6: Runtime catalog publication and fail-soft generic coverage

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumEntityCatalogRuntime.java`
- Modify `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- Test `src/test/java/dev/gustavopere/rpgskilltree/compendium/entity/EntityCatalogCoverageTest.java`

**Interfaces:**
- Builds/publishes an entity catalog snapshot from runtime registry collection using the Stage 10.03 immutable catalog contracts.
- Every classifiable runtime entity receives at least the generic AUTO entry.
- Dedicated adapters enrich but are never required for page existence.
- Publication is bounded to lifecycle/reload work; no registry scan occurs per tick or per inspection.

- [ ] Write RED test/validator for one-entry-per-runtime-entity coverage and deterministic IDs.
- [ ] Wire lifecycle publication without client classes and without per-tick scans.
- [ ] Run GREEN, NeoForge build and dedicated-server smoke.

### Task 7: Focused CI, structural validator and acceptance review

**Files:**
- Create `scripts/compendium/test_entities.sh`
- Create `scripts/compendium/verify_entity_runtime.py`
- Create `.github/workflows/compendium-entities.yml`
- Modify `.github/workflows/alpha2-build.yml`

**Interfaces:**
- Focused CI runs pure contracts plus NeoForge compile where Minecraft runtime classes are required.
- Structural validator rejects client-only imports in common/server entity Compendium packages, arbitrary NBT exposure and accidental per-tick registry scans.

- [ ] Add focused gate and capture expected RED before implementation tasks become complete.
- [ ] Run all entity tests, Stage 10.01–10.04 gates and current RPG validators.
- [ ] Verify generated-data drift, NeoForge build, built JAR and dedicated-server smoke.
- [ ] Compare final PR against current `main`, reconcile concurrent changes, then merge only with an exact expected head SHA.
- [ ] Run post-merge CI on `main` before formal plan closure.
