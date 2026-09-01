# Stage 07 — Third-Party Provenance Audit

Machine-readable inventory: [`third-party-inventory.json`](third-party-inventory.json).

Status: **GREEN and canonical on `main` through PR #93, squash merge `bbb273d61984e2c9bb84e8f8a56668ae7e315532`.**

## Scope

The audit covers direct third-party relationships declared or invoked by this repository:

- Gradle/plugin/API dependencies from both project and settings scopes;
- Gradle property-controlled external build inputs;
- exact third-party runtime artifacts downloaded by canonical compatibility workflows;
- implementation/behavioral references;
- direct GitHub Actions and audit/build tools.

Pure transitive dependencies owned by an inventoried direct provider are not mislabeled as source derivation. Unrelated player-modpack contents are outside scope unless this repository directly references/downloads them.

Current machine inventory: **41 direct project/tool records** — 25 `RUNTIME_DEPENDENCY`, 14 `BUILD_DEPENDENCY`, and 3 `REFERENCE_ONLY` relationships. NeoForge legitimately carries both runtime and build relationships, so relationship counts are not a project-count partition. No canonical entry is classified `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED`; the dedicated validator independently verifies that this state matches repository/build reality.

## TFC Volcanoes immutable baseline

TFC Volcanoes is the highest-risk implementation-reference source because Foundation retained detailed decompiled implementation material.

The immutable baseline is:

- artifact: `TFCVolcanoes-1.21.1-2.2.1.jar`;
- CurseForge project/file: `962578` / `8710292`;
- SHA-256: `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`;
- decompiler: CFR `0.152`;
- upstream rights evidence: BSD 2-Clause notice retained in `docs/upstream/NOTICE.md`.

The public repository did not expose a complete source tree matching this published JAR during Foundation inspection, so no source commit is invented. The binary + digest is the exact implementation-reference revision used for automated comparison.

## Automated audit methods

`scripts/validate_provenance.py` and `.github/workflows/third-party-provenance-audit.yml` implement a fail-closed gate:

1. Validate inventory schema, unique project IDs, relationship vocabulary and required derivation fields.
2. Extract every versioned plugin declared in `build.gradle` and `settings.gradle`, direct Gradle dependency strings, canonical acceptance acquisition tokens, workflow `uses:` actions and canonical Gradle versions; every token must map to an inventory record.
3. Cross-check the exact NeoForge and Parchment versions configured in `gradle.properties` against the `neoforge` and `parchment` inventory records.
4. Reject any `REVIEW_REQUIRED` relationship.
5. Require exact upstream revision/artifact, upstream/local paths, permission/license evidence and required notices for every actual `DERIVED_CODE`/`DERIVED_ASSET` entry.
6. Scan current source/resources for TFC Volcanoes package names, TerraFirmaCraft/TFC Registry identifiers and Verph copyright signatures.
7. Reconstruct the pinned TFC 2.2.1 binary baseline and verify its exact SHA-256.
8. Restrict historical refs to `origin/main`, the exact PR candidate merge ref and release tags, excluding abandoned/experimental branches from release-history claims.
9. Enumerate historical release-material blobs and scan historical Java for the same high-signal identifiers.
10. Normalize Java and compare against decompiled TFC source using 64-token shingles; repeated long-window matches fail closed for explicit derivation review.
11. Compare every historical resource blob by exact size + SHA-256 against every eligible non-class resource in the pinned TFC JAR. There is no minimum file-size exemption.
12. Build the release JAR and require root `LICENSE` + `THIRD_PARTY_NOTICES.md`; reject nested third-party JARs.

This does not claim that automated matching can prove the absence of every conceptual influence. It is combined with the retained Foundation class-by-class `PORT` / `ADAPT` / `DROP` inventory and project history; ambiguous positive findings block release until explicitly classified.

## RED evidence

PR #93 introduced the validator/workflow before reconciling legal/status documents.

`Third-Party Provenance Audit` run **33353994195**, job **99372556031**, failed at `Verify validator syntax` with `PROVENANCE_AUDIT_SUMMARY errors=10 warnings=0 checks=6`.

Before failing, it confirmed:

- inventory schema and **39 project records** valid at that historical head;
- all **42 direct Gradle/acceptance/CI external tokens** inventoried;
- `build.gradle` already packaged `LICENSE` and `THIRD_PARTY_NOTICES.md`;
- current source/resource signature scan covered **375 text files** without reporting a TFC/TFC Registry/upstream-copyright match;
- all 10 errors were expected closeout-state failures: missing links in `SOURCES.md` / `THIRD_PARTY_NOTICES.md`, old pre-audit status language, and the unrenamed Task 05 plan.

The RED run intentionally stopped before reconstructing the TFC baseline or performing the full historical source/resource comparison.

## First GREEN candidate and rejected merge proof

PR #93 head `223e2729b19f31494c33e1fd2e46653afc48d6ab` ran `Third-Party Provenance Audit` **33354323560**, job **99373475674**, to completion with `errors=0 warnings=0 checks=11`. It established the historical baseline: 677 release-material blobs, 631 Java blobs, 46 resource blobs and 95,059 64-token TFC shingles, with no high-signal Java carryover or exact resource copy and a legal-payload-clean release JAR.

