# Minecraft Art Pipeline Implementation Plan

**Goal:** add canonical art-direction and Blockbench/GeckoLib guidance plus a read-only structural validator for project-owned Minecraft assets.

**Base main:** `937df2b0ff6c336f5f297ef53be5ef72c86de83f`

**Spec:** `docs/superpowers/specs/2026-09-07-minecraft-art-pipeline-design.md`

## Tasks

- [x] Verify no equivalent open PR exists.
- [x] Re-check official Blockbench plugin/reference documentation.
- [x] Re-check GeckoLib Blockbench/GeckoLib 4 documentation for Minecraft 1.21.1.
- [x] Add `minecraft-asset-art-direction`.
- [x] Add `minecraft-blockbench-geckolib`.
- [x] Add canonical model asset contract.
- [x] Add visual QA standard.
- [x] Add read-only Blockbench structural validator.
- [x] Document upstream technical evidence.
- [x] Route the new skills and update repository README.
- [x] Extend deterministic skill validation from 20 to 22 skills and art-pipeline files.
- [ ] Validate Python syntax and JavaScript syntax from the exact branch content.
- [ ] Run the repository skill validator against the exact branch tree.
- [ ] Review branch diff against latest `main`.
- [ ] Re-sync if `main` advanced.
- [ ] Open PR and obtain applicable CI green.
- [ ] Re-check `main`, reconcile if needed, revalidate, merge and confirm post-merge SHA.

## Out of scope

- changing Java runtime/renderers;
- adding GeckoLib as a new dependency;
- modifying existing game assets;
- automatic aesthetic scoring;
- automatic model/UV mutation;
- VFX/spell/audio pipeline (separate follow-up PRs).
