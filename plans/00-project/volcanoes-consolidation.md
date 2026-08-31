# Volcanoes -> RPG Skill Tree consolidation

Status: IN PROGRESS

## Canonical inputs

- Destination repository: `Gustavaopere/neoforge-rpg-skilltree`
- Destination base `main`: `eb073733fbde62190860eb2f739acae9a797c8dc`
- Source repository: `Gustavaopere/Volcanoes`
- Source canonical `main`: `eaddc3232dfc600780769f4a5e7e45ff1e50181c`
- Minecraft: 1.21.1
- NeoForge: 21.1.248
- Java: 21
- Final distribution contract: exactly one NeoForge mod/JAR, `rpgskilltree`.

## Non-negotiable compatibility rules

1. `rpgskilltree` is the only `@Mod` entry point and the only distributed JAR.
2. Volcanoes becomes a native subsystem of RPG Skill Tree, never a second required mod.
3. Existing persistent/resource identifiers under `volcanoes:*` are retained where changing them can invalidate worlds, registries, datapacks, SavedData, network contracts, or integrations.
4. Optional hosts remain optional and classloading-safe when absent.
5. Existing Volcanoes double-consumption/double-pollution protections, MineColonies protection, no-retrogen rules, pressure/atmosphere fail-closed behavior, deterministic worldgen, performance budgets, provenance, and release checks must survive consolidation.
6. The source repository is not emptied until the consolidated destination is merged, CI-green where applicable, and its `main` is freshly verified.

## Implementation sequence

- [ ] Import the exact canonical Volcanoes source, tests, assets/data, mixin config, acceptance support scripts, plans/docs, integration templates, and legal/provenance evidence into collision-safe locations in this repository.
- [ ] Replace `VolcanoesMod` as an independent `@Mod` with an internal `VolcanoesModule` bootstrap invoked by `RpgSkillTreeMod`.
- [ ] Merge Gradle repositories and compile/test-only optional dependencies without introducing required optional-mod dependencies.
- [ ] Register the Volcanoes mixin configuration from the single RPG Skill Tree mod metadata.
- [ ] Preserve `volcanoes:*` persistent/resource namespaces and explicitly test that no second mod descriptor or `@Mod("volcanoes")` remains.
- [ ] Add native RPG-facing provider/service bridges for geology, tectonics, volcanic activity, geothermal deposits, atmosphere, pressure, and hazards without duplicating the simulation.
- [ ] Reconcile the RPG project dossier and source Volcanoes plans/docs so implemented Stage 01-07 behavior is canonical in the unified repository.
- [ ] Port/adapt CI and acceptance gates to the single-JAR repository and remove the temporary import workflow.
- [ ] Run unit tests, build, dedicated server/GameTests, worldgen matrix, optional-host acceptance, full-pack smoke, performance and provenance/release checks as applicable to the consolidated artifact.
- [ ] Open/review/merge the consolidation PR and verify destination `main` at the exact merge SHA.
- [ ] Only after destination verification, create a separate source-repository cleanup PR that removes every tracked file from `Gustavaopere/Volcanoes`, merge it, and verify its `main` tree is empty.

## Completion evidence

This plan is complete only when the destination has a single functional `RPGSkillTree-1.21.1-NeoForge` JAR containing the Volcanoes subsystem, destination CI is green, destination `main` is verified, and source `Volcanoes/main` is verified empty after its own merge.