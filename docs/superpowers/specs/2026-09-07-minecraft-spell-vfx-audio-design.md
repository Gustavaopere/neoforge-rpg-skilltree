# Minecraft Spell VFX and Audio Skill Design

## Objective

Add repository-native instructions that let future chats design and review polished Minecraft spell presentation without turning VFX/audio into a second gameplay authority or inventing APIs for the installed NeoForge 1.21.1 stack.

## Project target

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21
- physical modlist/JAR is authority for provider presence and version
- provider-native first; optional backends must fail safely

## Confirmed presentation stack at design time

Physical 2026-09-07 modlist:

- Photon 2.2.6.a
- AAA Particles 2.2.3
- AAA Particles: World 2.0.0
- GeckoLib 4.9.2
- Iron's Spells 'n Spellbooks 3.16.3

The gameplay guide still describing Photon 2.2.4 is stale for version authority. The physical JAR wins.

## Architecture

### Gameplay authority

The provider that owns cast, cost, cooldown, targeting, damage, summons or world mutation remains authoritative. VFX, sound, camera feedback and animation react to confirmed lifecycle boundaries; they do not create or infer gameplay state.

### Presentation lifecycle

The canonical conceptual sequence is:

`ANTICIPATION -> RELEASE -> TRAVEL/ACTIVE -> IMPACT/RESOLVE -> DECAY`

A spell may omit phases deliberately. One authoritative action produces one intended presentation sequence, with explicit deduplication between prediction/client presentation and authoritative server handling.

### Backend selection

1. provider-native visual/audio facilities;
2. Photon for project-owned advanced authored effects when justified;
3. AAA Particles/Effekseer when an Effekseer-authored effect is deliberately selected and the installed API is verified;
4. vanilla/NeoForge primitives for simple effects.

No backend is selected merely because it is installed. No Java class, method, packet or resource path is copied from another version without confirmation.

### Audio

Audio follows the same causal lifecycle. Cues are mapped to real events; loops have one owner and explicit start/stop/fade policy; networking is added only after verifying whether the provider already broadcasts or plays the sound.

## Contracts

`PROJECT-INSTRUCTIONS/skills/standards/SPELL-PRESENTATION-CONTRACT.md` records:

- gameplay owner and authoritative boundaries;
- visual identity and first/third-person readability;
- lifecycle phases;
- selected backend and proof;
- multiplayer causality/deduplication;
- audio event map;
- cleanup/lifecycle behavior;
- concurrency/performance test scenario;
- acceptance evidence.

`VFX-QA.md` and `AUDIO-QA.md` are final in-game gates, not editor-preview checklists.

## Lifecycle requirements

Persistent or entity-bound presentation must define behavior for relevant cancel/interruption, death, entity removal, logout, dimension change, chunk unload/reload and server stop paths. Dedicated server must not load client-only presentation classes.

## Performance

Do not freeze invented universal particle or audio budgets. The contract declares an expected concurrency scenario; QA validates that scenario in the real modpack, including overdraw/trails/post effects/loops where applicable.

## Accessibility

Critical gameplay information must not depend solely on one color or one sound. Effects should preserve target/crosshair/HUD readability and use reduced-effects behavior when the actual backend/provider exposes a supported mechanism.

## Provenance

Project-owned audio/VFX resources record source, creator, license/original status and redistribution constraints. Third-party assets are not copied because they are good references. Photon source/license terms apply to Photon itself; content authored with the tool is handled according to the exact LICENSE revision and the asset's own provenance.

## Repository integration

Add two preferred project-authored skills:

- `minecraft-spell-vfx-engineering`
- `minecraft-audio-design`

Expose them through the canonical router and extend `validate_skill_repository.py` from 22 to 24 canonical skills, preserving all prior art-pipeline checks.

## Non-goals

- no Java runtime implementation in this PR;
- no new mod dependency;
- no replacement of provider-native spell engines;
- no copying Iron's assets/code;
- no automatic aesthetic scoring;
- no frozen Photon/AAA Java signatures without JAR/source verification;
- no arbitrary particle-count/loudness constants presented as universal quality thresholds.

## Acceptance

The PR is acceptable when:

- the branch remains based on current main or is reconciled with it;
- the exact branch contains 24 canonical skill entrypoints and all specialized standards/source audits;
- repository CI executes the deterministic skill validator successfully;
- all applicable exact-head CI/security/full-pack gates are green;
- main is rechecked immediately before merge and the post-merge main SHA is confirmed.
