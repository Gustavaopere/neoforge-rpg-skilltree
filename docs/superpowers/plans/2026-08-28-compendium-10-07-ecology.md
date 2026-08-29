# Stage 10.07 Compendium Ecology Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement safe, source-aware Compendium enrichment for relation targets, loot, food, temptation, breeding, taming and ecology, with atomic reload snapshots and no gameplay side effects.

**Architecture:** Pure Java contracts live under `compendium/`; Minecraft/NeoForge access stays under `runtime/compendium`. Relations use typed non-entry targets instead of an ITEM entry kind. Loot resources are parsed structurally during server data reload into immutable summaries, while contextual entity state stays outside global caches.

**Tech Stack:** Java 21, Minecraft 1.21.1, NeoForge 21.1, GitHub Actions, shell/Python gates.

**Spec:** `docs/superpowers/specs/2026-08-28-compendium-10-07-ecology-design.md`

## Constraints

- No `CompendiumEntryKind.ITEM`.
- No loot rolling, entity spawning, commands/functions, arbitrary reflection or undocumented NBT reads for documentation.
- `CURATED_EDITORIAL + EXACT` requires evidence.
- Food, temptation and breeding remain separate semantics.
- Optional mod enrichments are fail-soft.
- Loot snapshots publish atomically after staging/validation.
- Habitat stays 10.08; external API wiring 10.11; global save/network/cache 10.13.

## Implemented

- [x] Typed relation targets: ENTRY/ITEM/ITEM_TAG/BLOCK/BLOCK_TAG; legacy relation compatibility preserved.
- [x] Schema v1 supports legacy `to` or typed target format, never both.
- [x] Structural loot parser with exact/range/conditional semantics and no gameplay execution.
- [x] `DROPS` relations plus page section with item/count/chance only when resolvable.
- [x] Food/temptation/breeding providers remain distinct.
- [x] Taming capability is distinct from contextual tame/owner/adult state.
- [x] Optional ecology enrichment degrades safely.
- [x] Runtime inspection uses existing entities only and holds no global entity/world cache.
- [x] Reload uses Minecraft 1.21.1 `loot_table/entities`, staging before atomic publication.
- [x] Dedicated `Compendium Ecology CI` and runtime/reload validators.
- [x] Temporary development markers removed.

## TDD review evidence

- Ecology #73 / `33223780280`: RED because `CompendiumLootEnricher` was absent.
- Ecology #79 / `33224214237`: RED because the current entity page had no resolved `loot` section.
- Both gaps were corrected without weakening the tests.

## Integration gate

Final code/spec behavior has already passed full merge-ref testing against `main@50c263d3da91c57ff15b047afaf1244f4991b89a`. The final documentation-only head is re-running the same five required gates before merge.

- [ ] Merge PR with exact final head after all gates complete.
- [ ] Verify push workflows on `main`.
- [ ] Close 10.07 in a separate docs PR only after functional post-merge GREEN.
