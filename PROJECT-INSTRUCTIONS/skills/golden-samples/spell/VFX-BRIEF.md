# Golden Sample — VFX Brief

Status: **REFERENCE-ONLY — not a runtime registration and not evidence of shipped gameplay.**

Reference ID: `golden_reference_arcane_bolt`

## Readability goal

Make phase changes obvious without using one oversized emitter for every job. The effect should read as charge → release → moving projectile → confirmed impact → short decay while preserving target silhouette and combat readability.

## Layer plan

### Anticipation
- Layer A: compact emissive core at hand/focus.
- Layer B: sparse inward particles.
- Layer C: optional small ring only if it does not obscure first-person view.

### Release
- Layer A: short flash at `spell_muzzle`-equivalent attachment.
- Layer B: directional streak/cone supporting travel direction.
- Layer C: minimal burst debris/sparks.

### Travel / Active
- Layer A: compact projectile core.
- Layer B: subtle halo.
- Layer C: narrow short-lived trail.

### Impact / Resolve
- Layer A: central flash.
- Layer B: radial ring/fragments.
- Layer C: outcome-specific residue only after authoritative result.

### Decay
- sparse residual motes/fade; no gameplay effect.

## Provider matrix

| Candidate | Verified presence | Use decision | Exact API/asset format |
|---|---|---|---|
| Photon 2.2.6.a | yes | `UNRESOLVED` | `UNRESOLVED` |
| AAA Particles 2.2.3 | yes | `UNRESOLVED` | `UNRESOLVED` |
| AAA Particles World 2.0.0 | yes | `UNRESOLVED` | `UNRESOLVED` |

Provider-native first. Do not automatically combine Photon and AAA/Effekseer. Select one path only after exact-version capability/API evidence exists.

## Causality

- Anticipation may be local intent only.
- Release/travel authoritative start condition: `UNRESOLVED`.
- Impact must consume authoritative result evidence; exact event/hook is `UNRESOLVED`.
- Deduplication token/event identity: `UNRESOLVED`.

## Performance contract

- particle/emitter budgets: `UNRESOLVED` pending real provider profiling;
- max visible distance/culling: `UNRESOLVED`;
- simultaneous caster stress target: `UNRESOLVED`;
- fallback behavior: `UNRESOLVED`, fail-safe required.

## Evidence

No provider graph/effect file, screenshot, capture, or profiler trace is supplied. Visual/performance acceptance remains `PENDING`.
