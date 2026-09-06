# Third-Party Notices and Provenance

Volcanoes is a public NeoForge project that interoperates with and has been informed by third-party Minecraft mods, libraries and tools. This file records release-facing compliance/provenance policy and the notices that must remain available with Volcanoes.

The complete direct-project inventory is [`docs/provenance/third-party-inventory.json`](docs/provenance/third-party-inventory.json). The Stage 07 retroactive audit record is [`docs/provenance/THIRD_PARTY_AUDIT.md`](docs/provenance/THIRD_PARTY_AUDIT.md). `SOURCES.md` is the public source/reference index.

Listing an external project does **not** mean its source or assets are bundled in Volcanoes. An installed binary, dependency declaration, decompiled/inspected artifact or public repository link is not by itself permission to copy or redistribute source/assets.

## Status vocabulary

- `REFERENCE_ONLY` — behavior/architecture may be studied; no source/assets are declared copied.
- `RUNTIME_DEPENDENCY` — an external runtime/compatibility artifact used by tests or supported environments; not embedded in the Volcanoes JAR.
- `BUILD_DEPENDENCY` — external build/test/CI tooling or API dependency; not embedded in the Volcanoes JAR.
- `DERIVED_CODE` — source was copied/adapted and requires exact source revision/artifact, upstream path, local path, license/permission evidence and notices.
- `DERIVED_ASSET` — asset was copied/adapted and requires exact asset provenance and rights evidence.
- `REVIEW_REQUIRED` — available evidence is insufficient for a release-facing classification; the provenance validator treats this state as a release blocker.

The machine-readable inventory is authoritative for the current classification. The canonical Stage 07 Task 05 state contains no `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship; this state remains release-acceptable only while the dedicated provenance workflow is green on the exact release candidate head.

## TFC Volcanoes notice and audit baseline

TFC Volcanoes by Verph is retained as a `REFERENCE_ONLY` implementation/behavioral baseline.

- Public repository: https://github.com/Verph/TFC-Volcanoes
- Upstream license evidence: BSD 2-Clause; full notice retained in [`docs/upstream/NOTICE.md`](docs/upstream/NOTICE.md).
- Copyright: Copyright (c) 2026, Verph.
- Exact implementation-reference artifact: `TFCVolcanoes-1.21.1-2.2.1.jar`.
- CurseForge project/file: `962578` / `8710292`.
- SHA-256: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`.

The public repository did not expose a complete current implementation source tree matching that published binary when the Foundation inspection was performed. Volcanoes therefore uses the exact published artifact + SHA-256 as the immutable implementation-reference revision and does not invent a corresponding source commit. The dedicated Stage 07 audit downloads that same artifact, verifies its digest, decompiles it with CFR `0.152`, and compares candidate/release history against it.

The BSD 2-Clause notice retained for TFC Volcanoes is:

Copyright (c) 2026, Verph

Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:

1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

No TFC Volcanoes source unit or asset is currently declared as derived material in the machine inventory. If a future release introduces actual TFC-derived code/assets, it must create the corresponding `DERIVED_CODE`/`DERIVED_ASSET` record with exact local↔upstream mapping and preserve the applicable BSD notice before the provenance gate can pass.

## Reference-only projects with explicit rights boundary

### ThinAir: ReLived

ThinAir: ReLived is classified `REFERENCE_ONLY`. The audited NeoForge 1.21.1 source project is `kgbcupcake/ThinAir-ReLived`; its project/repository metadata records an MIT license. Volcanoes declares no copied ThinAir source/assets and redistributes none of them.

### Sable: Water Pressure

Sable: Water Pressure is classified `REFERENCE_ONLY`. The audited published baseline is `waterpressure-1.0.0.jar`, CurseForge project `1621903`, whose project metadata is All Rights Reserved. That status is not used as permission to derive code/assets: Volcanoes' pressure implementation is independent and the audit declares no copied Sable Water Pressure source/assets.

## External dependency and compatibility artifacts

Direct Gradle/settings plugins, property-controlled build inputs and exact compatibility-test JARs are inventoried in `docs/provenance/third-party-inventory.json`, including NeoForge/NeoGradle, Parchment, Foojay Toolchains Resolver Convention, MineColonies, Cold Sweat, Create: Rock & Stone, Sable/Sable Companion, Create/Ponder, Curios, JUnit, the canonical worldgen/full-pack test stack, Destroy/Petrolpark/JEI, Rhino/KubeJS, and MineColonies companions.

These artifacts remain external dependencies or CI fixtures. Volcanoes does not embed them as nested JARs. Their own licenses continue to govern those separate projects and distributions.

## Build and CI tooling

The inventory also records direct canonical tooling used to produce/verify Volcanoes: Foojay Toolchains Resolver Convention `0.5.0`, Gradle `8.14`, `actions/checkout@v4`, `actions/setup-java@v5`, `gradle/actions/setup-gradle@v4`, `actions/upload-artifact@v4`, and CFR `0.152` for the TFC baseline reconstruction. These tools are not bundled into the Volcanoes release JAR.

## Stage 07 retroactive audit policy

The Stage 07 provenance gate combines:

- machine-readable classification of every direct project/token used by `build.gradle`, versioned `settings.gradle` plugins, property-controlled build inputs, canonical acceptance workflows and implementation references;
- current source/resource signature checks for TFC Volcanoes/TerraFirmaCraft/TFC Registry identifiers and upstream copyright markers;
- full-history scanning of release-material blobs reachable by the release/candidate history;
- normalized Java similarity comparison against the pinned TFC Volcanoes 2.2.1 decompiled baseline using long token windows;
- exact historical resource-hash comparison against every eligible non-class resource in the pinned TFC JAR, with no minimum-size exemption;
- release JAR inspection proving Volcanoes `LICENSE` and this `THIRD_PARTY_NOTICES.md` are present and that no nested third-party JAR is bundled.

Automated similarity analysis cannot prove the absence of every possible conceptual influence, so it complements the retained human `PORT`/`ADAPT`/`DROP` inspection and the project history. It is intentionally fail-closed for high-signal/source/resource matches and for incomplete derived-material records.

## Release policy

A release candidate is rejected when the machine inventory contains `REVIEW_REQUIRED`, when derived material lacks exact provenance/rights/notices, when a direct external dependency/tool/settings-plugin token is not inventoried, when exact build inputs drift from their configured values, when the retroactive audit detects unresolved high-signal carryover, or when the built JAR lacks required legal payload.

The project-level BSD-2-Clause license covers Volcanoes-owned material; it does not override third-party obligations.
