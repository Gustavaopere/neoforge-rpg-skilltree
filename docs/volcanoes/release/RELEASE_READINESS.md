# Volcanoes — Release Readiness

RELEASE_READINESS_STATUS: GREEN

Volcanoes is now a native subsystem of the unified `rpgskilltree` artifact. The historical standalone Stage 07 Task 04 closeout remains valid as historical evidence, but current release candidates are governed by the consolidated exact-head workflow set below.

## Current consolidated exact-head workflow set

For every current release candidate, all **11 sibling workflows** below must complete GREEN on the same exact PR head before `Volcanoes Consolidated Release Readiness` may pass:

1. `RPG Skill Tree CI`
2. `Volcanoes Consolidation Contract`
3. `Volcanoes Functional Parity Audit`
4. `Volcanoes Cold Sweat Heat Acceptance`
5. `Volcanoes Performance Hardening Acceptance`
6. `Volcanoes MineColonies Claim Acceptance`
7. `Volcanoes Create Sable Acceptance`
8. `Volcanoes RNS Hydrothermal Acceptance`
9. `Volcanoes Full Pack Compatibility Acceptance`
10. `Volcanoes Third-Party Provenance Audit`
11. `Volcanoes Worldgen Compatibility Matrix`

`Volcanoes Consolidated Release Readiness` is the fail-closed aggregator for that sibling set. A stale GREEN from an earlier SHA does not satisfy the contract. Source, workflow, compatibility or documentation changes after a GREEN run require a fresh exact-head validation set.

`Volcanoes Functional Parity Audit` is specifically mandatory after the standalone retirement. It restores the frozen source checkpoint `eaddc3232dfc600780769f4a5e7e45ff1e50181c` from the checksum-verified local bundle at `docs/archive/volcanoes/standalone-git-history/`, then fails on missing functional paths or any drift not documented in `docs/volcanoes/provenance/functional-parity-exceptions.json`. Release validation therefore does not depend on the retired standalone GitHub repository remaining online.

## Historical Stage 07 Task 04 evidence

The original standalone Task 04 implementation was accepted on PR #95 final exact head `837d6688cfe870e776984fa25f141db178c44d35`, where the **ten workflows that formed the standalone release set at that time** completed GREEN, and was squash-merged as `5187fb63baaf2006003b24767ac8943a3e9334a3`. Those run names/counts are historical facts; they do not replace the current consolidated 11-sibling policy above.

## Checklist evidence

### RL-01 — `gradle test` green

`RPG Skill Tree CI` executes the unified Java 21 test/build contract. The consolidated Release Readiness workflow additionally requires every Volcanoes sibling gate listed above to be GREEN on the same exact candidate head.

### RL-02 — `gradle build` green

`RPG Skill Tree CI` executes the NeoForge build after unit tests and validation. The release is rejected if the unified artifact does not build on the exact candidate head.

### RL-03 — dedicated-server smoke green

The Volcanoes acceptance workflows execute real NeoForge dedicated-server GameTests and smoke where their contracts require them. `Volcanoes Full Pack Compatibility Acceptance` independently boots a dedicated server with the canonical combined host stack and validates save/reload persistence.

### RL-04 — no TFC/TFC Registry runtime dependency

`src/main/resources/META-INF/neoforge.mods.toml` declares only the unified artifact's mandatory runtime dependencies; TFC/TFC Registry remain provenance/reference baselines rather than required Volcanoes runtime dependencies. The dedicated provenance scan rejects untracked TFC/TFC Registry source-signature carryover.

### RL-05 — no required optional-mod dependency

Optional compatibility hosts are compile-only/API inputs in `build.gradle`; they are not mandatory metadata dependencies. Individual adapters fail closed when their exact supported host/API is absent or drifts. In particular, the optional Volcanoes → Create RNS projection companion does not make KubeJS a top-level modpack requirement.

### RL-06 — full-pack startup and world-load test green

`Volcanoes Full Pack Compatibility Acceptance` installs the pinned canonical provider stack, runs exact-host GameTests, then `.github/scripts/volcanoes/run_full_pack_smoke.sh` boots a dedicated server, loads the deterministic volcanic region with `forceload`, saves/stops, and requires persisted Volcanoes site data. The script executes two clean rounds and compares site-data digests; this is a full-pack startup/world-load and deterministic persistence check.

### RL-07 — new-world volcano generation inspected

