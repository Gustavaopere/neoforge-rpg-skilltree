# Historical Shared-Control-Plane STATUS — Frozen

Status: **historical provenance only; not the current shared Engineering Control Plane status.**

The canonical current execution status for reusable mod-production engineering and the visual/asset pipeline lives in [`Gustavaopere/minecraft-mod-factory`](https://github.com/Gustavaopere/minecraft-mod-factory).

This file preserves the minimum provenance needed to interpret the period when `Gustavaopere/neoforge-rpg-skilltree` also hosted shared Engineering Control Plane work.

## Frozen snapshot

- Snapshot date: `2026-09-09`.
- Former shared-control-plane main at the snapshot frontier: `852d6bf3780f82ea604754ac52a0d1838af02795`.
- I1 Foundation: PR `#494`, merged at `1607579cb8bbc7fcd43a456a69e4270f86e4af4f`.
- I2 Physical Modlist Catalog: PR `#496`, merged at `a614683225a079cf836afd8f974042806d96e1e9`.
- I3 Mod Scaffolder: PR `#500`, merged at `ee4fcb5f22f01a6fe52d8bcd42ff8168666b12a8`.
- I4 Engineering Validators: PR `#501`, merged at `fec3b39ed3c5ce9dca5f5c3bf86afc97cb599d88`.
- I5 Test Harness: PR `#503`, merged at `4822468e0f431bbad0fec16c9e3dde9d7883bb13`.
- I6 Repo Textura structural handoff contract: PR `#506`, merged at `852d6bf3780f82ea604754ac52a0d1838af02795`; the old snapshot explicitly did not claim a real handoff while its Repo Textura remote was unresolved.
- Physical snapshot recorded at that frontier: `595` top-level mods, `404` nested JAR entries, `999` total entries, `758` provider identities, NeoForge `21.1.248`, source SHA-256 `7c0a23d6013101383d196526e4b6ba6940fb54a0fed10eaed5956ab015cfcc00`.

The complete pre-cleanup STATUS text remains recoverable from Git history; at cleanup base `8ed920fcab6486b59f8fd8b5422eea79c0f5946e` its blob SHA was `94fdd12f91319c73f31afbba1a21e48d4b36d882`.

## Migration disposition

Reusable I1/I3–I6 contracts, schemas, examples, templates, scaffolding, validators, test harness, asset-handoff tooling and dedicated shared CI were migrated/reconciled and revalidated in Minecraft Mod Factory before their RPG working-tree duplicates were retired.

I2 is intentionally different: the physical-modlist corpus is evidence bound to the RPG/modpack environment and remains here together with its bounded importer, regression test, fixture and workflow.

## Resume rule

- For shared Engineering / Repo Textura work: read the current Factory `STATUS.md`, canonical plans, current `main`, PRs and physical evidence before acting.
- For RPG runtime work: use `AGENT-WORKFLOW.md`, `TESTING.md`, current RPG code/build/tests and the latest physical modlist.
- Do not use this frozen file to infer current branches, PRs, implementation frontier or blockers.
