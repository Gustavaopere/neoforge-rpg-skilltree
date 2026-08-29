# Stage 10.07 Compendium Ecology Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement safe, source-aware Compendium enrichment for relation targets, loot, food, temptation, breeding, taming and ecology, with atomic reload snapshots and no gameplay side effects.

**Architecture:** Keep pure Java contracts under `compendium/` and isolate Minecraft/NeoForge access under `runtime/compendium`. Extend relations with typed non-entry targets instead of adding an ITEM Compendium kind. Parse loot resources structurally during server data reload into immutable summaries, and keep contextual entity state out of global caches.

**Tech Stack:** Java 21, Minecraft 1.21.1, NeoForge 21.1, GitHub Actions, shell/Python validation gates.

**Spec:** `docs/superpowers/specs/2026-08-28-compendium-10-07-ecology-design.md`

## Global Constraints

- NeoForge 1.21.1 and Java 21 only.
- No `CompendiumEntryKind.ITEM`.
- No loot rolling, entity spawning, commands, functions, block placement, arbitrary reflection or undocumented NBT reads to generate documentation.
- `CURATED_EDITORIAL + EXACT` relations require explicit evidence.
- Food, temptation and breeding are separate semantics and never auto-promote into one another.
- Optional mod enrichments must be fail-soft and must not resolve external classes when the mod is absent.
- Loot/ecology snapshots are immutable and published atomically on reload; invalid staging keeps the previous valid snapshot.
- Habitat/biome/structure/dimension remains Stage 10.08; full external API wiring remains Stage 10.11; global save/network/cache orchestration remains Stage 10.13.

---

### Task 1: Typed relation targets and evidence validation

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumRelationTargetKind.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumRelationTarget.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumRelation.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ProviderMerger.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaValidator.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/api/CompendiumRelationTargetTest.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/data/CompendiumSchemaTest.java`

**Interfaces:**
- Produces: `CompendiumRelationTarget.entry(CompendiumEntryId)`, `.item(String)`, `.itemTag(String)`, `.block(String)`, `.blockTag(String)`, `serializedTarget()`.
- Produces: `CompendiumRelation(type, target, source, confidence, evidenceId)` while retaining the existing constructor that accepts `CompendiumEntryId`.

- [ ] Write failing tests proving item/tag/block targets serialize deterministically; the legacy entry constructor remains valid; `CURATED_EDITORIAL + EXACT` without evidence fails; technical exact relations may omit evidence; schema v1 accepts either legacy `to` or typed `target_kind + target`, never both.
- [ ] Run `bash scripts/compendium/test_model_catalog.sh` and record RED caused only by missing/new relation APIs.
- [ ] Implement the minimum target model, relation validation, deterministic merger ordering and backward-compatible schema rules.
- [ ] Re-run `bash scripts/compendium/test_model_catalog.sh` and require GREEN.

### Task 2: Pure loot summary model and structural parser

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootResolution.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootNumberSummary.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootConditionSummary.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootEntrySummary.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/LootSummary.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/CompendiumLootParser.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/CompendiumLootProvider.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/loot/LootSummaryTest.java`

**Interfaces:**
- Consumes Gson-like `Map<String,Object>`/JSON-neutral structures in the pure parser.
- Produces immutable `LootSummary` with fixed/range/conditional quantity and chance semantics.
- Produces `DROPS` relations targeting ITEM and facts only when mathematically resolvable.

- [ ] Write failing tests for fixed item, fixed/range count, simple constant rolls, player-kill/Looting context, unsupported condition/function becoming `CONDITIONAL`, and empty table producing an empty summary rather than an invented drop.
- [ ] Run focused pure tests via a new `scripts/compendium/test_ecology.sh`; confirm RED on missing loot contracts.
- [ ] Implement the minimum pure parser without executing `LootTable` or Minecraft runtime code.
- [ ] Re-run focused tests and require GREEN.

