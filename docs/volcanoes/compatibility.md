# Compatibility matrix

This document is the release-facing compatibility contract for the Volcanoes subsystem consolidated into `rpgskilltree`. A version combination is supported only when the corresponding evidence below is green for the release candidate being published. Merely compiling an optional adapter, discovering a mod id, or observing a previous release is not a release claim.

## Platform baseline

| Component | Pinned version | Evidence gate |
| --- | --- | --- |
| Minecraft | 1.21.1 | `RPG Skill Tree CI` |
| NeoForge | 21.1.248 | `RPG Skill Tree CI` |
| Java | 21 | all GitHub Actions gates |
| Volcanoes | native `rpgskilltree` subsystem | current development line |

`RPG Skill Tree CI` is the base-host gate. It runs unit tests and the unified NeoForge build with optional integrations absent where applicable. The Volcanoes-specific exact-host workflows below prove provider integrations independently and in the combined stack.

## Automated exact-host gates

| Integration | Exact tested host version | Automated evidence |
| --- | --- | --- |
| Create respiration | Create 6.0.10 (`6.0.10-280` artifact) | `Volcanoes Create Sable Acceptance`; also exercised by RNS and Full Pack acceptance |
| Sable pressure | Sable 2.0.5 | `Volcanoes Create Sable Acceptance`; `Volcanoes Full Pack Compatibility Acceptance` |
| Create Aeronautics coexistence | Aeronautics 1.3.2 | `Volcanoes Create Sable Acceptance`; `Volcanoes Full Pack Compatibility Acceptance`; no synthetic cabin-seal authority is claimed |
| Destroy pollution/acid rain | Destroy 0.4.1 | `Volcanoes Full Pack Compatibility Acceptance` real-host GameTests and dedicated-server smoke |
| Cold Sweat heat | Cold Sweat 2.4.2 | `Volcanoes Cold Sweat Heat Acceptance`; `Volcanoes Full Pack Compatibility Acceptance` |
| RNS hydrothermal prospecting | Create RNS 1.3.1-1.21.1-6 | `Volcanoes RNS Hydrothermal Acceptance`; `Volcanoes Full Pack Compatibility Acceptance` |
| MineColonies protected areas | MineColonies 1.1.1375-1.21.1-snapshot | `Volcanoes MineColonies Claim Acceptance`; `Volcanoes Full Pack Compatibility Acceptance` |

Supporting exact artifacts currently used by those gates:

- Ponder 1.0.82+mc1.21.1 for Create 6.0.10.
- Petrolpark's Library 1.5.0, immutable Modrinth version `3A7Utwm4`, and JEI 19.39.0.371 for Destroy 0.4.1.
- Rhino 2101.2.8-build.91, KubeJS NeoForge 2101.7.2-build.374 and Better Advanced Tooltips 2101.1.0-build.5 for the optional RNS projection fixture.
- MineColonies acceptance dependencies: Structurize 1.0.832-1.21.1-snapshot, MultiPiston 1.2.51-1.21.1-snapshot, BlockUI 1.0.199-1.21.1-snapshot and Domum Ornamentum 1.0.223-1.21.1-snapshot.

Version gates are intentionally exact. A newer upstream release is not automatically considered compatible; it requires its own provider audit and acceptance evidence.

### Optional KubeJS companion contract

The current 573-entry target modpack does not require KubeJS as a top-level dependency for Volcanoes. Create RNS remains free to own and generate its native deposits when KubeJS is absent. The Volcanoes → RNS custom projection bridge activates only when the exact supported optional companion stack is present: Create RNS 1.3.1-1.21.1-6 + KubeJS 2101.7.2-build.374 + Rhino 2101.2.8-build.91 + Better Advanced Tooltips 2101.1.0-build.5. Absence or version mismatch fails closed and does not transfer native RNS worldgen authority to Volcanoes.

## World-generation matrix

`Volcanoes Worldgen Compatibility Matrix` runs deterministic bounded dedicated-server generation twice for each case and compares the persisted Volcanoes site digest.

| Case | Terralith | Tectonic | BWG | Biolith |
| --- | --- | --- | --- | --- |
| WG-00 | — | — | — | — |
| WG-01 | 2.6.2 | — | — | — |
| WG-02 | — | 3.0.26 | — | — |
| WG-03 | — | — | 2.6.0 | — |
| WG-04 | 2.6.2 | 3.0.26 | — | — |
| WG-05 | 2.6.2 | — | 2.6.0 | 3.0.14 |
| WG-06 | — | 3.0.26 | 2.6.0 | — |
| WG-07 | 2.6.2 | 3.0.26 | 2.6.0 | 3.0.14 |

