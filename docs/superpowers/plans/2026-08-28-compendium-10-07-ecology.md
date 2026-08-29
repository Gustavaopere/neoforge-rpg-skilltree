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

All six tasks have been implemented and reviewed. TDD RED/GREEN cycles include the final review REDs for missing current-snapshot enrichment and missing resolved loot facts.

Final pre-merge head: `090a84d35b7b9dd8a2f2ef14b09fe9d3ea245ae9`.
Final merge-ref: `004124f52dd68c053307274b1599c39463cc4146` = `main@50c263d3da91c57ff15b047afaf1244f4991b89a` + final head.

Final GREEN gates:
- Compendium Ecology #95 / `33224525345`;
- Compendium Flora #109 / `33224525332`;
- Compendium Entities #174 / `33224525358`;
- Compendium Discovery #252 / `33224525326`;
- RPG Skill Tree #1135 / `33224525390`, including NeoForge build, JAR verification and dedicated-server smoke.

### Task 1: Typed relation targets and evidence validation
- [x] ENTRY/ITEM/ITEM_TAG/BLOCK/BLOCK_TAG targets; no ITEM entry kind.
- [x] Legacy schema/constructor compatibility and exact-editorial evidence validation.

### Task 2: Structural loot summary
- [x] Fixed/range/conditional summary semantics without loot rolling.
- [x] `DROPS` relations and page facts only when mathematically resolvable.

### Task 3: Food, temptation, breeding, taming and ecology
- [x] Food/tempt/breeding are distinct.
- [x] Taming capability and contextual instance state are distinct.
- [x] Optional ecology contributions fail soft.

### Task 4: Runtime ecology inspection
- [x] Existing entities only; no spawning, reflection, arbitrary NBT, client-only imports or global entity/world cache.

### Task 5: Atomic loot reload
- [x] 1.21.1 `loot_table/entities`, staging before atomic publication, no rolling/per-tick rebuild.

### Task 6: Integration and closure
- [x] Focal Ecology CI added and temporary markers removed.
- [x] Diff reviewed against Stage 10.07 boundaries.
- [x] Current `main` merged virtually by GitHub merge-ref and all focused/full gates passed.
- [ ] Merge PR and verify push workflows on `main`.
- [ ] Close Stage 10.07 in a separate documentation PR after functional post-merge verification.
