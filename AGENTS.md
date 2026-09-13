# AGENTS.md — RPG Skill Tree

This root file is the repository discovery/bootstrap entrypoint for AI agents and contributors.

## Repository authority

`Gustavaopere/neoforge-rpg-skilltree` is the runtime authority for the RPG mod.

Reusable mod-production infrastructure, shared engineering skills, Blockbench/asset tooling, validators, scaffolding, catalogs and cross-mod automation are authoritative in [`Gustavaopere/minecraft-mod-factory`](https://github.com/Gustavaopere/minecraft-mod-factory). Do not recreate shared Factory capabilities in this repository.

## Before changing the RPG runtime

Read the repository-local working contract in full:

- [`docs/RUNTIME-OPERATIONS.md`](docs/RUNTIME-OPERATIONS.md)

Then use the current RPG code/build/tests and the exact physical modlist/JAR evidence for the target environment as the primary evidence for runtime behavior and provider versions. The canonical shared physical-modlist catalog/tooling is maintained in the Minecraft Mod Factory under `engineering/catalog/physical-modlist/` and `engineering/tooling/`.

For specialized shared Minecraft workflows, use the Factory entrypoints:

- [`skills/ROUTER.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/ROUTER.md)
- [`skills/VERSION-AUTHORITY.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/VERSION-AUTHORITY.md)
- [`skills/USER-GUIDED-WORKFLOW.md`](https://github.com/Gustavaopere/minecraft-mod-factory/blob/main/skills/USER-GUIDED-WORKFLOW.md)

The repository-local project-instruction index is [`PROJECT-INSTRUCTIONS/README.md`](PROJECT-INSTRUCTIONS/README.md). Historical migration material remains recoverable from Git history, but it does not restore shared-infrastructure authority to this repository.
