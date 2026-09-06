# Source provenance

This file is the human entry point for third-party provenance in Volcanoes. The complete machine-readable inventory is [`docs/provenance/third-party-inventory.json`](docs/provenance/third-party-inventory.json), and the Stage 07 retroactive audit record is [`docs/provenance/THIRD_PARTY_AUDIT.md`](docs/provenance/THIRD_PARTY_AUDIT.md).

A source link, installed artifact, compatibility target or inspected binary is **not** a license grant. The current inventory classifies each direct relationship as `REFERENCE_ONLY`, `RUNTIME_DEPENDENCY`, `BUILD_DEPENDENCY`, `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED`. Any future `DERIVED_CODE` / `DERIVED_ASSET` entry must name the exact upstream revision/artifact, upstream path(s), local path(s), permission/license evidence and required notice before release.

## Current derivation posture

The Stage 07 inventory contains no declared `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship. Third-party mods/libraries are either external dependencies/compatibility hosts or reference-only sources; they are not bundled inside the Volcanoes JAR. This statement is enforced by `scripts/validate_provenance.py` and `.github/workflows/third-party-provenance-audit.yml`, including a full-history source/resource comparison against the pinned TFC Volcanoes baseline.

## Primary implementation references

### TFC Volcanoes

- Project: **TFC Volcanoes** by Verph.
- Public repository: https://github.com/Verph/TFC-Volcanoes
- Relationship: `REFERENCE_ONLY`.
- License evidence: BSD 2-Clause; retained in [`docs/upstream/NOTICE.md`](docs/upstream/NOTICE.md).
- Exact implementation-reference artifact: `TFCVolcanoes-1.21.1-2.2.1.jar`.
- CurseForge project/file: `962578` / `8710292`.
- SHA-256: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`.
- Foundation inspection workflow: `32898537754`; artifact `9582448664`, ZIP digest `sha256:9631742e4ab527c322c0c2e9ab3a69cd7b11e8b5f31ab4d1d40067e65679bfbe`.

The public repository exposed the BSD license but did not expose a complete source tree matching the published 2.2.1 JAR at Foundation time. Volcanoes therefore does **not** invent a source commit equivalent to that binary. The exact published JAR + SHA-256 is the implementation-reference revision. The Stage 07 provenance workflow downloads that same artifact, verifies its SHA-256, decompiles it with CFR `0.152`, and compares the current and historical Volcanoes release-material blobs against it.

The exhaustive inspected-unit classification remains under [`docs/upstream/`](docs/upstream/): `TFC_VOLCANOES.md`, `TFC_VOLCANOES_CLASSIFICATION.md`, `TFC_VOLCANOES_ASH_PYROCLASTICS.md` and `TFC_VOLCANOES_GEOTHERMAL.md`. `PORT` / `ADAPT` / `DROP` are historical engineering classifications, not claims that production code was copied.

### ThinAir: ReLived

- Repository: https://github.com/kgbcupcake/ThinAir-ReLived
- Relationship: `REFERENCE_ONLY`.
- NeoForge 1.21.1 behavioral reference.
- License verified 2026-08-31: MIT (repository/Modrinth project metadata).
- No ThinAir source/assets are declared copied, adapted or redistributed by Volcanoes.

### Sable: Water Pressure

- Project: https://www.curseforge.com/minecraft/mc-mods/sable-water-pressure
- Relationship: `REFERENCE_ONLY`.
- Exact published baseline referenced by the audit: `waterpressure-1.0.0.jar`, CurseForge project `1621903`.
- License verified 2026-08-31: All Rights Reserved.
- Because ARR is not a derivation grant, this project is used only as a behavioral reference. Volcanoes' pressure implementation remains independent; no source/assets are copied or redistributed.

## Direct build/API dependencies

The exact direct Gradle/plugin declarations and property-controlled build inputs are authoritative in `build.gradle`, `settings.gradle` and `gradle.properties` and are mirrored by the machine inventory. They currently include NeoForge **`21.1.248`**, NeoGradle UserDev `7.1.26`, Parchment mappings **Minecraft 1.21.1 / `2024.11.17`**, Foojay Toolchains Resolver Convention **`0.5.0`**, MineColonies file `8621898`, Cold Sweat file `8302211`, Create: Rock & Stone file `8729955`, Sable file `8673825`, Sable Companion common `1.6.0`, Create `6.0.10-280`, Ponder `1.0.82+mc1.21.1`, Curios API `9.5.1+1.21.1`, and JUnit BOM `5.11.4`.

The provenance validator scans versioned plugins from both `build.gradle` and `settings.gradle`. The workflow also cross-checks NeoForge and Parchment inventory versions directly against `gradle.properties`, preventing the human inventory from silently drifting away from the build actually executed by CI.

These external artifacts are compile/test/runtime inputs only; they are not embedded as nested JARs in Volcanoes. Optional host adapters remain version-gated/fail-closed and do not transfer source ownership.

## Canonical acceptance-stack artifacts

The full-pack and worldgen acceptance scripts intentionally download exact third-party runtime artifacts to prove compatibility. The machine inventory covers every direct acquisition token from `.github/scripts/install_full_pack_acceptance.sh`, including:

- Lithostitched `81DDKTGJ`;
- Terralith `IY93YaEe`;
- Tectonic `vNrkxC3z`;
- CorgiLib `nqrTa84r`;
- GeckoLib `tPkJmim6`;
- Oh The Trees You'll Grow `1.21.1-5.3.2-NeoForge`;
- TerraBlender `6e8GCrLb`;
- Oh The Biomes We've Gone `aPEcdSHb`;
- Biolith `EAjbdreT`;
- Create `6.0.10-280` and Ponder `1.0.82+mc1.21.1`;
- Sable `U678xqle` (`2.0.5+mc1.21.1`) and Create Aeronautics `Vzp221Un` (`1.3.1+mc1.21.1`);
- Destroy `v0.4.1`, SHA-256 `ba20bd69fd69e94671060665f08249f782e5526e1fd4223995c681a23361d351`;
- Petrolpark `3A7Utwm4` (`1.21.1-1.5.0`) and JEI `TMNM8nwH` (`19.39.0.371`);
- Cold Sweat file `8302211`;
- Rhino `2101.2.7-build.81`, KubeJS `2101.7.2-build.368`, and Create: RNS file `8729955`;
- MineColonies file `8621898`, Structurize file `8610535`, Multi-Piston `1.2.51-1.21.1-snapshot`, BlockUI file `6367809`, and Domum Ornamentum file `7231908`.

These JARs are CI/runtime fixtures, not redistributed Volcanoes contents.

## CI/build tooling

The machine inventory also covers direct build/CI tooling: Foojay Toolchains Resolver Convention `0.5.0`, Gradle `8.14`, `actions/checkout@v4`, `actions/setup-java@v5`, `gradle/actions/setup-gradle@v4`, `actions/upload-artifact@v4`, and CFR `0.152` for reconstruction of the pinned TFC reference baseline.

## Maintenance rule

`docs/provenance/third-party-inventory.json` is the authoritative complete direct-project inventory. `SOURCES.md` and `THIRD_PARTY_NOTICES.md` must agree with it. Any new direct Gradle dependency or versioned settings plugin, property-controlled build input, acceptance artifact, CI action/tool or behavioral reference must be inventoried in the same PR. The provenance validator fails when an external direct token is unclassified, when exact NeoForge/Parchment build versions drift from `gradle.properties`, or when any `REVIEW_REQUIRED`/incompletely documented derived material would enter a release.