### Task 3: Food, temptation, breeding, taming and ecology providers

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/FoodRelationProvider.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/TemptationRelationProvider.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/BreedingRelationProvider.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/TamingFacts.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/EcologyRelationProvider.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/ecology/EcologyAdapterContribution.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/FoodRelationProviderTest.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/BreedingProviderTest.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/ecology/EcologyRelationTest.java`

**Interfaces:**
- Providers consume explicit item/item-tag targets and evidence source/confidence.
- `BreedingRelationProvider` carries `canBreed`, required targets, adult requirement, optional cooldown/result when known.
- `TamingFacts` separates species capability from instance tame/owner state.

- [ ] Write failing tests proving food does not imply temptation/breeding; breeding can target item tags; taming capability is separate from instance owner; ecological exact relations require reliable evidence; optional adapter present/absent is fail-soft.
- [ ] Run `bash scripts/compendium/test_ecology.sh`; require RED on missing provider APIs.
- [ ] Implement minimal immutable providers and optional adapter contribution.
- [ ] Re-run focused tests and require GREEN.

### Task 4: NeoForge entity ecology inspection without global instance cache

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeEntityEcologyInspector.java`
- Create: `scripts/compendium/verify_ecology_runtime.py`
- Modify: `scripts/compendium/test_ecology.sh`

**Interfaces:**
- Consumes an existing `Entity`; does not create/spawn one.
- Produces whitelisted contextual tame/owner/adult state only.
- May use stable vanilla `Animal`, `AgeableMob`, `TamableAnimal` APIs but must not retain entity/world references.

- [ ] Add runtime validator first; require class existence, forbid `EntityType#create`, spawn, arbitrary NBT/reflection/client imports and static entity/world caches.
- [ ] Run focused gate and record RED because the inspector is absent.
- [ ] Implement minimal runtime inspector over existing entity instances.
- [ ] Run focused gate plus full NeoForge CI and require compile/smoke GREEN before continuing.

### Task 5: Atomic loot resource reload snapshot

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/compendium/provider/loot/CompendiumLootSnapshot.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumLootResourceReloader.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/RuntimeCompendiumLootCatalog.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- Create: `scripts/compendium/verify_ecology_reload.py`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/compendium/loot/LootSnapshotTest.java`

**Interfaces:**
- Reads server data resources under `loot_table/entities` during reload.
- Builds staging summaries first; publishes immutable snapshot only after complete parse/validation.
- Invalid reload preserves previous snapshot.

- [ ] Write failing pure tests for immutable snapshot replacement and failed staging preserving the previous snapshot.
- [ ] Add runtime validator requiring reload registration and singular `loot_table` path, while forbidding loot rolling (`getRandomItems`, `fill`) and per-tick rebuild.
- [ ] Confirm RED.
- [ ] Implement listener/catalog using the repository's server reload event pattern.
- [ ] Run ecology gate and full NeoForge CI; require GREEN including dedicated-server smoke.

### Task 6: Focal CI, review, integration and closure

**Files:**
- Create/modify: `.github/workflows/compendium-ecology.yml`
- Modify: PR body and Stage 10.07 closure docs only after functional post-merge verification.

**Interfaces:**
- `Compendium Ecology CI` runs `bash scripts/compendium/test_ecology.sh` independently.
- Full `RPG Skill Tree CI` remains the authoritative NeoForge/JAR/smoke gate.

- [ ] Ensure the focal workflow executes pure ecology tests plus runtime/reload validators on push/PR.
- [ ] Review the complete diff against `plans/10-compendio-natural/07-loot-dieta-reproducao-ecologia.md` and the design spec; verify no scope creep into 10.08/10.11/10.13.
- [ ] Fetch fresh `main`; reconcile if it advanced and rerun focal + existing Compendium gates + full CI on the final mergeable tree.
- [ ] Mark PR ready, merge with `expected_head_sha`, and verify all push workflows on the merge commit.
- [ ] In a separate documentation branch, replace `07-loot-dieta-reproducao-ecologia.md` with `✅-07-loot-dieta-reproducao-ecologia.md`, update `plans/STATUS.md` from fresh main counts, open/verify/merge closure PR, and verify post-closure CI.
