# Volcanoes

Volcanoes is a standalone environmental-simulation mod for **Minecraft 1.21.1 / NeoForge 21.1.248 / Java 21**. It adds a deterministic geology and tectonics layer, persistent volcano systems, eruption hazards, atmosphere and respiration simulation, pressure, geothermal activity, and optional compatibility bridges for the large modpack it is designed to inhabit.

It deliberately **does not replace the Overworld**. Terralith, Tectonic and BWG remain the terrain/worldgen stack; Volcanoes layers its own geological and environmental state on top through bounded, indexed and provider-neutral systems.

## Current implementation on `main`

The canonical implementation state is tracked in [`plans/STATUS.md`](plans/STATUS.md). At the current checkpoint:

- **Foundation — complete.** Reproducible NeoForge build, CI/test harness, domain contracts and upstream inventory.
- **Geology — complete.** Rock profiles, deterministic/virtual strata and resource classification without wholesale terrain replacement.
- **Tectonics — complete.** Deterministic plate field, boundaries, stress and safe seismic gameplay. Tectonics explains geological activity; it never moves Minecraft chunks.
- **Volcanoes — complete.** Volcano-site selection, magma lifecycle, lava, eruptions, ash/pyroclastics and geothermal/hot-spring systems are canonical.
- **Atmosphere — complete.** Shared atmosphere state, respiration, volcanic gases, pollution integration and acid-rain routing.
- **Pressure — complete.** Atmospheric pressure, continuous hydrostatic pressure, enclosed-volume contracts and equipment protection.
- **Integrations — complete.** Worldgen coexistence, Create/Sable, Cold Sweat, Destroy, RNS hydrothermal prospecting coexistence and MineColonies safety are canonical. Volcanoes physically realizes and projects only bounded hydrothermal Cu/Fe/Au bodies; native RNS worldgen remains enabled for Cu/Fe/Au/Sn/Ni/Zn/Ag.
- **Hardening — next stage.** Compatibility matrix, profiling, persistence/world-upgrade verification, provenance audit and final release gates remain the closing stage.

## What Volcanoes does

### Geology

Volcanoes maintains a lightweight geological model compatible with existing terrain generators. Geological state can be derived from actual blocks where useful and represented virtually/deterministically where rewriting terrain would be invasive. This layer provides rock profiles, strata and resource identities consumed by volcanism and hydrothermal systems.

### Tectonics and seismicity

A deterministic invisible plate map models tectonic regions, boundaries and stress. It drives volcano placement, geothermal context and bounded earthquake/tremor gameplay without simulating literal moving chunks or replacing Tectonic terrain generation.

### Volcano lifecycle

Volcanoes models a persistent lifecycle rather than one-off decorative structures:

- deterministic volcano sites and identities;
- magma state/lifecycle;
- lava behavior and bounded world effects;
- eruption scheduling and effect dispatch;
- ash, plumes, bombs and pyroclastic hazards;
- geothermal systems, geysers and hot springs;
- causal metadata used by downstream atmosphere, heat and resource integrations.

### Atmosphere, breathing and pollution

A shared `AtmosphereState` represents environmental air conditions used by respiration, volcanic gases, ash and external pollution adapters. Oxygen supply, filtration and contaminant hazards remain separate concepts. When Destroy is installed, Destroy remains the pollution/acid-rain authority; Volcanoes supplies volcanic emissions without creating a duplicate pollution feedback loop.

### Pressure

Pressure is a shared physical layer:

- atmospheric pressure is derived from dimension/configuration and altitude;
- water adds continuous hydrostatic pressure rather than fixed depth bands;
- enclosed vehicles/rooms may expose pressure overrides only through verified adapters;
- protection can be aggregated from armor, Curios and supported equipment capabilities;
- pressure protection and respiration resource use share transactional accounting so host resources are not consumed twice.

## Optional integrations and authority boundaries

All host integrations are optional, isolated and version/presence-gated. The base mod must start without them.

- **Terralith / Tectonic / BWG:** worldgen coexistence; Volcanoes does not replace their terrain authority.
- **Create:** supported respiration/equipment surfaces where verified.
- **Sable / Sable Companion:** verified vehicle/sub-level atmosphere and pressure context. Sable owns the host pressure facts it actually exposes.
- **Aeronautics:** compatibility boundary only where a real API contract exists; Volcanoes does not invent a generic cabin-seal model.
- **Cold Sweat:** remains body-temperature authority; Volcanoes projects bounded volcanic/geothermal heat.
- **Destroy:** remains pollution and acid-rain authority when installed.
- **Create: RNS:** remains prospecting and native metal-worldgen authority. Volcanoes contributes only physically proven hydrothermal Cu/Fe/Au bodies and matching RNS custom/scannable locations; native RNS worldgen stays enabled for Cu/Fe/Au/Sn/Ni/Zn/Ag, and tin/nickel/zinc/silver are never projected by Volcanoes. Volcanoes-owned custom locations carry a durable owner marker so restart recovery cannot adopt or delete a foreign same-value RNS record.
- **MineColonies:** claims feed the generic fail-closed protected-area service used by destructive terrain effects.
- **Curios:** optional equipment/protection aggregation.

Missing or incompatible optional providers disable only the affected adapter; they must not prevent standalone startup.

## Engineering invariants

- Server-authoritative world/environment state.
- No global per-tick scans across loaded chunks.
- No chunk force-loading merely to finish geological or environmental work.
- Deterministic/indexed spatial state where practical.
- Destructive effects respect protected-area policy and bounded budgets.
- Optional integrations fail closed rather than silently fabricating unsupported host semantics.
- Persistent state is schema-versioned and must survive world upgrades without destructive downgrade behavior.

## Roadmap

The full engineering roadmap is in [`plans/README.md`](plans/README.md). Stage 06 Integrations is closed. The remaining major work is Stage 07 hardening: real-pack compatibility matrix, performance budgets, persistence/world-upgrade coverage, provenance audit and release verification.

## Build

This repository intentionally relies on an installed Gradle distribution in CI rather than committing an executable Gradle wrapper. From a checkout with a compatible Gradle installation:

```bash
gradle test
gradle build
```

The target toolchain is Minecraft 1.21.1, NeoForge 21.1.248 and Java 21. CI additionally exercises the applicable GameTests, JAR verification and dedicated-server smoke paths.

## Plans and project memory

Implementation sessions should read, in order:

1. [`plans/README.md`](plans/README.md)
2. [`plans/STATUS.md`](plans/STATUS.md)
3. [`plans/DECISIONS.md`](plans/DECISIONS.md)
4. the README/task files of the active stage.

## License and third-party provenance

Volcanoes is licensed under the **BSD 2-Clause License**; see [`LICENSE`](LICENSE).

Third-party projects used as dependencies, compatibility targets or implementation/design references are indexed in [`SOURCES.md`](SOURCES.md) and audited in [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md). TFC Volcanoes-specific historical inspection and attribution material remains under [`docs/upstream/`](docs/upstream/).

A source link or a `PORT`/`ADAPT` classification is **not** by itself a license grant for arbitrary copying. Any substantial copied/adapted source or asset must have exact file-level provenance, a compatible license/permission and all required notices before release.