Worldgen support artifacts pinned by the workflow are Lithostitched 1.8.0+beta4-neoforge-21.1, CorgiLib 1.21.1-5.0.0.9-NeoForge, GeckoLib 4.9.2, Oh The Trees You'll Grow 5.3.2 and TerraBlender 4.1.0.8.

## Destroy 0.4.1

Destroy runtime coverage is automated and fail-closed.

The exact supported Destroy asset is the public `NHblock714/Destroy` release `v0.4.1`, file `destroy-1.21.1-0.4.1.jar`, SHA-256 `ba20bd69fd69e94671060665f08249f782e5526e1fd4223995c681a23361d351`. `Volcanoes Full Pack Compatibility Acceptance` validates that hash before Minecraft starts and then executes the real Destroy host adapter against the loaded 0.4.1 API.

Destroy 0.4.1 requires the Petrolpark API generation under `petrolpark.mc.library.*`. The original Maven host declared by the upstream build is no longer DNS-resolvable in GitHub Actions. The acceptance gate therefore pins the immutable public Petrolpark NeoForge 1.21.1 release `3A7Utwm4` at version 1.5.0 and refuses to boot unless `petrolpark/mc/library/mixin/plugin/PetrolparkMixinPlugin.class` exists. This ABI guard is intentional: another binary previously encountered under a similar 1.5.0 filename used the incompatible `com.petrolpark.*` package family and was correctly rejected by the combined runtime.

No regex, fake mod, stub or silently different Destroy/Petrolpark version satisfies this gate.

## Automated full-pack release matrix

`Volcanoes Full Pack Compatibility Acceptance` exercises the combined supported integration stack, not merely each adapter in isolation. Its pinned target combination is:

- Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21;
- Terralith 2.6.2;
- Tectonic 3.0.26;
- Oh The Biomes We've Gone 2.6.0 and its pinned support dependencies;
- Biolith 3.0.14;
- Create 6.0.10;
- Sable 2.0.5;
- Create Aeronautics 1.3.2;
- Destroy 0.4.1 + Petrolpark 1.5.0 (`3A7Utwm4`) + JEI 19.39.0.371;
- Cold Sweat 2.4.2;
- Create RNS 1.3.1-1.21.1-6 + KubeJS NeoForge 2101.7.2-build.374 + Rhino 2101.2.8-build.91 + Better Advanced Tooltips 2101.1.0-build.5;
- MineColonies 1.1.1375-1.21.1-snapshot and its pinned support dependencies.

RNS native deposit worldgen remains enabled in this combined gate. The workflow does not reuse the isolated RNS test's datapack-suppression fixture.

The automated full-pack gate must complete all of the following before a combination is accepted:

- fetch and validate the exact pinned host artifacts;
- require exact runtime versions for the integration hosts;
- execute the Volcanoes dedicated-server GameTest suite with the whole stack loaded;
- start a dedicated server and create/load a real world;
- force bounded world activity and flush the save;
- perform a clean shutdown;
- restart the same world, save again and shut down cleanly;
- prove the persisted Volcanoes site digest is stable across the reload.

Historical Stage 07 evidence proves the matrix infrastructure but does not bless later release heads. Every current release candidate requires fresh exact-head evidence for the current pins above.

## Functional parity and provenance

`Volcanoes Functional Parity Audit` restores the frozen standalone source checkpoint `eaddc3232dfc600780769f4a5e7e45ff1e50181c` from the canonical local Git bundle under `docs/archive/volcanoes/standalone-git-history/`, verifies the bundle checksum, and compares the functional source/resource/test surface against the consolidated repository. Missing source functionality or unclassified content drift fails the gate. Reviewed consolidation adaptations are recorded in `docs/volcanoes/provenance/functional-parity-exceptions.json`.

The audit does not depend on the retired standalone GitHub repository remaining online.

## Required functional GameTest coverage

The release gate must retain dedicated-server GameTests for:

- Volcano site persistence across `VolcanoSavedData` round-trip;
- eruption restart/resume without creating a second eruption;
- breathable-air consumption/refill and breathing-authority coexistence;
- hydrostatic/atmospheric pressure and fail-closed sealed-environment authority;
- real MineColonies protected-area terrain mutation denial;
- real Destroy 0.4.1 host activation;
- optional-adapter absence without core startup failure;
- exact Create and Sable host activation when those verified hosts are present;
- exact optional RNS projection activation only with its supported KubeJS/Rhino/BAT companion stack.

## Release evidence policy

For every release candidate, record successful workflow run IDs (or equivalent immutable CI links) for the exact candidate SHA. Historical green runs from a different SHA are supporting history only and cannot substitute for candidate evidence. `Volcanoes Consolidated Release Readiness` fails closed until all required sibling workflows, including `Volcanoes Functional Parity Audit`, are GREEN on that exact head.

Last current-pack reconciliation: 2026-09-02.
