# Material Chemistry Plan — Pre-Implementation Checklist

This checklist is the mandatory entry gate before runtime implementation begins. It exists because the implementation plan intentionally refuses to invent provider APIs, registry names, package locations, or chemistry.

## Canonical sources already fixed

- Runtime repository: `Gustavaopere/neoforge-rpg-skilltree`.
- Target: Minecraft 1.21.1 / NeoForge / Java 21.
- Destroy installed baseline: `destroy-1.21.1-0.4.1.jar`.
- Volcanoes authority boundaries remain as documented in the consolidated project.
- Design: `docs/specs/2026-09-06-volcanoes-universal-material-chemistry-design.md`.
- Execution plan: `docs/superpowers/plans/2026-09-06-volcanoes-universal-material-chemistry.md`.

## Mandatory checks before Task 1 runtime edits

- [ ] Re-read the current canonical modlist snapshot and record its exact row count/hash/date in the provider audit.
- [ ] Reconcile the current Notion mod audit against that same snapshot before provider classification.
- [ ] Inspect `AGENTS.md`, `docs/ARCHITECTURE.md`, build files, source roots, existing package names, event bootstrap, resource reload conventions, test source sets, and client/server separation patterns.
- [ ] Replace any plan example path/package that does not match the repository's actual conventions before creating runtime files.
- [ ] Inspect the exact Destroy 0.4.1 port source/commit and freeze the real species/registry/API contract in `docs/integrations/2026-09-06-destroy-material-bridge.md`.
- [ ] Inspect Molecule Drawings and the NeoForge Molecules fork license/provenance before any source/data reuse.
- [ ] Identify every mod in the canonical modlist that is a material provider, material consumer only, no-material mod, or blocked audit case.
- [ ] Confirm the existing encyclopedia/compendium extension point before planning any new UI page class.
- [ ] Confirm the installed JEI hook before adding JEI classes.
- [ ] Confirm CI/check tasks and dedicated-server/client smoke commands from the repository rather than inventing commands.

## Hard stop conditions

Implementation pauses and records a blocker instead of guessing when any of the following is true:

- provider material identity cannot be proven;
- real formula/composition cannot be proven;
- provider alias equivalence is only name/tag similarity;
- Destroy identity/API is not stable or not available in the installed port;
- a fictional material lacks a curated arcane representation;
- a third-party renderer/data asset lacks clear reuse permission;
- a proposed client hook would leak client classes into dedicated-server initialization;
- an integration would require redesigning provider authority rather than reading it.

## Completion rule

The implementation phase may claim full coverage only using the separate coverage definitions in the design/plan. A blocked entry can close classification coverage, but it never counts as scientifically supported representation coverage.
