# Repository Routing and Authority Contract

Status: **canonical repository-local routing contract for RPG Skill Tree work.**

## Authority matrix

| Concern | Source of truth | Notes |
| --- | --- | --- |
| Shared mod-production engineering, contracts, scaffolding, validators, reusable CI/test tooling | `Gustavaopere/minecraft-mod-factory` | Canonical Mod Engineering / Integration Control Plane. |
| Shared art direction, models, textures, UV, rigs, animations, VFX and asset tooling | Minecraft Mod Factory — Repo Textura / Visual & Asset Pipeline | Preserve provider-native/source formats such as `.bbmodel`. |
| RPG Skill Tree runtime | `Gustavaopere/neoforge-rpg-skilltree` | Registries, networking, persistence, gameplay logic, RPG tests and release runtime. |
| Runtime/gameplay code for another mod | That mod's repository | Factory may coordinate/generate changes but is not the runtime authority. |
| RPG/modpack provider presence and exact physical version | Latest RPG physical modlist/JAR evidence | Documentation cannot override physical evidence. |
| Exact third-party API behavior | Exact target source/JAR + matching official docs | Do not infer from newer Minecraft/loader/provider versions. |

## Local physical-evidence exception

`PROJECT-INSTRUCTIONS/engineering/catalog/physical-modlist/**` remains in this repository because it records the physical RPG/modpack environment.

The bounded maintenance path also remains local:

- `PROJECT-INSTRUCTIONS/engineering/tooling/import-physical-modlist.py`;
- `PROJECT-INSTRUCTIONS/engineering/tests/test_i2_modlist_catalog.py`;
- `PROJECT-INSTRUCTIONS/engineering/tests/fixtures/modlist-i2-sample.txt`;
- `.github/workflows/mod-engineering-i2-modlist-catalog.yml`.

This exception preserves local physical evidence; it does not restore shared-control-plane authority to the RPG repository.

## Routing rules

1. New generic mod-production capabilities belong in Minecraft Mod Factory, not this repository.
2. RPG-specific runtime changes belong here.
3. Another mod's runtime belongs in that mod's repository.
4. Do not create a runtime dependency on Minecraft Mod Factory by default; shared runtime code requires an explicit versioned library/API boundary.
5. Provider adapters must be isolated and backed by exact-version evidence.
6. Cross-pipeline asset conversion must be explicit, source-preserving and loss-audited.
7. Before writes, inspect current GitHub state and concurrent work; never overwrite another branch's work.
8. The live repository tree wins over historical conceptual paths.
9. Historical shared engineering material may be consulted through Git history for migration/provenance, but must not be extended as a parallel control plane.

## Current resolved binding

- Shared control plane: `Gustavaopere/minecraft-mod-factory`.
- RPG runtime repository: `Gustavaopere/neoforge-rpg-skilltree`.
- RPG default branch: `main`.
- Target: Minecraft `1.21.1`, NeoForge exact version from current physical evidence/build (reconciled baseline `21.1.248`), Java `21`.
- Shared canonical plans and execution status: Minecraft Mod Factory.
- RPG runtime operating contract: `PROJECT-INSTRUCTIONS/engineering/AGENT-WORKFLOW.md`.
- RPG physical evidence: `PROJECT-INSTRUCTIONS/engineering/catalog/physical-modlist/**`.
