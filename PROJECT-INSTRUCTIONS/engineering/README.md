# Engineering Control Plane

Status: **canonical entrypoint for reusable mod-production engineering in `Gustavaopere/neoforge-rpg-skilltree`.**

## Canonical authorities

- Integration/control-plane repository: `Gustavaopere/neoforge-rpg-skilltree`.
- Reusable working root: `PROJECT-INSTRUCTIONS/`, especially `PROJECT-INSTRUCTIONS/engineering/`.
- Artistic source of truth: Repo Textura and its canonical art plan.
- Runtime source of truth for each mod: that mod's own repository.
- Physical runtime/provider presence and versions: latest physical modlist snapshot.

The integration repository coordinates instructions, skills, contracts, catalogs, templates, validators, tooling, test infrastructure, compatibility and release workflows. It must not silently become the runtime repository for every mod.

## Canonical master plans

1. [`plans/PLANO-MESTRE-UNIFICADO-REPO-TEXTURA-BLOCKBENCH-ASSET-MCP-V5.md`](plans/PLANO-MESTRE-UNIFICADO-REPO-TEXTURA-BLOCKBENCH-ASSET-MCP-V5.md)
   - SHA-256: `9d2b93d4ddc2b032d73156e8a594cc2eda352481f3620d365c244fc913a2a154`
   - Scope: Repo Textura, Blockbench, source models, textures, UV, rigging, animation, VFX, provider routing, Asset Toolkit/MCP, visual QA and handoff.
2. [`plans/PLANO-MESTRE-REPO-INTEGRACAO-MOD-ENGINEERING-NEOFORGE-1.21.1-V1.md`](plans/PLANO-MESTRE-REPO-INTEGRACAO-MOD-ENGINEERING-NEOFORGE-1.21.1-V1.md)
   - SHA-256: `fa61d4baf47919592fd293d39d18d0bf5a687c6c01810762dd9c4b06b8e9cf87`
   - Scope: Java 21 / NeoForge 1.21.1 engineering, scaffolding, gameplay/runtime systems, testing, integration, performance and release.

These files are the planning authorities for this production line. Historical plans are reference-only when they conflict with them. GitHub state, physical files/JARs and the latest modlist remain factual authorities for implementation state and provider presence.

## Execution order

Before implementation:

1. read both master plans;
2. read [`STATUS.md`](STATUS.md);
3. verify the latest physical modlist;
4. verify `main`, open PRs and concurrent branches;
5. inspect the current repository tree before choosing paths;
6. avoid repeating work already implemented;
7. create an isolated branch from the exact verified base;
8. implement the smallest plan phase that closes a real gap;
9. run the required tests/validators;
10. resynchronize with current `main` and resolve semantic conflicts;
11. open a PR and validate the exact head;
12. recheck concurrency immediately before merge;
13. update `STATUS.md` whenever a phase/PR changes the execution frontier.

## Work routing

- Art/model/texture/animation/VFX/source-format work → Repo Textura plan and Repo Textura authority.
- Java/NeoForge runtime for a specific mod → that mod repository, coordinated by this control plane.
- Shared contracts, skills, scaffolding, validators, catalogs, provider proofs, CI/test harness and reusable integration tooling → this repository.
- RPG Skill Tree runtime itself → this repository's normal runtime tree, because this repository is also the RPG mod repository.

## Manual-user protocol

When execution needs a user action, issue exactly one atomic, verifiable manual step and wait for the result before sending another manual action. Do not delegate work that the agent can execute directly.

## Response protocol during execution

Execution updates should contain the current execution plan followed by the updated checklist. Use `✅` for complete, `🔄` for current, `⬜` for pending and `⛔` for blocked. Additional prose is reserved for blockers, risks, decisions, questions or a required manual action.
