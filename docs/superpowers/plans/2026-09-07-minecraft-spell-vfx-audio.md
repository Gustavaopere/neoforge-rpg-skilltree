# Minecraft Spell VFX and Audio Skills Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** add canonical spell-VFX and audio instructions, contracts and QA gates while preserving provider gameplay authority on NeoForge 1.21.1.

**Architecture:** extend the repository skill system merged by #479/#480. Presentation is modeled as a causal layer around provider-owned gameplay; the router selects project-authored VFX/audio skills, and the deterministic repository validator guarantees the entrypoints and standards remain present.

**Tech Stack:** Markdown skill/contract files, Python 3 validator, GitHub Actions existing `RPG Skill Tree CI`, Minecraft 1.21.1 / NeoForge 21.1.x / Java 21 context.

**Spec:** `docs/superpowers/specs/2026-09-07-minecraft-spell-vfx-audio-design.md`

## Global Constraints

- Physical modlist/JAR is authority for provider presence/version.
- Provider-native first.
- Do not invent Java APIs, events, packets, resource paths or signatures.
- VFX/audio may not become gameplay authority.
- Optional backends must fail safely and remain dedicated-server classloading safe.
- No third-party asset/code copying without compatible license/provenance.

---

### Task 1: Spell VFX skill and presentation contract

**Files:**
- Create: `PROJECT-INSTRUCTIONS/skills/library/minecraft-spell-vfx-engineering/SKILL.md`
- Create: `PROJECT-INSTRUCTIONS/skills/standards/SPELL-PRESENTATION-CONTRACT.md`
- Create: `PROJECT-INSTRUCTIONS/skills/standards/VFX-QA.md`

**Interfaces:**
- Consumes: `VERSION-AUTHORITY.md`, `USER-GUIDED-WORKFLOW.md`, existing art-direction/Blockbench skills.
- Produces: lifecycle vocabulary and QA gates used by future spell/VFX work.

- [x] Define `ANTICIPATION -> RELEASE -> TRAVEL/ACTIVE -> IMPACT/RESOLVE -> DECAY` without requiring every phase for every spell.
- [x] Preserve provider-native gameplay authority and require deduplication of client/server presentation.
- [x] Document lifecycle cleanup, multiplayer, dedicated-server and concurrency requirements.
- [x] Add backend selection rules for provider-native, Photon, AAA/Effekseer and simple vanilla/NeoForge presentation.
- [x] Add in-game VFX QA rather than editor-only acceptance.

### Task 2: Audio skill and QA

**Files:**
- Create: `PROJECT-INSTRUCTIONS/skills/library/minecraft-audio-design/SKILL.md`
- Create: `PROJECT-INSTRUCTIONS/skills/standards/AUDIO-QA.md`

**Interfaces:**
- Consumes: authoritative gameplay events and Spell Presentation Contract.
- Produces: audio event map, loop ownership rules, mix/accessibility/provenance acceptance.

- [x] Define cue mapping for start/release/travel/impact/resolve/cancel/tail/loops.
- [x] Require one audible event per intended listener and prevent provider + custom duplicate playback.
- [x] Require loop lifecycle ownership and cleanup.
- [x] Require real-modpack mix/spatialization testing and rights/provenance records.

### Task 3: Version/source audit

**Files:**
- Create: `PROJECT-INSTRUCTIONS/skills/SPELL-VFX-AUDIO-SOURCES.md`

**Interfaces:**
- Consumes: physical modlist and official upstream sources.
- Produces: auditable version/backend context; does not freeze Java signatures.

- [x] Record physical Photon 2.2.6.a, AAA Particles 2.2.3, AAA World 2.0.0, GeckoLib 4.9.2 and Iron's 3.16.3.
- [x] Record Photon 2.2.4 guide divergence and physical-JAR authority.
- [x] Record official Photon, AAA/Effekseer and Iron's reference evidence.
- [x] Record Photon LICENSE evidence and fail closed on redistribution ambiguity.

### Task 4: Router and deterministic validation

**Files:**
- Modify: `PROJECT-INSTRUCTIONS/skills/ROUTER.md`
- Modify: `PROJECT-INSTRUCTIONS/skills/README.md`
- Modify: `PROJECT-INSTRUCTIONS/skills/scripts/validate_skill_repository.py`

**Interfaces:**
- Consumes: Task 1-3 paths.
- Produces: 24-skill canonical repository contract checked by existing CI.

- [x] Route spell/VFX/audio work through the two new project-authored skills and standards.
- [x] Expand expected skill set from 22 to 24.
- [x] Require new source audit and three presentation standards.
- [x] Preserve prior Blockbench/art-pipeline validations.
- [x] Add deterministic content-token checks for the new contracts.

### Task 5: Exact-head validation and merge

**Files:**
- No runtime files.

**Interfaces:**
- Consumes: final branch HEAD and current `main`.
- Produces: merged, reproducible skill infrastructure or an explicit technical blocker.

- [x] Confirm no equivalent VFX-skill PR/branch exists.
- [x] Create branch from `main@6756fc8aaead51bbbd3af94f167240040bfb8bbf`.
- [ ] Re-check current `main` and verify branch is 0 behind before PR.
- [ ] Review exact changed-file set for scope leakage.
- [ ] Open PR against `main`.
- [ ] Confirm `Verify repository Minecraft skill infrastructure` passes on exact PR HEAD.
- [ ] Require all applicable RPG CI, full-pack, CodeQL, Sonar and release-readiness gates green.
- [ ] Re-check `main` immediately before merge; reconcile and rerun validation if it advanced.
- [ ] Squash merge with expected HEAD SHA.
- [ ] Confirm post-merge `main` SHA.
