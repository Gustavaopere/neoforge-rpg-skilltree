# Ars Nouveau integration

Verified against `baileyholl/Ars-Nouveau` current source.

## Native hooks we can use
`SpellModifierEvent` fires before a spell part resolves and provides caster, spell part, hit result, level, spell context and a mutable `SpellStats.Builder`.
`EffectResolveEvent.Pre/Post` provides the full spell, current effect, spell stats and resolver around each effect resolution.

`SpellStats` directly exposes/modifies amplification, acceleration, damage modifier, duration, AOE and augment lists.

## Progression model
Do not invent static schools for Ars. Classify the spell from its actual composition/effects/augments. This lets addon glyphs work naturally when they use the standard Ars APIs.

Initial mastery lanes:
- `ars:projectile`
- `ars:amplification`
- `ars:aoe`
- `ars:duration`
- `ars:control`
- `ars:summoning`

## Candidate major nodes
- Projectile Mastery: additional projectile behavior for compatible delivery methods.
- Recursive Geometry: AOE scaling after chained effects, with hard recursion guard.
- Efficient Amplification: amplification costs less mana but has diminishing returns.
- Lingering Formula: duration effects persist longer but direct damage is reduced.
- Construct Savant: summon/construct scaling.

The user's pack includes Ars Elemental, Elemancy, Additions, Creo, Technica, Ars 'n' Spells and other addons, so composition-based detection is preferred over hardcoded spell IDs.
