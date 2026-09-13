# Minecraft Art Golden Samples — Implementation Plan

> Execution target: `docs/minecraft-art-golden-samples`
> Base main SHA: `65645778a59653ca6844a9249cfe491af72a3666`
> Design: `docs/superpowers/specs/2026-09-08-minecraft-art-golden-samples-design.md`

## Goal

Add an executable, project-owned reference corpus for model/animation/spell/VFX/audio/visual-QA production without introducing fake runtime registrations or unverified provider APIs.

## Task 1 — Establish RED repository contract

Files:
- modify `PROJECT-INSTRUCTIONS/skills/scripts/validate_skill_repository.py`

Steps:
1. Require the Golden Sample root and all design-approved files.
2. Require Node and execute `PROJECT-INSTRUCTIONS/skills/golden-samples/validate_golden_samples.js`.
3. Do not add the corpus yet.
4. Commit this validator-only change.
5. Verify the repository skill gate fails specifically because the Golden Sample files/validator are absent.

Expected evidence: deterministic RED attributable to the intended missing corpus, not syntax/environment failure.

## Task 2 — Add reference model corpus

Files:
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/README.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/model-asset/ASSET-BRIEF.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/model-asset/MODEL-CONTRACT.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/model-asset/ANIMATION-BRIEF.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/model-asset/TOOLKIT-PROFILE.json`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/model-asset/validator-project-fixture.json`

Requirements:
- ID `golden_reference_focus_relic`.
- Every document states `REFERENCE-ONLY`.
- Runtime consumer/export/provider hooks remain `UNRESOLVED`.
- Fixture is explicitly normalized Toolkit input, not `.bbmodel`.
- Fixture contains valid cube + valid Locator paths.
- Profile requires `root`, `cast_hand`, `vfx_anchor`, idle and cast animations, plus a bounded `maxSpan`.

## Task 3 — Add reference spell presentation corpus

Files:
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/SPELL-BRIEF.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/SPELL-PRESENTATION.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/VFX-BRIEF.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/AUDIO-CUE-SHEET.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/VISUAL-QA.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/VFX-QA.md`
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/spell/AUDIO-QA.md`

Requirements:
- ID `golden_reference_arcane_bolt`.
- Cover Anticipation, Release, Travel/Active, Impact/Resolve, Decay.
- Separate server authority from client presentation.
- Leave real registry/network/provider API details `UNRESOLVED`.
- Record verified installed provider candidates only; do not select or stack providers without implementation evidence.
- QA without screenshots/in-game execution remains `PENDING`, not PASS.

## Task 4 — Add executable Golden Sample validator

Files:
- create `PROJECT-INSTRUCTIONS/skills/golden-samples/validate_golden_samples.js`

Checks:
1. Load `../tools/blockbench/rpg-asset-toolkit/rpg_asset_toolkit.js` from the canonical project tree.
2. Parse fixture/profile JSON.
3. Run `validateProject(fixture, profile)` and require zero errors.
4. Verify at least one cube-like element and one Locator-like element.
5. Verify expected required bones/animations and finite bounds/counts.
6. Verify reference IDs and safety markers across documents.
7. Require explicit `UNRESOLVED` markers for runtime/provider fields.
8. Fail if a `.bbmodel` is present in the Golden Sample directory during this phase.

Run through the existing Python repository validator; no third-party npm package.

## Task 5 — GREEN and repository integration

1. Run/observe `Verify repository Minecraft skill infrastructure` on the resulting HEAD.
2. Require the Golden Sample validator to pass alongside the existing 27-skill and RPG Asset Toolkit tests.
3. Inspect logs to ensure failures are not hidden/skipped.
4. Update `PROJECT-INSTRUCTIONS/skills/README.md` and/or `ROUTER.md` only if needed to expose the Golden Samples discoverably; do not duplicate routing authority.

## Task 6 — Review and concurrency synchronization

1. Fetch current `origin/main`.
2. Record SHA and compare against base `65645778a59653ca6844a9249cfe491af72a3666`.
3. If main advanced, merge `origin/main` into the shared branch semantically; no rebase/force-push.
4. Review changed files and diff for accidental runtime claims/API invention.
5. Rerun validation on the reconciled HEAD.

## Task 7 — PR and full exact-head validation

1. Open PR from `docs/minecraft-art-golden-samples` to `main`.
2. Record PR number and final branch HEAD.
3. Require all applicable repository workflows on that exact HEAD:
   - repository skill validation;
   - JUnit/NeoForge tests and GameTests where triggered;
   - build + built-JAR verification;
   - dedicated-server smoke;
   - Full Pack / Worldgen acceptance where the repository triggers them;
   - SonarQube;
   - CodeQL;
   - consolidated Release Readiness.
4. Investigate any failure rather than disabling/rerouting gates.
5. Review and resolve technical PR feedback with regression coverage where applicable.

## Task 8 — Final merge protocol

1. Fetch `origin/main` again immediately before merge.
2. If main advanced, reconcile and rerun exact-head CI; prior CI is invalid as final evidence.
3. Ensure no unresolved review thread remains.
4. Merge with `expected_head_sha` protection.
5. Confirm PR merged state, post-merge main SHA, and that main contains Golden Samples.
6. Report branch, PR, base main SHA, final HEAD, tests/CI, review state, merge state, post-merge main SHA, and remaining risks/pending cleanup.

## Known cleanup note

An accidental empty branch `docs/minecraft-art-golden-samples-spec` was created at base SHA `65645778a59653ca6844a9249cfe491af72a3666`. The available GitHub connector exposes no branch/ref deletion action. It must receive no commits or PR and should be deleted when a tool with ref deletion is available.
