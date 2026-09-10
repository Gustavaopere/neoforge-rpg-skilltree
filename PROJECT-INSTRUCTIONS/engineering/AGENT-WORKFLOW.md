# Agent Workflow — RPG Skill Tree

Status: **canonical working contract for AI agents and contributors operating in this repository.**

The root [`AGENTS.md`](../../AGENTS.md) is intentionally retained only as the discovery/bootstrap entrypoint. This file governs work on the RPG runtime.

## Repository authority

`Gustavaopere/neoforge-rpg-skilltree` is the source of truth for the RPG mod runtime.

Reusable infrastructure for mod creation, shared engineering, skills, scaffolding, validators, CI templates, catalogs, Blockbench tooling and the visual/asset pipeline is authoritative in [`Gustavaopere/minecraft-mod-factory`](https://github.com/Gustavaopere/minecraft-mod-factory).

Do not start new generic Factory capabilities in this repository. Changes here must be either:

- specific to the RPG runtime; or
- necessary to preserve, reconcile or migrate historical shared work.

## Target platform

- Minecraft: **1.21.1**
- NeoForge: **21.1.x**; verify the exact current target against the physical modlist/build before version-sensitive work
- Java: **21**
- NeoGradle UserDev: verify against the current build before relying on a version
- Mappings: verify against the current build before relying on a version

Do not apply APIs, registry IDs, resource paths or examples from newer Minecraft/NeoForge versions, Forge legacy or Fabric without proving that they are valid for the exact RPG target.

## Evidence order

For runtime behavior, API shape and compatibility, prefer:

1. code, build files and tests in this repository;
2. the exact physical JAR/modlist snapshot used by the RPG environment;
3. source/JAR/documentation for the exact dependency/provider version;
4. canonical contracts and plans in the Minecraft Mod Factory;
5. shared Factory skills as workflow/checklist guidance;
6. historical material and examples from other versions only as reference.

Do not invent APIs, versions, providers, repository paths, branches, PRs or validation results.

## Shared skill router

Specialized shared Minecraft workflows no longer live under `PROJECT-INSTRUCTIONS/skills/` in this repository. Use the Factory:

- [`skills/ROUTER.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/ROUTER.md)
- [`skills/VERSION-AUTHORITY.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/VERSION-AUTHORITY.md)
- [`skills/USER-GUIDED-WORKFLOW.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/USER-GUIDED-WORKFLOW.md)

When a task requires a manual action by the user, give one atomic, verifiable manual step at a time and wait for the required evidence before issuing the next manual action.

## Shared mod-production work

If the requested change is generic mod-production infrastructure rather than RPG runtime work, switch authority to `Gustavaopere/minecraft-mod-factory` before implementing it. Verify that repository's current `main`, open PRs, STATUS, canonical plans and latest physical modlist first.

Historical engineering/art files in this RPG repository may remain as provenance. They do not authorize a parallel control plane and must not be extended as the default location for shared capabilities.

Preserve provider-native artistic source formats such as `.bbmodel`; cross-pipeline conversion must never happen silently.

## Execution reporting

During execution, report the current execution plan followed by an updated checklist. Use `✅` for complete, `🔄` for current, `⬜` for pending and `⛔` for blocked. Additional prose is reserved for a blocker, risk, decision, question or required manual action.

Do not mark a gate complete until the relevant test, validator, build, CI job, runtime smoke or other objective proof has actually run.

## Documents to read before changing RPG architecture

For RPG runtime architecture, read as applicable:

- [`../../docs/MASTER_PLAN.md`](../../docs/MASTER_PLAN.md)
- [`TESTING.md`](TESTING.md)
- [`../../docs/decisions/README.md`](../../docs/decisions/README.md)
- [`../../docs/audits/2026-08-23-consolidated/README.md`](../../docs/audits/2026-08-23-consolidated/README.md)

`docs/audits/` is historical evidence. Historical recommendations are not automatically current requirements; revalidate against current code and tests.

For common engineering/art infrastructure, use the canonical plans and STATUS in the Minecraft Mod Factory instead of the historical shared-control-plane material retained here.

## Permanent RPG runtime invariants

1. The **server is authoritative** for progression, costs, requirements, mastery, unlocks, respec and effects.
2. The client sends intents/IDs, never authoritative balances, ranks or calculated outcomes.
3. Player-persistent progression belongs in **Data Attachments** when that remains the correct exact-version mechanism.
4. `ItemStack`-owned persistent state belongs in **Data Components** when appropriate for the exact target.
5. Capabilities are behavior/interoperability contracts; do not duplicate attachment persistence in a capability.
6. `SavedData` is level/dimension state and must have an explicit growth/cleanup policy.
7. Keep the core Java-pure where practical; provider/Minecraft classes must not leak into core progression rules without justification.
8. Client-only classes must never be loadable on a dedicated server.
9. Extensible IDs should be namespaced `ResourceLocation`s. Avoid new addon-facing contracts based on free-form unnamespaced strings or closed enums unless the domain is intentionally closed.
10. Never silently discard unknown persisted progression.
11. Every persisted schema change requires an explicit migration strategy and regression fixtures.
12. Disk format version, semantic/economic schema version and network protocol version must not be conflated.
13. Gameplay-critical definitions must have one effective server-side source of truth and a revisioned client projection when the client needs them.
14. Reload must be transactional: publish the whole validated rule snapshot or keep the previous known-good snapshot.
15. Successful reload must reconcile online players, effects and client rules view when applicable.
16. Missing required attribute/provider bindings are an error or explicit unavailability state, never a silent no-op purchase.
17. Node effects must flow through canonical stat bindings once that runtime path is active.
18. Mastery XP and purchase currencies are separate concepts/ledgers.
19. Emergent classes are derived from investment and must not become hard-lock choices that prevent valid hybrids unless design authority explicitly changes this.
20. Specialization/unlock provenance must be explicit before generic reconciliation removes or preserves it.
21. Provider integrations grant progression from confirmed semantic outcomes, not merely attempted actions when a confirmation hook exists.
22. High-frequency awards must not rebuild all attribute modifiers or send full state on every event without measured justification.
23. Shared dedupe/anti-farm/rate-limit rules belong in the central RPG runtime pipeline, not duplicated ad hoc across integrations.
24. Optional provider absence must be safe for classloading and gameplay. A missing provider must not leave a purchasable useless node.
25. Reflection or optional mixins must fail visibly/diagnostically when their expected contract is incompatible.
26. Do not add large new content batches while unresolved foundation blockers would invalidate that content.
27. Avoid cosmetic refactors of stable core code during blocker-focused work.

## Historical blocker snapshot

The consolidated 2026-08-23 audit recorded issues including resource-path correctness, optional Cataclysm references, attribute IDs, reconciliation behavior, client/server catalog loading, high-frequency sync cost, CI gaps and optional-provider metadata.

Treat that list as **historical evidence only**. Before acting on any item, confirm whether current `main` still exhibits it. Do not reopen already-fixed work because an old audit mentioned it.

## Change workflow

For RPG bug fixes and runtime changes:

1. Inspect current code and reproduce/characterize the problem.
2. Confirm version-sensitive Minecraft/NeoForge/provider APIs against the exact current target.
3. Add a regression test that fails for the intended reason before changing behavior when practical.
4. Make the smallest architectural change that satisfies the current RPG requirement.
5. Run relevant generators and validators.
6. Run pure/JUnit/GameTests as applicable.
7. Run the NeoForge build.
8. Run dedicated-server smoke for common/server/runtime changes.
9. Run client smoke/manual UI verification for client presentation changes.
10. Run provider-present/provider-absent tests for optional integrations.
11. Require deterministic generated output and a clean diff when generators are versioned.
12. Update `docs/MASTER_PLAN.md`, ADRs or migration docs if the change resolves or changes an RPG architectural decision.

A compile-only success is not sufficient evidence for gameplay correctness.

## Generated data

When this repository uses multiple generation mechanisms:

- do not create two independent authoritative generators for the same resources;
- generator output must be deterministic;
- CI should detect stale generated output when generated files are versioned;
- define whether native NeoForge datagen replaces or complements an existing generator before introducing overlapping authority.

## Integration policy

For every RPG provider integration:

- keep provider types out of core where practical;
- isolate loading behind `compat`/provider boundaries;
- prove the exact physical provider version before depending on an API;
- preserve provider-native resources where appropriate;
- normalize external events into the canonical RPG semantic/runtime pipeline rather than duplicating progression logic;
- test absence, presence, cancellation/failure, duplicate events, logout/reload and multiplayer attribution as applicable;
- do not expose a progression gateway until there is a real, tested route to earn it.

## Before expanding RPG content

Large-scale skill/subtree expansion should wait until the runtime foundations required by that content are objectively green. Use [`../../docs/MASTER_PLAN.md`](../../docs/MASTER_PLAN.md) and current tests/CI to determine the actual frontier rather than relying on historical phase assumptions.
