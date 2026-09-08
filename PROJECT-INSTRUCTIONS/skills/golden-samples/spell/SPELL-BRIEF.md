# Golden Sample — Spell Brief

Status: **REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.**

## Identity

- Reference ID: `golden_reference_arcane_bolt`
- Gameplay owner/mod: `UNRESOLVED`
- Runtime spell/ability registry ID: `UNRESOLVED`
- Provider-native spell implementation: `UNRESOLVED`
- Intended presentation role: demonstrate a readable single-projectile spell lifecycle without defining real gameplay.

## Gameplay authority boundary

The example intentionally does not invent gameplay values.

- Cast legality: `UNRESOLVED` — server authority required in a real implementation.
- Resource/mana cost: `UNRESOLVED`.
- Cooldown: `UNRESOLVED`.
- Damage/effect: `UNRESOLVED`.
- Targeting/range/velocity: `UNRESOLVED`.
- Hit/miss/block resolution: `UNRESOLVED`.
- Anti-abuse/deduplication key: `UNRESOLVED`.

A real implementation must prove these from its canonical gameplay system before presentation is wired.

## Presentation intent

Lifecycle:

`Anticipation -> Release -> Travel/Active -> Impact/Resolve -> Decay`

- Anticipation: compact charge cue around the cast hand/focus; must not visually prove a successful cast.
- Release: clear burst only when the authoritative cast is confirmed.
- Travel/Active: readable core + restrained trail, preserving target visibility.
- Impact/Resolve: impact family selected from authoritative outcome; no client-only hit confirmation.
- Decay: short residual fade with no gameplay authority.

## Asset dependencies

- Reference model attachment pattern: `golden_reference_focus_relic` / `spell_muzzle`.
- Real model dependency: `UNRESOLVED`.
- Real animation dependency: `UNRESOLVED`.
- Real VFX assets/provider graphs: `UNRESOLVED`.
- Real audio assets/SoundEvents: `UNRESOLVED`.

## Provider candidates

Physical-modlist presence establishes candidates, not integration:

- Photon 2.2.6.a — candidate for authored particle/VFX graph work.
- AAA Particles 2.2.3 / AAA Particles World 2.0.0 — candidate where exact supported effect path is proven.
- Selected provider and exact API/hook: `UNRESOLVED`.

Do not stack providers by default.

## Acceptance state

Structural/document completeness can be validated. In-game spell behavior, multiplayer causality, visual quality, audio mix, and performance remain `PENDING` until a real implementation and evidence exist.
