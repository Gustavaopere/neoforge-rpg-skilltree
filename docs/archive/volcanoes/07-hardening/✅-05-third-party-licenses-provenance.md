# 07.05 — Third-Party Licenses & Provenance

## Goal

Make every external dependency, compatibility target, behavioral reference, copied/adapted source unit and asset auditable before Volcanoes is publicly released.

This is an engineering compliance gate, not legal advice. Ambiguous provenance fails closed.

## Acceptance checklist

- [x] Maintain a machine-readable direct-project inventory in `docs/provenance/third-party-inventory.json`.
- [x] Maintain a human-readable audit record in `docs/provenance/THIRD_PARTY_AUDIT.md`.
- [x] Inventory every direct Gradle/plugin dependency from `build.gradle` and `settings.gradle`, Gradle property-controlled external build input, canonical acceptance artifact, implementation reference and direct CI/audit tool used by the repository.
- [x] Classify each relationship as `REFERENCE_ONLY`, `RUNTIME_DEPENDENCY`, `BUILD_DEPENDENCY`, `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED`.
- [x] Fail release when `REVIEW_REQUIRED` exists.
- [x] Require exact upstream revision/artifact, upstream/local paths, rights evidence and required notices for every future `DERIVED_CODE` / `DERIVED_ASSET` entry.
- [x] Preserve the exact TFC Volcanoes implementation-reference artifact: `TFCVolcanoes-1.21.1-2.2.1.jar`, CurseForge file `8710292`, SHA-256 `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`.
- [x] Do not invent a TFC source commit matching the binary when the public repository does not expose a matching current source tree.
- [x] Reconstruct the pinned TFC baseline with CFR `0.152` for the retroactive comparison gate.
- [x] Scan current release material for TFC Volcanoes/TerraFirmaCraft/TFC Registry namespaces and upstream copyright signatures.
- [x] Audit historical release-material Java blobs against the pinned TFC baseline using high-signal signatures and long normalized token windows.
- [x] Audit every historical release resource against exact hashes of every eligible non-class resource in the pinned TFC JAR, without a minimum-size exemption.
- [x] Keep `SOURCES.md`, `THIRD_PARTY_NOTICES.md` and the machine inventory mutually consistent.
- [x] Verify every direct external token found in Gradle project/settings declarations, canonical acceptance acquisition scripts and GitHub Actions is represented in the inventory.
- [x] Cross-check the exact NeoForge and Parchment versions in `gradle.properties` against the machine inventory.
- [x] Verify the built Volcanoes JAR contains root `LICENSE` and `THIRD_PARTY_NOTICES.md` and contains no nested third-party JAR.
- [x] Keep direct third-party projects external; no external project in the inventory is marked redistributed in the Volcanoes JAR.
- [x] Require every canonical workflow to be GREEN on the exact final implementation head before merge.
- [x] Resolve review findings before merge even when an earlier CI head is already GREEN.
- [x] Merge Task 05 and record the canonical merge on `main` before unblocking Task 04.

## Closed contract

### Inventory boundary

The machine inventory covers direct project relationships that Volcanoes itself declares in project/settings Gradle configuration, configures as an external build input, downloads, tests against, studies as a behavioral/implementation reference or invokes as build/CI tooling. Pure transitive dependencies resolved by an inventoried provider are not promoted into false source-derivation relationships, and unrelated player-modpack contents are outside this repository audit unless the repository directly references/downloads them.

At the canonical Task 05 state the inventory contains **41 direct project/tool records**. No entry is classified `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED`; that absence is not accepted merely by documentation—it is enforced by the dedicated provenance workflow and retroactive source/resource scans.

### TFC Volcanoes boundary

The Foundation phase already retained the exact TFC Volcanoes binary and a 111-source-unit `PORT` / `ADAPT` / `DROP` inspection. Task 05 converts that historical engineering inventory into a release gate:

- exact binary identity and SHA-256 are revalidated;
- current and historical Volcanoes release material is scanned for direct upstream identifiers/copyright evidence;
- decompiled TFC Java is normalized and indexed into 64-token windows; repeated long-window matches fail closed for explicit derivation review;
- every eligible non-class TFC JAR resource, including compact files below 128 bytes, is indexed by exact size + SHA-256 and compared against historical release resources;
- any positive finding must become an explicit, fully evidenced `DERIVED_CODE`/`DERIVED_ASSET` record before release.

The comparison is intentionally stricter than a present-tree grep, but it does not claim to prove absence of every conceptual influence; the retained human subsystem inspection remains complementary evidence.

