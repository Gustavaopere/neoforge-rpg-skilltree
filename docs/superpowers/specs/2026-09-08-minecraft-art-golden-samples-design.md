# Minecraft Art Golden Samples — Design

Status: APPROVED FOLLOW-UP OF PR #483

Branch: `docs/minecraft-art-golden-samples`
Base main SHA: `65645778a59653ca6844a9249cfe491af72a3666`

## Purpose

Create a small project-owned reference corpus that shows other chats how to fill and validate the canonical model, animation, spell, VFX, audio, and visual-QA contracts introduced by PR #483.

The corpus is executable where deterministic validation is possible, but it is **not runtime content** and must never be treated as evidence that a spell, model, provider hook, sound event, or resource is registered in the mod.

## Authority and safety

Every Golden Sample file must carry this semantic boundary:

> REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.

Rules:

1. Repository reality and the exact physical modlist remain authority.
2. Unknown runtime/provider fields stay `UNRESOLVED`; examples do not guess APIs, registries, export paths, network messages, sound events, or provider hooks.
3. Provider names may be listed only as verified installed candidates. Current physical-modlist evidence relevant to this corpus includes GeckoLib 4.9.2, Photon 2.2.6.a, AAA Particles 2.2.3, and AAA Particles World 2.0.0 for Minecraft 1.21.1/NeoForge.
4. The spell example is named `golden_reference_arcane_bolt`; it has no gameplay owner and no runtime registration.
5. The model example is named `golden_reference_focus_relic`; it has no runtime item/entity registration.
6. Client presentation may describe intended phases, but server authority, causality, resource cost, cooldown, target resolution, hit/damage, and provider APIs remain `UNRESOLVED` unless a real runtime implementation is later linked.
7. Visual QA cannot claim PASS without actual screenshot/in-game evidence. The Golden Sample demonstrates correct `PENDING` handling where evidence is absent.

## Corpus layout

Canonical location:

```text
PROJECT-INSTRUCTIONS/skills/golden-samples/
├── README.md
├── model-asset/
│   ├── ASSET-BRIEF.md
│   ├── MODEL-CONTRACT.md
│   ├── ANIMATION-BRIEF.md
│   ├── TOOLKIT-PROFILE.json
│   └── validator-project-fixture.json
├── spell/
│   ├── SPELL-BRIEF.md
│   ├── SPELL-PRESENTATION.md
│   ├── VFX-BRIEF.md
│   ├── AUDIO-CUE-SHEET.md
│   ├── VISUAL-QA.md
│   ├── VFX-QA.md
│   └── AUDIO-QA.md
└── validate_golden_samples.js
```

## Model fixture semantics

`validator-project-fixture.json` is a **normalized test fixture consumed by the project-owned RPG Asset Toolkit**. It is deliberately not named `.bbmodel` and must not be documented as a Blockbench-native source file.

It exercises:

- valid lower-snake-case bones/groups;
- a valid renderable cube with finite bounds, pivot, UVs, and a resolvable texture;
- a valid Locator-like attachment/VFX point with `from` and no cube-style `to`;
- required animation names;
- contract profile required bones/animations/maxSpan;
- the Locator-aware path fixed in PR #483.

An actual `.bbmodel` is out of scope until the exact Blockbench/GeckoLib file schema and exporter path are verified from a real project source.

## Reference model

`golden_reference_focus_relic` demonstrates:

- complete asset identity and ownership boundary;
- source-of-truth and provenance fields;
- intended silhouette/material/UV/animation notes;
- attachment/VFX anchor contract;
- explicit `UNRESOLVED` runtime consumer/export/provider fields;
- a Toolkit profile that deterministically validates the normalized fixture.

The sample does not include fabricated PNG art, fabricated `.bbmodel`, or fabricated runtime class/resource paths.

## Reference spell

`golden_reference_arcane_bolt` demonstrates the canonical presentation lifecycle:

`Anticipation -> Release -> Travel/Active -> Impact/Resolve -> Decay`

It must separate:

- presentation intent from gameplay authority;
- client-local anticipation from server-confirmed result presentation;
- VFX provider selection from provider API implementation;
- audio cue design from actual `SoundEvent` registration;
- visual QA state from actual acceptance evidence.

The sample intentionally leaves gameplay values that would imply a real registered spell (`damage`, exact mana/resource cost, cooldown, networking hook, registry ID, provider API calls) as `UNRESOLVED`.

## Deterministic validator

`validate_golden_samples.js` will:

1. load the actual project-owned `rpg_asset_toolkit.js`;
2. parse `validator-project-fixture.json` and `TOOLKIT-PROFILE.json`;
3. require zero Toolkit errors;
4. require the fixture to contain at least one cube-like element and one Locator-like element;
5. require the profile bones/animations to be present;
6. require the model and spell IDs expected by this corpus;
7. verify that documentation contains the `REFERENCE-ONLY` and `UNRESOLVED` safety markers where required;
8. reject any `.bbmodel` file inside the Golden Sample corpus in this phase.

`PROJECT-INSTRUCTIONS/skills/scripts/validate_skill_repository.py` will invoke this validator so the corpus is part of the repository CI contract.

## TDD sequence

1. Add repository-validator expectations for the corpus before corpus files exist.
2. Commit and capture CI RED caused specifically by missing Golden Sample files.
3. Add the corpus and executable validator.
4. Capture GREEN for the repository skill gate.
5. Run full repository CI on the final reconciled HEAD before merge.

## Non-goals

This PR does not:

- register a new spell, item, entity, particle, sound, network packet, or provider bridge;
- introduce new gameplay authority;
- add or guess Photon/AAA/GeckoLib APIs;
- add a fake `.bbmodel` or fake texture;
- claim screenshot/in-game visual acceptance without evidence;
- replace project templates or standards.

## Acceptance criteria

The PR is acceptable only if:

- every sample is visibly reference-only;
- all unknown runtime fields are fail-closed as `UNRESOLVED`;
- the normalized model fixture passes the actual RPG Asset Toolkit including Locator handling;
- the spell sample covers presentation, VFX, audio, multiplayer causality, performance, and evidence state without inventing implementation;
- repository validation runs the Golden Sample validator;
- full exact-head CI is green after the final `main` synchronization;
- no unresolved review thread remains before merge.
