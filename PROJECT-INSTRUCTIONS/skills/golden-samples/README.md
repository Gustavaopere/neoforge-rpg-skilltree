# Minecraft Art Golden Samples

Status: **REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.**

These samples show how to fill the canonical art-production contracts merged in PR #483 without inventing runtime ownership, provider APIs, registry IDs, or acceptance evidence.

## What is authoritative

Repository code/build configuration and the exact physical modlist remain authoritative. The examples below are process/reference data only. Any field not proven by repository/JAR/provider evidence stays `UNRESOLVED`.

Verified installed candidates relevant to this corpus at creation time:

- GeckoLib 4.9.2 for Minecraft 1.21.1/NeoForge;
- Photon 2.2.6.a;
- AAA Particles 2.2.3;
- AAA Particles World 2.0.0.

Presence is not API proof. No provider is selected automatically and no provider-native method/signature is asserted here.

## Samples

- `model-asset/` — `golden_reference_focus_relic`, a normalized structural fixture plus completed model/animation contracts.
- `spell/` — `golden_reference_arcane_bolt`, a complete presentation/VFX/audio/QA reference with gameplay/runtime fields intentionally unresolved.

## Important fixture boundary

`model-asset/validator-project-fixture.json` is normalized input for the project-owned RPG Asset Toolkit. It is **not** a Blockbench `.bbmodel`, is not an exporter artifact, and is not intended to ship in a resource pack.

The fixture contains metadata for one texture slot so Toolkit texture-reference validation can run; no PNG art is supplied or claimed.

## How to use this corpus

1. Copy the relevant brief/contract as a starting structure.
2. Replace the `golden_reference_*` identity with the real project identity.
3. Resolve every `UNRESOLVED` field from repository/JAR/exact-version provider evidence.
4. Create real model/texture/audio/VFX sources through the proper pipeline.
5. Run structural validation.
6. Capture actual editor/in-game/multiplayer evidence before changing QA from `PENDING` to `PASS`.

Do not copy the normalized fixture as if it were a native Blockbench source.

## Validation

The repository gate runs:

```bash
python3 PROJECT-INSTRUCTIONS/skills/scripts/validate_skill_repository.py
```

That invokes `validate_golden_samples.js`, which reuses the actual RPG Asset Toolkit and fails closed if this corpus drifts from its safety contract.
