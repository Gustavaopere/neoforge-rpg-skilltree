# Stage 07 — Release Readiness

RELEASE_READINESS_STATUS: GREEN

This document maps the canonical `plans/07-hardening/✅-04-release-checklist.md` 1:1 to executable release evidence. Task 04 implementation was accepted on PR #95 final exact head `837d6688cfe870e776984fa25f141db178c44d35`, where all ten canonical workflows completed GREEN, and was squash-merged to `main` as `5187fb63baaf2006003b24767ac8943a3e9334a3`. RL-19 is closed by this post-merge plan/status closeout. For every future release candidate, all canonical workflows must be GREEN on the same exact PR head. The repository release-readiness state is canonical only while the same fail-closed gates continue to pass.

## Canonical workflow set

Every final Task 04 head must pass all ten workflows below on the same exact commit:

1. `Volcanoes CI`
2. `Cold Sweat Heat Acceptance`
3. `Performance Hardening Acceptance`
4. `MineColonies Claim Acceptance`
5. `Create Sable Acceptance`
6. `RNS Hydrothermal Acceptance`
7. `Full Pack Compatibility Acceptance`
8. `Third-Party Provenance Audit`
9. `Worldgen Compatibility Matrix`
10. `Release Readiness`

A stale GREEN from an earlier head does not satisfy this contract. Review findings or source changes after a GREEN run require fresh exact-head acceptance.

## Checklist evidence

### RL-01 — `gradle test` green

`Volcanoes CI` executes `gradle --no-daemon test` on Java 21. The Release Readiness workflow additionally reruns the narrow release-invariant contracts for existing-world safety and the optional integrations whose ownership/deduplication behavior is part of this checklist.

### RL-02 — `gradle build` green

`Volcanoes CI` executes `gradle --no-daemon build` after unit tests and diff sanity. The release is rejected if the NeoForge build does not complete on the exact candidate head.

### RL-03 — dedicated-server smoke green

`Volcanoes CI` starts a real NeoForge dedicated server with `gradle --no-daemon runServer`, waits for the vanilla `Done (...)!` readiness marker, requires the Volcanoes rock-profile datapack to load, and rejects startup failure. Full-pack acceptance independently boots a dedicated server with the canonical host stack.

### RL-04 — no TFC/TFC Registry runtime dependency

`src/main/resources/META-INF/neoforge.mods.toml` declares only NeoForge and Minecraft as mandatory runtime dependencies. `build.gradle` contains no TFC/TFC Registry runtime dependency. TFC Volcanoes remains a provenance/reference baseline only, and the dedicated provenance scan also rejects TFC/TFC Registry source-signature carryover.

### RL-05 — no required optional-mod dependency

Optional compatibility hosts are compile-only/API inputs in `build.gradle`; they are not mandatory entries in `neoforge.mods.toml`. The release validator fails if a compatibility host becomes a mandatory metadata dependency. Individual adapters also fail closed when their exact supported host/API is absent or drifts.

### RL-06 — full-pack startup and world-load test green

`Full Pack Compatibility Acceptance` installs the pinned canonical provider stack, runs exact-host GameTests, then `.github/scripts/run_full_pack_smoke.sh` boots a dedicated server, loads the deterministic volcanic region with `forceload`, saves/stops, and requires `world/data/volcanoes_sites.dat`. The script executes two clean rounds and compares site-data digests; this is a full-pack startup/world-load and deterministic persistence check, not a claim that both rounds reuse one world directory.

### RL-07 — new-world volcano generation inspected

`.github/scripts/run_worldgen_matrix_case.sh` deletes the prior world for every round, creates a fresh fixed-seed world, forces the deterministic Stage 01 owner neighborhood, requires `volcanoes_sites.dat`, computes its digest, repeats from another clean world, and compares the two digests. WG-00 through WG-07 therefore exercise fresh-world generation across the canonical compatibility matrix.

### RL-08 — existing-world no-retrogen safety inspected

`ExistingWorldVolcanoAdminSessionTest.applyingMetadataNeverInvokesTerrainShaping` verifies that explicit existing-world registration writes site metadata only and does not synthesize chamber/lifecycle terrain state. Existing-world administration is preview-gated and one-shot; it is not an automatic terrain-retrogen path.

### RL-09 — MineColonies protected-area tests green

`MineColoniesCompatTest` proves that an exact compatible host establishes the authoritative protected-area service, claimed positions reject mutation, unclaimed positions remain mutable, and version/API mismatch fails closed without mutation authority. `MineColonies Claim Acceptance` runs the exact-host integration gate.

