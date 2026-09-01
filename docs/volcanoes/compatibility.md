# Compatibility matrix

This document is the release-facing compatibility contract for Volcanoes. A version combination is supported only when the corresponding evidence below is green for the release candidate being published. Merely compiling an optional adapter, discovering a mod id, or observing a previous release is not a release claim.

## Platform baseline

| Component | Pinned version | Evidence gate |
| --- | --- | --- |
| Minecraft | 1.21.1 | `Volcanoes CI` |
| NeoForge | 21.1.248 | `Volcanoes CI` |
| Java | 21 | all GitHub Actions gates |
| Volcanoes | 0.1.0-dev | current development line |

`Volcanoes CI` is the base-host gate. It runs unit tests, build, dedicated-server GameTests and server smoke with the optional integration jars absent. The GameTest suite must therefore prove that absence of an optional host disables only that adapter and does not prevent Volcanoes from loading.

## Automated exact-host gates

| Integration | Exact tested host version | Automated evidence |
| --- | --- | --- |
| Create respiration | Create 6.0.10 (`6.0.10-280` artifact) | `Create Sable Acceptance`; also exercised by `RNS Hydrothermal Acceptance` and `Full Pack Compatibility Acceptance` |
| Sable pressure | Sable 2.0.5 | `Create Sable Acceptance`; `Full Pack Compatibility Acceptance` |
| Create Aeronautics coexistence | Aeronautics 1.3.1 | `Create Sable Acceptance`; `Full Pack Compatibility Acceptance`; no synthetic cabin-seal authority is claimed |
| Destroy pollution/acid rain | Destroy 0.4.1 | `Full Pack Compatibility Acceptance` real-host GameTest and dedicated-server smoke |
| Cold Sweat heat | Cold Sweat 2.4.2 | `Cold Sweat Heat Acceptance`; `Full Pack Compatibility Acceptance` |
| RNS hydrothermal prospecting | Create RNS 1.3.1-1.21.1-6 | `RNS Hydrothermal Acceptance`; `Full Pack Compatibility Acceptance` |
| MineColonies protected areas | MineColonies 1.1.1374-1.21.1-snapshot | `MineColonies Claim Acceptance`; `Full Pack Compatibility Acceptance` |

Supporting exact artifacts currently used by those gates:

- Ponder 1.0.82+mc1.21.1 for Create 6.0.10.
- Petrolpark's Library 1.5.0, immutable Modrinth version `3A7Utwm4`, and JEI 19.39.0.371 for Destroy 0.4.1.
- Rhino 2101.2.7-build.81 and KubeJS NeoForge 2101.7.2-build.368 for the RNS acceptance fixture.
- MineColonies acceptance dependencies: Structurize 1.0.832-1.21.1-snapshot, MultiPiston 1.2.51-1.21.1-snapshot, BlockUI 1.0.199-1.21.1-snapshot and Domum Ornamentum 1.0.223-1.21.1-snapshot.

Version gates are intentionally exact. A newer upstream release is not automatically considered compatible; it requires its own provider audit and acceptance evidence.

## World-generation matrix

`Worldgen Compatibility Matrix` runs deterministic bounded dedicated-server generation twice for each case and compares the persisted Volcanoes site digest.

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

The exact supported Destroy asset is the public `NHblock714/Destroy` release `v0.4.1`, file `destroy-1.21.1-0.4.1.jar`, SHA-256 `ba20bd69fd69e94671060665f08249f782e5526e1fd4223995c681a23361d351`. `Full Pack Compatibility Acceptance` validates that hash before Minecraft starts and then executes the real Destroy host adapter against the loaded 0.4.1 API.

Destroy 0.4.1 requires the Petrolpark API generation under `petrolpark.mc.library.*`. The original Maven host declared by the upstream build is no longer DNS-resolvable in GitHub Actions. The acceptance gate therefore pins the immutable public Petrolpark NeoForge 1.21.1 release `3A7Utwm4` at version 1.5.0 and refuses to boot unless `petrolpark/mc/library/mixin/plugin/PetrolparkMixinPlugin.class` exists. This ABI guard is intentional: another binary previously encountered under a similar 1.5.0 filename used the incompatible `com.petrolpark.*` package family and was correctly rejected by the combined runtime.

No regex, fake mod, stub or silently different Destroy/Petrolpark version satisfies this gate.

## Automated full-pack release matrix

`Full Pack Compatibility Acceptance` exercises the combined supported integration stack, not merely each adapter in isolation. Its pinned target combination is:

- Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21;
- Terralith 2.6.2;
- Tectonic 3.0.26;
- Oh The Biomes We've Gone 2.6.0 and its pinned support dependencies;
- Biolith 3.0.14;
- Create 6.0.10;
- Sable 2.0.5;
- Create Aeronautics 1.3.1;
- Destroy 0.4.1 + Petrolpark 1.5.0 (`3A7Utwm4`) + JEI 19.39.0.371;
- Cold Sweat 2.4.2;
- Create RNS 1.3.1-1.21.1-6 + KubeJS NeoForge 2101.7.2-build.368 + Rhino 2101.2.7-build.81;
- MineColonies 1.1.1374-1.21.1-snapshot and its pinned support dependencies.

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

Stage 07 Task 01 proof on head `21a54d16bc9abf9c812d176c4c8340e0afaf2cfe` completed 31/31 required full-pack GameTests and the same-world save/reload persistence smoke in `Full Pack Compatibility Acceptance` run `33337881867`. The corresponding worldgen matrix run `33337881718` passed WG-00 through WG-07.

This Task 01 evidence proves the matrix infrastructure and the pinned combination above. A future release candidate still requires the same gates to be GREEN on that exact candidate SHA; historical Task 01 runs do not automatically bless a later build.

## Required functional GameTest coverage

The release gate must retain dedicated-server GameTests for:

- Volcano site persistence across `VolcanoSavedData` round-trip;
- eruption restart/resume without creating a second eruption;
- breathable-air consumption/refill and breathing-authority coexistence;
- hydrostatic/atmospheric pressure and fail-closed sealed-environment authority;
- real MineColonies protected-area terrain mutation denial;
- real Destroy 0.4.1 host activation;
- optional-adapter absence without core startup failure;
- exact Create and Sable host activation when those verified hosts are present.

## Release evidence policy

For every release candidate, record successful workflow run IDs (or equivalent immutable CI links) for the exact candidate SHA. Historical green runs from a different SHA are supporting history only and cannot substitute for candidate evidence.

Last matrix audit: 2026-08-30.