### Other references

ThinAir: ReLived is reference-only with MIT license metadata recorded. Sable: Water Pressure is reference-only and its published project metadata is All Rights Reserved, therefore it is not used as a code/asset derivation grant. Normal API and compatibility integrations remain external dependencies, not derivation by association.

### Build-input consistency

The canonical Task 05 build input state uses NeoForge `21.1.248`, Parchment Minecraft baseline `1.21.1` and Parchment mappings `2024.11.17`. `settings.gradle` additionally declares `org.gradle.toolchains.foojay-resolver-convention` `0.5.0`. The provenance validator scans versioned plugins from both Gradle scopes and the workflow cross-checks the NeoForge/Parchment property values against the inventory. Version/plugin drift therefore fails closed.

## TDD / verification evidence

The dedicated `Third-Party Provenance Audit` gate was introduced before legal/status reconciliation.

RED run `33353994195`, job `99372556031`, failed at `Verify validator syntax` with exactly 10 expected closeout errors while already proving the original 39-record/42-token scaffold and current-source scan.

The first complete GREEN run `33354323560`, job `99373475674`, established the 677 release-material / 631 Java / 46 resource / 95,059 TFC-shingle historical baseline and a legal-payload-clean JAR. It was not used for merge after manual review found stale NeoForge provenance and an omitted Parchment build input.

After correcting those, head `51fa792b461ef15a5603df79d73a99824f0764eb` reached all nine canonical workflows GREEN. PR review then found two additional coverage defects: the versioned Foojay settings plugin was outside the token scanner, and resources smaller than 128 bytes were excluded from the upstream hash set. The merge remained blocked despite 9/9 GREEN.

Those defects were corrected. Hardened provenance run `33357209194`, job `99381539195`, on head `3804fde1f9088238609d2e2f75ff5b534cff70bc` completed GREEN and proved:

- **41 direct project/tool records**;
- **43 direct Gradle/settings/acceptance/CI tokens**, including Foojay `0.5.0`;
- exact NeoForge `21.1.248` and Parchment `Minecraft 1.21.1 / mappings 2024.11.17` consistency;
- **375 current text files** scanned;
- exact TFC 2.2.1 SHA-256 verified;
- **677 historical release-material blobs**, **631 Java**, **46 resources**;
- **95,059** 64-token TFC shingles;
- no high-signal Java carryover, repeated long-window TFC similarity or exact TFC resource copy after removing the small-resource exemption;
- `PROVENANCE_AUDIT_SUMMARY errors=0 warnings=0 checks=12`;
- release JAR and legal payload GREEN;
- evidence artifact `9745523095`, ZIP SHA-256 `8b65f71bd63ea2e1358d74a137559675513c30e6af28e8c89861a9d53bf079af`.

## Final exact-head acceptance

PR #93 final implementation head was `aeb50c9edbf6d33c840e050213662db5f8d52c09`. All nine canonical workflows completed GREEN on that exact head:

- Volcanoes CI `33357465741`;
- Cold Sweat Heat Acceptance `33357465707`;
- Performance Hardening Acceptance `33357465712`;
- MineColonies Claim Acceptance `33357465789`;
- Create Sable Acceptance `33357465855`;
- RNS Hydrothermal Acceptance `33357465723`;
- Full Pack Compatibility Acceptance `33357465928`;
- Third-Party Provenance Audit `33357465806`;
- Worldgen Compatibility Matrix `33357465774`.

The first WG-06 attempt in the final Worldgen Matrix stopped during third-party acquisition with `curl (56) Recv failure: Connection reset by peer`, before Minecraft/worldgen execution. The isolated retry on the **same exact PR head**, job `99386683182`, completed `success`; the latest attempt therefore has WG-00 through WG-07 all GREEN. No code change was used to mask the transient acquisition failure.

All three PR review findings were resolved before merge: exact NeoForge/Parchment inventory drift, missing `settings.gradle` plugin coverage, and the small-resource hash exemption.

PR #93 was squash-merged as `bbb273d61984e2c9bb84e8f8a56668ae7e315532`. Fresh post-merge verification confirmed `main` at that exact SHA.

## Release consequence

Task 05 is **complete and canonical**. Its provenance prerequisite for Task 04 (`04-release-checklist.md`) is satisfied on `main` through PR #93 / merge `bbb273d61984e2c9bb84e8f8a56668ae7e315532`.

Task 04 remains unopened and is now the next executable/final Hardening task. This Task 05 closeout does not start Task 04.