### RL-10 — eruption + atmosphere + Destroy has no double damage/pollution

Destroy receives only the explicitly mapped pollution channels; particulate-only load is not silently folded into smog. `DestroyPollutionApplicationLedger` is regression-tested so a successful `(emissionId, component)` mutation is applied once, while a failed host mutation is not reserved and may retry. The full-pack/Destroy gates exercise the runtime host bridge; Volcanoes does not add a second Destroy-owned pollution damage system.

### RL-11 — pressure + Create/Sable protection consumes resources once

`CreateRespirationDecisionContractTest` proves safe air spends no backtank resource and thin-air oxygen supply debits exactly once on Create's 20-tick cadence. `VolcanoesMod` deliberately runs Atmosphere breathing at `LOWEST` after Create's native listener so Create and the shared protection transaction do not both debit air. `SablePressureCompatContractTest` proves the Sable pressure adapter is exact-version gated and fails closed on API drift rather than inventing a second pressure authority.

### RL-12 — performance budgets verified

`Performance Hardening Acceptance` reruns the Task 02 profiler, 0/1/10/50-site benchmark, scheduler/index/depth-cache/lava contracts and requires `build/performance/volcano-sites.csv`. Runtime defaults remain bounded at 64 ash block mutations/tick, 32 lava-specialization block work/tick and 8 eruption terrain mutations/tick.

### RL-13 — `LICENSE`, `SOURCES.md`, `THIRD_PARTY_NOTICES.md` current

The Stage 07 provenance gate cross-validates the public source/notices documents against the machine inventory. Task 04 additionally removed stale pre-merge candidate wording from `THIRD_PARTY_NOTICES.md`; the release-facing documents describe the canonical Task 05 state.

### RL-14 — built JAR contains Volcanoes `LICENSE` and `THIRD_PARTY_NOTICES.md`

`build.gradle` adds both root files to the JAR. `Third-Party Provenance Audit` builds the release artifact and explicitly verifies the legal payload while rejecting nested third-party JARs. Standard CI independently checks the built JAR contains the NeoForge descriptor and Volcanoes entrypoint.

### RL-15 — Stage 07 third-party provenance audit green

Task 05 is canonical through PR #93 / merge `bbb273d61984e2c9bb84e8f8a56668ae7e315532`. Its fail-closed workflow is part of the ten-workflow exact-head release set, so a future release head cannot rely only on the historical Task 05 run.

### RL-16 — every actual `DERIVED_CODE` / `DERIVED_ASSET` has exact provenance/notices/permission

`scripts/validate_provenance.py` requires derived entries to carry local paths, upstream paths, exact source/revision, permission evidence, license evidence and required notice. Missing any required field blocks the provenance gate. The current inventory has no derived-material entry.

### RL-17 — no unresolved `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown derived status

`REVIEW_REQUIRED` explicitly blocks release; unknown relationship vocabulary also fails validation. A non-derived entry may not override derivation status with an unrecognized state. The current machine inventory contains no `DERIVED_CODE`, `DERIVED_ASSET` or `REVIEW_REQUIRED` relationship, so there is no current permission-review exception to waive.

### RL-18 — TFC Volcanoes derived material, if any, satisfies BSD/root/file provenance rules

The exact TFC Volcanoes 2.2.1 artifact and SHA-256 are immutable provenance inputs. `docs/upstream/NOTICE.md` retains the BSD 2-Clause notice and `SOURCES.md`/`THIRD_PARTY_NOTICES.md` state the exact rule for any future derived material. Historical Java/resources are compared against the pinned baseline; any positive carryover must become an explicit derived record before release. Current state remains `REFERENCE_ONLY` with no declared derived source or asset.

### RL-19 — COMPLETE

The post-merge closeout is now materialized:

- `plans/07-hardening/04-release-checklist.md` was renamed to `plans/07-hardening/✅-04-release-checklist.md`;
- all 19 canonical checklist items are checked;
- Task 04 implementation PR #95, final exact head `837d6688cfe870e776984fa25f141db178c44d35`, all ten exact-head workflow runs and merge `5187fb63baaf2006003b24767ac8943a3e9334a3` are recorded in `plans/STATUS.md`;
- this document is `RELEASE_READINESS_STATUS: GREEN`;
- the closeout PR must itself pass the same ten canonical workflows on its exact head before merge;
- after that merge, `main` must be verified at the resulting closeout SHA.

Task 04 and Stage 07 are considered fully canonical only after the closeout PR itself is merged and `main` is freshly verified.