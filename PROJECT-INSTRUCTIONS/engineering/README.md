# Engineering — RPG Runtime Operations

Status: **canonical repository-local engineering entrypoint for the RPG Skill Tree runtime and its physical environment evidence.**

## Current authority

- RPG Skill Tree runtime: `Gustavaopere/neoforge-rpg-skilltree`.
- Shared mod-production engineering and integration control plane: [`Gustavaopere/minecraft-mod-factory`](https://github.com/Gustavaopere/minecraft-mod-factory).
- Shared visual/asset pipeline: the **Repo Textura / Visual & Asset Pipeline** authority inside Minecraft Mod Factory.
- Runtime for any other mod: that mod's own repository.
- Provider/mod presence and exact physical versions for the RPG environment: the latest snapshot under [`catalog/physical-modlist/`](catalog/physical-modlist/).

This repository must not be used as a parallel home for generic scaffolding, schemas, validators, templates, test harnesses, asset-handoff tooling or cross-mod automation.

## Repository-local engineering material

The live material retained here is intentionally RPG-specific:

- [`AGENT-WORKFLOW.md`](AGENT-WORKFLOW.md) — operating contract for work on the RPG runtime;
- [`TESTING.md`](TESTING.md) — RPG test strategy and reproduction gates;
- [`DIAGNOSTICS.md`](DIAGNOSTICS.md) — RPG diagnostics guidance;
- [`REPO-ROUTING.md`](REPO-ROUTING.md) — current repository/authority routing;
- [`STATUS.md`](STATUS.md) — frozen provenance for the former shared-control-plane phase;
- [`catalog/physical-modlist/`](catalog/physical-modlist/) — physical runtime evidence;
- [`tooling/import-physical-modlist.py`](tooling/import-physical-modlist.py), [`tests/test_i2_modlist_catalog.py`](tests/test_i2_modlist_catalog.py) and `tests/fixtures/modlist-i2-sample.txt` — the bounded local I2 maintenance/validation path for that physical evidence.

The I2 path remains here because the physical modlist is evidence bound to this RPG/modpack environment. Its presence does **not** make this repository the shared provider-catalog or engineering authority.

## Historical shared-control-plane material

This repository previously hosted common Engineering Control Plane capabilities I1–I6. Reusable I1/I3–I6 contracts, schemas, examples, templates, scaffolding, validators, test harnesses, asset-handoff tooling and their dedicated CI were migrated/reconciled and revalidated in Minecraft Mod Factory before their working-tree duplicates were retired here.

Git history preserves the original implementations and evidence. The Factory migration matrix and current Factory `STATUS.md` are authoritative for the migrated shared capabilities. Do not recreate removed generic directories here solely to preserve historical paths.

## Work routing

- RPG runtime/gameplay/code/tests → this repository.
- Shared contracts, scaffolding, validators, reusable CI/test infrastructure, provider proofs and cross-mod tooling → Minecraft Mod Factory.
- Shared Blockbench/model/texture/animation/VFX/asset-pipeline work → Repo Textura / Visual & Asset Pipeline in Minecraft Mod Factory.
- Another mod's runtime → that mod's repository.
- Exact third-party API behavior → exact target JAR/source plus matching official documentation; never infer from a different Minecraft/loader/provider version.

Before writes, inspect current GitHub state and concurrent work. Paths described by historical plans are not authority over the live repository tree.

## Target baseline

- Minecraft: `1.21.1`
- NeoForge: verify the exact current value against the physical modlist/build; the reconciled physical baseline is `21.1.248`
- Java: `21`

## Manual-user protocol

When execution requires user action, issue exactly one atomic, verifiable manual step and wait for its result before sending another manual action. Do not delegate work that the agent can execute directly.

## Execution reporting

During execution, report the current execution plan followed by the updated checklist. Use `✅` for complete, `🔄` for current, `⬜` for pending and `⛔` for blocked. Do not mark a gate complete until the relevant test, validator, build, CI job, runtime smoke or other objective proof has actually run.
