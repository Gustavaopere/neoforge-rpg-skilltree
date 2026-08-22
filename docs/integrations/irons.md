# Iron's Spells 'n Spellbooks integration

Verified against the 1.21 branch of `iron431/irons-spells-n-spellbooks`.

## Native hooks we can use
`AbstractSpell` exposes school, mana cost, spell level/power, cast time and recast behavior. Casting posts `SpellPreCastEvent` and `SpellOnCastEvent`; spell level is modifiable through `ModifySpellLevelEvent`.

## Canonical attributes
Use Iron's own attributes instead of creating duplicates: max mana, mana regeneration, cooldown reduction, spell power, spell resistance, cast-time reduction, summon damage and casting movement speed. School power/resistance exists for Fire, Ice, Lightning, Holy, Ender, Blood, Evocation, Nature and Eldritch.

## Progression model
A real cast awards generic `magic:casting` mastery and the spell's actual school lane (`irons:fire`, etc.). Proc-generated echo/duplicate casts do not award mastery XP.

The main Arcane region grants broad magic stats. Gateway keystones such as `Mestre do Fogo` unlock the corresponding school tree only after the player has also earned school mastery XP.

## Candidate major nodes
- Echo Cast: conditional extra cast with proc-depth guard.
- Split Invocation: compatible projectile spells gain an additional projectile/trajectory behavior.
- Arcane Compression: lower mana cost at the cost of cast speed or power.
- Overchannel: stronger spell power with increased mana cost.
- School keystones: mechanics unique to each school rather than only `+X% power`.

Addon spells should participate automatically if they register as normal Iron's spells and expose a school through the base API.