`.github/scripts/volcanoes/run_worldgen_matrix_case.sh` deletes the prior world for every round, creates a fresh fixed-seed world, forces the deterministic Stage 01 owner neighborhood, requires persisted Volcanoes site data, computes its digest, repeats from another clean world, and compares the two digests. WG-00 through WG-07 therefore exercise fresh-world generation across the canonical compatibility matrix.

### RL-08 — existing-world no-retrogen safety inspected

`ExistingWorldVolcanoAdminSessionTest.applyingMetadataNeverInvokesTerrainShaping` verifies that explicit existing-world registration writes site metadata only and does not synthesize chamber/lifecycle terrain state. Existing-world administration is preview-gated and one-shot; it is not an automatic terrain-retrogen path.

### RL-09 — MineColonies protected-area tests green

`MineColoniesCompatTest` proves that an exact compatible host establishes the authoritative protected-area service, claimed positions reject mutation, unclaimed positions remain mutable, and version/API mismatch fails closed without mutation authority. `Volcanoes MineColonies Claim Acceptance` runs the exact current-host integration gate.

### RL-10 — eruption + atmosphere + Destroy has no double damage/pollution

Destroy receives only the explicitly mapped pollution channels; particulate-only load is not silently folded into smog. `DestroyPollutionApplicationLedger` is regression-tested so a successful `(emissionId, component)` mutation is applied once, while a failed host mutation is not reserved and may retry. The full-pack/Destroy gates exercise the runtime host bridge; Volcanoes does not add a second Destroy-owned pollution damage system.

### RL-11 — pressure + Create/Sable protection consumes resources once

`CreateRespirationDecisionContractTest` proves safe air spends no backtank resource and thin-air oxygen supply debits exactly once on Create's cadence. The shared protection transaction prevents duplicate resource consumption. `SablePressureCompatContractTest` proves the Sable pressure adapter is exact-version gated and fails closed on API drift rather than inventing a second pressure authority; Create Aeronautics coexistence is verified against the current 1.3.2 target without claiming a synthetic generic cabin-seal API.

### RL-12 — performance budgets verified

`Volcanoes Performance Hardening Acceptance` reruns the profiler, bounded site-count benchmarks and scheduler/index/depth-cache/lava contracts and requires the performance evidence artifact. Runtime defaults remain bounded by their canonical mutation budgets.

### RL-13 — `LICENSE`, `SOURCES.md`, `THIRD_PARTY_NOTICES.md` current

The provenance gate cross-validates the public source/notices documents against the machine inventory. Release-facing documents must describe the canonical consolidated state rather than a pre-merge standalone candidate.

### RL-14 — built JAR contains Volcanoes legal payload

The unified build packages the required Volcanoes license/notices payload. `Volcanoes Third-Party Provenance Audit` builds the release artifact and explicitly verifies the legal payload while rejecting nested third-party JARs.

### RL-15 — third-party provenance audit green

The provenance workflow is part of the current 11-sibling exact-head release set. Historical provenance runs are supporting evidence only; every future release head must pass the current gate again.

### RL-16 — every actual `DERIVED_CODE` / `DERIVED_ASSET` has exact provenance/notices/permission

`scripts/validate_provenance.py` requires derived entries to carry local paths, upstream paths, exact source/revision, permission evidence, license evidence and required notice. Missing any required field blocks the provenance gate. The current inventory has no declared derived-material entry requiring an exception.

### RL-17 — no unresolved `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown derived status

`REVIEW_REQUIRED` explicitly blocks release; unknown relationship vocabulary also fails validation. A non-derived entry may not override derivation status with an unrecognized state. No permission-review exception is silently waived by Release Readiness.

### RL-18 — TFC Volcanoes derived material, if any, satisfies provenance rules

The exact TFC Volcanoes baseline remains an immutable provenance input. `docs/upstream/NOTICE.md`, `SOURCES.md` and `THIRD_PARTY_NOTICES.md` define the rules for any future derived material. Historical Java/resources are compared against the pinned baseline; any positive carryover must become an explicit derived record before release. Current policy remains fail closed.

### RL-19 — consolidated closeout invariant

Completed Volcanoes planning history is archived under `docs/archive/volcanoes/`; it is not an active implementation queue. `Volcanoes Consolidation Contract` enforces the archived planning surface and verifies the preserved standalone history before parity checks run.

A current Volcanoes change is considered release-ready only after:

- all 11 sibling workflows are GREEN on the exact candidate SHA;
- `Volcanoes Consolidated Release Readiness` is GREEN for that same SHA;
- SonarQube/CodeQL required project gates are GREEN where applicable;
- the PR is merged into `main`;
- the resulting `main` SHA is fetched and confirmed after merge.
