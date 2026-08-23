# AGENTS.md — RPG Skill Tree

This file is the canonical working contract for AI agents and contributors operating in this repository.

## Target platform

- Minecraft: **1.21.1**
- NeoForge: **21.1.x** (current baseline `21.1.248`)
- Java: **21**
- NeoGradle UserDev: **7.1.26**
- Mappings: Parchment for Minecraft 1.21.1 (`2024.11.17` baseline)

Do not apply APIs, registry IDs, resource paths or examples from 1.21.2+, 1.21.4+, Forge legacy or Fabric without proving that they are valid for NeoForge/Minecraft 1.21.1.

When version-sensitive behavior is involved, prefer this evidence order:

1. code/build files in this repository;
2. NeoForge/Minecraft 1.21.1 sources and official documentation;
3. Context7 scoped specifically to 1.21.1, with results checked for accidental newer-version content;
4. Minecraft development skills as workflow/checklist guidance, not as authority when their examples target another version.

When available, relevant skills include `minecraft-modding`, `minecraft-mod-dev`, `minecraft-testing`, `minecraft-ci-release`, plus appropriate Superpowers workflows.

## Documents to read before changing architecture

- [`docs/MASTER_PLAN.md`](docs/MASTER_PLAN.md)
- [`docs/TESTING.md`](docs/TESTING.md)
- [`docs/decisions/README.md`](docs/decisions/README.md)
- [`docs/audits/2026-08-23-consolidated/README.md`](docs/audits/2026-08-23-consolidated/README.md)

`docs/audits/` is historical evidence. Historical audit recommendations are not automatically current requirements; use the consolidated audit and current code.

## Permanent invariants

1. The **server is authoritative** for progression, costs, requirements, mastery, unlocks, respec and effects.
2. The client sends intents/IDs, never authoritative balances, ranks or calculated outcomes.
3. Player-persistent progression belongs in **Data Attachments**.
4. `ItemStack`-owned persistent state belongs in **Data Components**.
5. Capabilities are for behavior/interoperability contracts; do not duplicate attachment persistence in a capability.
6. `SavedData` is level/dimension state and must have an explicit growth/cleanup policy.
7. The core should remain Java-pure where practical; provider/Minecraft classes must not leak into core progression rules.
8. Client-only classes must never be loadable on a dedicated server.
9. Extensible IDs should be namespaced `ResourceLocation`s. Do not add new addon-facing contracts based on free-form unnamespaced strings or closed enums unless the domain is intentionally closed.
10. Never silently discard unknown persisted progression.
11. Every persisted schema change requires an explicit migration and regression fixtures.
12. Disk format version, semantic/economic schema version and network protocol version must not be conflated.
13. Gameplay-critical definitions must have one effective server-side source of truth and a revisioned client projection.
14. Reload must be transactional: publish the whole validated rule snapshot or keep the previous known-good snapshot.
15. Successful reload must reconcile online players, effects and client rules view.
16. Missing required attribute/provider bindings are an error or explicit unavailability state, never a silent no-op purchase.
17. Node effects must flow through canonical stat bindings once Phase 3 is implemented.
18. Mastery XP and purchase currencies are separate concepts/ledgers.
19. Emergent classes are derived from investment and must not become hard-lock choices that prevent valid hybrids.
20. Specialization/unlock provenance must be explicit before generic reconciliation removes or preserves it.
21. Provider integrations grant progression from confirmed semantic outcomes, not merely attempted actions when a confirmation hook exists.
22. High-frequency awards must not rebuild all attribute modifiers or send full state on every event.
23. Shared dedupe/anti-farm/rate-limit rules belong in the central runtime pipeline, not duplicated ad hoc across integrations.
24. Optional provider absence must be safe both for classloading **and gameplay**. A missing provider must not leave a purchasable useless node.
25. Reflection or optional mixins must fail visibly/diagnostically when their contract is expected but incompatible.
26. Do not add large new content batches while foundation blockers in Phases 0–4 remain open.
27. Avoid cosmetic refactors of stable core code while foundation work is in progress.

## Known verified blockers at the consolidated baseline

These were rechecked directly on `main` during the 2026-08-23 consolidation:

- boss tag lives at `data/rpgskilltree/tags/entity_types/bosses.json`; for MC 1.21 this must be reconciled to the singular registry/tag path;
- Cataclysm IDs in that tag are mandatory despite Cataclysm being optional;
- `node_effects/main.json` contains 34 vanilla attribute references using post-1.21.1 IDs such as `minecraft:max_health` instead of the valid 1.21.1 binding;
- `AttributeNodeEffectRuntime` silently skips missing attribute holders/instances;
- `reconcileInvalidNodes` still tries to remove missing definitions through normal `respecNode`;
- client gameplay catalogs use classpath `getResourceAsStream` while the server reloads datapacks;
- XP/mastery mutations route through `PlayerProgressionRuntime.set`, which always refreshes all attributes and syncs owner state;
- `ProcGuard`/mastery source metadata are not yet a central runtime dedupe pipeline;
- CI uses `git diff --check` but not `git diff --exit-code`;
- Gradle Wrapper, JUnit-integrated runtime tests and GameTests are still absent;
- optional provider version ranges are not declared in `neoforge.mods.toml`.

Do not treat the historical PR #5 failure as a current blocker; that foundation was later merged successfully.

## Change workflow

For bug fixes and foundation changes:

1. Inspect the current code and reproduce/characterize the problem.
2. Confirm all version-sensitive NeoForge/Minecraft APIs against 1.21.1.
3. Add a regression test that fails for the intended reason before changing behavior when practical.
4. Make the smallest architectural change that satisfies the canonical plan.
5. Run the relevant generators/validators.
6. Run core/JUnit/GameTests as applicable.
7. Run NeoForge build.
8. Run dedicated-server smoke for common/server/runtime changes.
9. Run client smoke/manual UI verification for client changes.
10. Run provider-present/provider-absent tests for integration changes.
11. Require a clean working tree after generators (`git diff --exit-code`) once the CI gate is implemented.
12. Update `docs/MASTER_PLAN.md`, ADRs or migration docs if the change resolves or changes an architectural decision.

A compile-only success is not sufficient evidence for gameplay correctness.

## Generated data

The current repository uses Python generators as an active pipeline and also has a `runData` configuration. Until an ADR changes this:

- do not create two independent authoritative generators for the same resources;
- generator output must be deterministic;
- CI must eventually require no diff after regeneration;
- when adding native NeoForge datagen, define whether it replaces or complements a Python generator before merging.

## Integration policy

Existing providers include Iron's Spellbooks, Ars Nouveau, Epic Fight, Goety, Malum, Eidolon and Identity/morph; Create and other technology providers are incomplete/planned.

For every integration:

- keep provider types out of core;
- isolate loading behind `compat`/provider boundaries;
- declare supported version range once the provider matrix is formalized;
- normalize external events into the future `SemanticAction` pipeline;
- preserve provider-native resources where appropriate (mana, Soul, stamina, etc.);
- test absence, presence, cancellation/failure, duplicate events, logout/reload and multiplayer attribution;
- do not expose a gateway until there is a real, tested route to earn its progression.

## Before expanding content

Large-scale skill/subtree expansion should wait until the project has, at minimum:

- reproducible wrapper/JUnit/GameTest baseline;
- 1.21.1 resource/attribute correctness fixes;
- safe save reconciliation/migration model;
- atomic `ProgressionRulesSnapshot` and server→client rules view;
- canonical stat runtime;
- coalesced mutation/sync and central dedupe.

See `docs/MASTER_PLAN.md` for the exact phased order.