A subsequent manual reconciliation correctly rejected that candidate as merge proof because `gradle.properties` used NeoForge **21.1.248** while the inventory still stated **21.1.219**, and Parchment mappings **2024.11.17** were not represented as a direct build input. Those defects were corrected and exact `gradle.properties` cross-checks were added.

Head `51fa792b461ef15a5603df79d73a99824f0764eb` then achieved all nine canonical workflows GREEN, including provenance run `33356488364`. Before merge, however, PR review exposed two additional release-gate gaps:

- `settings.gradle` declares `org.gradle.toolchains.foojay-resolver-convention` `0.5.0`, but the dependency-token scanner covered only project-scope Gradle declarations;
- exact resource matching excluded upstream resources smaller than 128 bytes, allowing a copied compact JSON/text asset to escape the derivation gate.

The merge was therefore blocked despite 9/9 GREEN. This is intentional evidence that review findings outrank stale CI success when the gate itself is incomplete.

## Hardened review-fix evidence

The canonical gate adds **Foojay Toolchains Resolver Convention 0.5.0** as project record 41 and scans versioned plugins in both `build.gradle` and `settings.gradle`. It also removes the 128-byte resource threshold: every eligible non-class upstream TFC resource is hash-indexed regardless of size.

On head `3804fde1f9088238609d2e2f75ff5b534cff70bc`, `Third-Party Provenance Audit` **33357209194**, job **99381539195**, completed GREEN after those two code fixes. The log recorded:

- inventory schema: **41 project records**;
- direct dependency/tool coverage: **43 Gradle/settings/acceptance/CI tokens**;
- exact build inputs: NeoForge **21.1.248**, Parchment **Minecraft 1.21.1 / mappings 2024.11.17**;
- current signature scan: **375 text files**;
- exact TFC Volcanoes 2.2.1 SHA-256 verified;
- retroactive releasable-history audit: **677 unique release-material blobs**;
- TFC comparison index: **95,059 64-token shingles**;
- historical Java: **631 unique blobs**, with no high-signal TFC carryover and no repeated long-window similarity finding;
- historical resources: **46 unique blobs**, with no exact TFC asset/resource copy even after small resources were included;
- `PROVENANCE_AUDIT_SUMMARY errors=0 warnings=0 checks=12`;
- release JAR build and legal payload: GREEN;
- evidence artifact ID **9745523095**, ZIP SHA-256 `8b65f71bd63ea2e1358d74a137559675513c30e6af28e8c89861a9d53bf079af`.

## Final exact-head canonical acceptance

PR #93 final implementation head `aeb50c9edbf6d33c840e050213662db5f8d52c09` passed the complete canonical workflow set:

- Volcanoes CI `33357465741` — GREEN;
- Cold Sweat Heat Acceptance `33357465707` — GREEN;
- Performance Hardening Acceptance `33357465712` — GREEN;
- MineColonies Claim Acceptance `33357465789` — GREEN;
- Create Sable Acceptance `33357465855` — GREEN;
- RNS Hydrothermal Acceptance `33357465723` — GREEN;
- Full Pack Compatibility Acceptance `33357465928` — GREEN;
- Third-Party Provenance Audit `33357465806` — GREEN;
- Worldgen Compatibility Matrix `33357465774` — GREEN after the isolated WG-06 retry.

The first WG-06 attempt failed before Minecraft execution while downloading its compatibility stack: `curl (56) Recv failure: Connection reset by peer`. The isolated retry job **99386683182** ran against the same exact PR head and completed `success`. The latest matrix attempt therefore records WG-00 through WG-07 all successful; no source change was used to convert the external acquisition failure into a pass.

All three review threads were resolved before merge: stale NeoForge/Parchment provenance, missing `settings.gradle` plugin coverage, and the small-resource audit exemption.

PR #93 was squash-merged as **`bbb273d61984e2c9bb84e8f8a56668ae7e315532`** on 2026-08-31. Fresh branch verification immediately after merge confirmed `main` points to that exact SHA.

## Reference-only rights boundaries

- **ThinAir: ReLived:** behavioral reference only; MIT project/repository metadata recorded; no source/assets declared copied or redistributed.
- **Sable: Water Pressure:** behavioral reference only; audited published baseline `waterpressure-1.0.0.jar`, CurseForge project `1621903`, All Rights Reserved; ARR is not treated as a derivation grant and the Volcanoes pressure implementation remains independent.
- **TFC Volcanoes:** reference-only at the canonical inventory state; exact BSD notice and exact binary baseline retained. Any future positive source/resource match must be converted to an explicit `DERIVED_CODE`/`DERIVED_ASSET` record with exact local↔upstream provenance before release.

## Canonical release conclusion

Task 05's provenance gate is **complete and canonical** through PR #93 / merge `bbb273d61984e2c9bb84e8f8a56668ae7e315532`. The machine inventory contains no `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship; exact build-input consistency, dependency/token coverage, current-source signatures, historical Java/resource comparison and release-JAR legal payload are all enforced by CI.

This closes the provenance prerequisite required by Stage 07 Task 04. Task 04 itself remains unopened and is the next executable/final hardening task.
