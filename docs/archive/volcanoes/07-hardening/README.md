# 07 — Hardening

**Branch:** `feat/09-hardening`, created after Integrations merges.

This phase proves Volcanoes can live safely in the real large modpack: compatibility tests, profiling, persistence/world-upgrade safety, provenance/license compliance and the final release gate. No major new gameplay feature enters here without a new plan.

## Tasks

1. [`✅-01-test-matrix.md`](✅-01-test-matrix.md) — compatibility and runtime matrix. **Complete** through the base, exact-host, WG-00–WG-07 and real full-pack acceptance gates.
2. [`✅-02-performance.md`](✅-02-performance.md) — performance budgets and profiling. **Complete** with five hot-path counters, bounded scheduler/index/cache contracts, configurable mutation budgets and a stored 0/1/10/50-site baseline.
3. [`✅-03-world-upgrade.md`](✅-03-world-upgrade.md) — SavedData/schema migration and downgrade safety. **Complete** with explicit v2 schemas, released unversioned-v1 migration, per-entry corruption isolation, future-schema fail-closed behavior and preview-gated metadata-only existing-world registration.
4. [`✅-04-release-checklist.md`](✅-04-release-checklist.md) — exact-head release acceptance. **Complete and canonical through PR #95, final head `837d6688cfe870e776984fa25f141db178c44d35`, squash merge `5187fb63baaf2006003b24767ac8943a3e9334a3`.** The dedicated Release Readiness gate maps all 19 canonical checklist conditions to executable evidence and aggregates the other nine canonical workflows on the same exact SHA.
5. [`✅-05-third-party-licenses-provenance.md`](✅-05-third-party-licenses-provenance.md) — public source/asset provenance, notices and fail-closed derivation gate. **Complete and canonical through PR #93, squash merge `bbb273d61984e2c9bb84e8f8a56668ae7e315532`.**

Task 05 was executed before Task 04 because the Task 04 release checklist explicitly requires the Stage 07 third-party provenance audit to be green. PR #93 final head `aeb50c9edbf6d33c840e050213662db5f8d52c09` passed all nine canonical workflows, including the hardened provenance audit and WG-00–WG-07 worldgen matrix after the isolated WG-06 acquisition retry. That prerequisite remains enforced by the final Release Readiness gate.

Stage 07 is complete once this post-merge Task 04 closeout itself passes all ten canonical workflows, is merged, and `main` is freshly verified at the resulting closeout SHA. No further unopened Stage 07 task remains.