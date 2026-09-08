# Minecraft Art Pipeline Completion Implementation Plan

**Goal:** complete the missing art-pipeline infrastructure promised in the original design without adding fake runtime gameplay assets.

**Base at start:** `main@622bdb5b2b1d74646528d5b48ff1c3d37c7657cd`.

## Task 1 — Evidence and visual foundation

- [x] confirm physical modlist target stack;
- [x] query Notion visual/resource-pack inventory;
- [x] create Visual Style Bible;
- [x] create Visual Style source audit;
- [x] fail closed on universal texel-density values not proven by current corpus.

## Task 2 — Missing templates

- [x] ASSET-BRIEF
- [x] MODEL-BRIEF
- [x] ANIMATION-BRIEF
- [x] VFX-BRIEF
- [x] SPELL-BRIEF
- [x] AUDIO-CUE-SHEET
- [x] VISUAL-QA evidence template

## Task 3 — Missing skills

- [x] `minecraft-vfx-engineering`
- [x] `minecraft-spell-production`
- [x] `minecraft-visual-qa`
- [x] route/integrate with existing asset/spell/audio skills

## Task 4 — Blockbench RPG Asset Toolkit (TDD)

- [x] RED: behavioral tests fail because toolkit module is absent;
- [x] GREEN: implement read-only validator/profile support;
- [x] `node --check` passes;
- [x] 7/7 Node tests pass;
- [x] wire Node syntax/behavior tests through `validate_skill_repository.py`, which existing CI already executes;
- [x] preserve old validator as legacy compatibility path.

## Task 5 — Repository validation

- [x] update canonical skill validator from 24 to 27;
- [x] require Visual Style Bible/templates/toolkit/tests;
- [x] update README/router/source links;
- [ ] validate exact changed-file set;
- [ ] open PR;
- [ ] require all exact-head CI/security/full-pack gates green;
- [ ] re-check/reconcile main if advanced;
- [ ] rerun final validation after reconciliation;
- [ ] squash merge and confirm post-merge main SHA.

## Follow-up PR E

After this PR merges, create Golden Samples against the new canonical contracts. Golden Samples must not invent provider APIs or claim in-game validation that was not executed.
