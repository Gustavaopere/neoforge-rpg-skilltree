# Volcanoes -> RPG Skill Tree consolidation

Status: **DESTINATION CONSOLIDATION MERGED / SOURCE CLEANUP NOT AUTHORIZED**

## Canonical inputs

- Destination repository: `Gustavaopere/neoforge-rpg-skilltree`
- Destination base used by the migration: `main@eb073733fbde62190860eb2f739acae9a797c8dc`
- Source repository: `Gustavaopere/Volcanoes`
- Source canonical import: `main@eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Consolidation PR: `Gustavaopere/neoforge-rpg-skilltree#308`
- Consolidation PR final head: `b24c1248e0a697cbf3ab77a7a222dcd5aee6f673`
- Consolidation merge: `f613dac5a15b26c7a92e07a9d9cb537c2412ddf2`
- Minecraft: 1.21.1
- NeoForge: 21.1.248
- Java: 21
- Final distribution contract: exactly one NeoForge mod/JAR, `rpgskilltree`.

## Non-negotiable compatibility rules

1. `rpgskilltree` is the only `@Mod` entry point and the only distributed JAR.
2. Volcanoes is a native subsystem of RPG Skill Tree, never a second required mod.
3. Existing persistent/resource identifiers under `volcanoes:*` are retained where changing them can invalidate worlds, registries, datapacks, SavedData, network contracts, or integrations.
4. Optional hosts remain optional and classloading-safe when absent.
5. Existing Volcanoes double-consumption/double-pollution protections, MineColonies protection, no-retrogen rules, pressure/atmosphere fail-closed behavior, deterministic worldgen, performance budgets, provenance, and release checks survive consolidation.
6. Destructive cleanup of the source repository is a separate administrative action and must not be inferred from successful destination consolidation. It requires explicit user authorization before deleting tracked source content or otherwise decommissioning the repository.

## Implementation sequence

- [x] Import the exact canonical Volcanoes source, tests, assets/data, mixin config, acceptance support scripts, plans/docs, integration templates, and legal/provenance evidence into collision-safe locations in this repository.
- [x] Remove Volcanoes as an independent `@Mod` entrypoint and bootstrap the subsystem from `RpgSkillTreeMod`.
- [x] Merge Gradle repositories and compile/test-only optional dependencies without introducing required optional-mod dependencies.
- [x] Register the Volcanoes mixin configuration from the single RPG Skill Tree mod metadata, with optional RNS target gating fail-closed when its host class is absent.
- [x] Preserve `volcanoes:*` persistent/resource namespaces and verify that no second `@Mod("volcanoes")` descriptor/entrypoint is required by the consolidated artifact.
- [x] Add native RPG-facing provider/service bridges for Volcanoes capabilities without duplicating the simulation.
- [x] Import the canonical Volcanoes plans/docs and release/provenance evidence under `plans/volcanoes/` and `docs/volcanoes/`.
- [x] Port/adapt CI and acceptance gates to the single-JAR repository and remove the temporary one-shot import workflow.
- [x] Execute the PR-head validation matrix. The final PR head `b24c1248...` completed all 21 discovered workflows successfully, including RPG Skill Tree CI, SonarQube and the Volcanoes consolidation/release/optional-host gates.
- [x] Merge the consolidation PR and verify destination `main` contains the exact merge `f613dac5...`.
- [x] Verify subsequent `main` advancement did not discard the consolidation: PR #332 produced `b32a4c85946807c38339c640614b44670c78643f` with `f613dac5...` as a direct parent.
- [ ] **SOURCE REPOSITORY CLEANUP — BLOCKED PENDING EXPLICIT USER AUTHORIZATION:** do not delete files, empty the tree, archive the repository or replace it with a redirect merely because destination consolidation succeeded.

## Post-merge CI note

The push workflows for the exact consolidation merge `f613dac5...` were superseded shortly afterward by a newer `main` push. Three long Volcanoes runs (`Create Sable Acceptance`, `Worldgen Compatibility Matrix`, and `Full Pack Compatibility Acceptance`) were cancelled by that supersession/concurrency behavior, not by test failure. No failed workflow was recorded for `f613dac5...`; the same consolidated code remains ancestry of the newer `main`.

The authoritative functional evidence for PR #308 itself is the exact PR head `b24c1248...`, where all 21 discovered workflows completed with `success` before merge.

## Completion evidence

### Destination phase — COMPLETE

The destination phase is complete because:

- PR #308 is merged;
- merge commit is `f613dac5a15b26c7a92e07a9d9cb537c2412ddf2`;
- the consolidation commit is retained in current `main` ancestry;
- the distribution contract is one `rpgskilltree` NeoForge mod/JAR;
- Volcanoes runtime, tests, resources, plans, CI, provenance and integration templates are present in the unified repository;
- the exact PR head passed the complete discovered workflow set before merge.

### Source-repository decommission phase — NOT STARTED

`Gustavaopere/Volcanoes@eaddc3232dfc600780769f4a5e7e45ff1e50181c` remains intact. No destructive source cleanup is authorized by this plan alone. If the user explicitly orders decommissioning later, that work must be performed as its own reviewed operation and the resulting source `main` state must then be verified separately.
