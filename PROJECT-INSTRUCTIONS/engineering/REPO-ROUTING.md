# Repository Routing and Authority Contract

Status: **canonical routing contract for mod-production work.**

## Control plane

`Gustavaopere/neoforge-rpg-skilltree` is the canonical integration/control-plane repository. `PROJECT-INSTRUCTIONS/` contains reusable operating knowledge; `PROJECT-INSTRUCTIONS/engineering/` is the canonical home for engineering contracts and control-plane documentation.

## Authority matrix

| Concern | Source of truth | Notes |
| --- | --- | --- |
| Shared engineering instructions/contracts/tooling | RPG integration repository | Reusable control-plane material. |
| Art direction, model sources, textures, UV, rigs, animations, VFX | Repo Textura | Preserve provider-native/source formats such as `.bbmodel`. |
| Runtime/gameplay code for a specific mod | That mod's repository | Includes registries, networking, persistence, logic and release JAR. |
| RPG Skill Tree runtime | RPG integration repository | This repo has dual role: RPG runtime + shared control plane. |
| Provider/mod presence and exact physical version | Latest physical modlist/JAR | Documentation cannot override physical evidence. |
| Exact third-party API behavior | Exact target source/JAR + matching official docs | Do not infer from newer MC/loader versions. |

## Routing rules

1. Do not move artistic source authority from Repo Textura into runtime repos.
2. Do not move another mod's full runtime implementation into the integration repository merely because shared tooling lives here.
3. Do not create a runtime dependency on the integration repository by default.
4. Shared runtime code requires a separate architectural decision and versioned library/API boundary.
5. Provider adapters must be isolated and backed by exact-version evidence.
6. Cross-pipeline asset conversion must be explicit, source-preserving and loss-audited.
7. Before writes, inspect current GitHub state and concurrent work; never force-push over another chat's branch.
8. Paths in plans are conceptual until verified against the live tree.

## Current resolved binding

- Integration repository: `Gustavaopere/neoforge-rpg-skilltree`.
- Default branch: `main`.
- Target: Minecraft `1.21.1`, NeoForge `21.1.x` (physical baseline `21.1.248`), Java `21`.
- Canonical plans: `PROJECT-INSTRUCTIONS/engineering/plans/`.
- Canonical execution status: `PROJECT-INSTRUCTIONS/engineering/STATUS.md`.
