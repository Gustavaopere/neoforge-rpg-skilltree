# Hardening Plan — Release Checklist

**Goal:** define the gate for a pack-usable/publicly distributable build.

A release candidate is acceptable only when all are true:

- [x] `gradle test` green.
- [x] `gradle build` green.
- [x] dedicated-server smoke green.
- [x] no TFC/TFC Registry runtime dependency.
- [x] no required optional-mod dependency.
- [x] full-pack startup and world-load test green.
- [x] new-world volcano generation inspected.
- [x] existing-world no-retrogen safety inspected.
- [x] MineColonies protected-area tests green.
- [x] eruption + atmosphere + Destroy integration has no double damage/pollution.
- [x] pressure + Create/Sable protection consumes resources once.
- [x] performance budgets verified.
- [x] `LICENSE`, `SOURCES.md` and `THIRD_PARTY_NOTICES.md` are current.
- [x] built JAR contains the Volcanoes `LICENSE` and `THIRD_PARTY_NOTICES.md`.
- [x] the Stage 07 third-party provenance audit is green for every dependency/reference/compatibility target relevant to release.
- [x] every actual `DERIVED_CODE`/`DERIVED_ASSET` entry has exact upstream revision/file provenance and all required notices/permissions.
- [x] no release contains actual derived material whose status is `REVIEW_REQUIRED`, `PERMISSION_REQUIRED` or unknown.
- [x] TFC Volcanoes-derived material, if any, satisfies the BSD notice and root/file-level provenance rules.
- [x] completed plan files are renamed with `✅-` prefix and `STATUS.md` records the final merge SHA.

## Canonical closeout

Task 04 implementation was accepted on PR #95, final exact head `837d6688cfe870e776984fa25f141db178c44d35`, after all ten canonical workflows completed GREEN. PR #95 was squash-merged to `main` as `5187fb63baaf2006003b24767ac8943a3e9334a3` on 2026-08-31.

The dedicated `Release Readiness` gate validates this checklist against executable repository evidence. On pull-request release candidates it aggregates the other nine canonical workflows on the same exact PR head before reporting GREEN. On `main` pushes it runs its own release contract without waiting for acceptance workflows that intentionally do not trigger on `main`.
