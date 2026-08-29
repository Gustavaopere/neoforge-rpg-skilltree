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

## Execution status

All six tasks have been implemented and reviewed. TDD RED/GREEN cycles were recorded during development, including the final page-enrichment review REDs. Final pre-merge verification on merge-ref `83d52269e90b1f4a0be07cc5188b32b3c94f14d7` (`main@50c263d3da91c57ff15b047afaf1244f4991b89a` + `head@aba397cbab14f08cdd244aebd0d87a102620ef2b`) is GREEN across Compendium Ecology #85, Flora #103, Entities #169, Discovery #246 and RPG Skill Tree #1129, including NeoForge build, JAR verification and dedicated-server smoke.

### Task 1: Typed relation targets and evidence validation

- [x] Added typed relation targets for ENTRY/ITEM/ITEM_TAG/BLOCK/BLOCK_TAG without creating `CompendiumEntryKind.ITEM`.
- [x] Preserved legacy entry-target constructor/schema compatibility.
- [x] Enforced evidence for `CURATED_EDITORIAL + EXACT`.
- [x] Updated deterministic relation ordering and schema validation.

### Task 2: Pure loot summary model and structural parser

- [x] Added immutable loot summary contracts and structural parser.
- [x] Fixed/range count and simple chance are represented only when resolvable.
- [x] Player-kill/Looting/unsupported context remains conditional rather than fabricated.
- [x] Added `DROPS` item relations and page facts for resolved item/count/chance.

### Task 3: Food, temptation, breeding, taming and ecology providers

- [x] Food, temptation and breeding remain separate semantics.
- [x] Breeding supports typed item/tag targets.
- [x] Taming separates species capability from contextual instance state.
- [x] Optional ecology adapter contribution is fail-soft.

### Task 4: NeoForge entity ecology inspection without global instance cache

- [x] Runtime inspector consumes existing entities only.
- [x] No entity spawning, reflection, arbitrary NBT or client-only imports.
- [x] No static entity/world cache.

### Task 5: Atomic loot resource reload snapshot

- [x] Reads singular 1.21.1 `loot_table/entities` resources during server reload.
- [x] Stages/validates before atomic publication.
- [x] Invalid staging leaves the previous published snapshot untouched.
- [x] No loot rolling via `getRandomItems`/`fill` and no per-tick rebuild.

### Task 6: Focal CI, review, integration and closure

- [x] `Compendium Ecology CI` added.
- [x] Temporary TDD/PR marker files removed before merge.
- [x] Diff reviewed against the Stage 10.07 plan/spec; no scope creep into habitat/worldgen, external API wiring or global save/network orchestration.
- [x] Final merge-ref reconciled with current `main` and all focused/full gates passed.
- [ ] Merge PR and verify push workflows on `main`.
- [ ] Close `07-loot-dieta-reproducao-ecologia.md` in a separate documentation PR after functional post-merge verification.
