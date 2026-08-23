# Beta 1 — Arcane awakening and provider identity

Version target: `1.0.0-beta.1-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: first Beta. The previous Alpha 9 pipeline produced a NeoForge JAR and passed a dedicated-server startup smoke test.

## Included
- the first Arcane-domain node, `rpgskilltree:arcane_000`, is now the shared **Arcane Awakening** gate for player-driven spellcasting;
- normal Iron's Spells 'n Spellbooks casts are canceled until Arcane Awakening is learned;
- Ars Nouveau casts are canceled until Arcane Awakening is learned;
- Creative players remain able to test spells without progression gating;
- Iron's command casts remain available for administration/debugging;
- fake/automated players do not earn Iron's mastery and are not forced through player progression;
- the access rule is provider-neutral so later magic systems can reuse the same canonical gate;
- core regression coverage ensures a neighboring Arcane node cannot bypass the awakening requirement.

## Class direction
The shared `Arcanist` remains the pure ARCANE trunk rather than being renamed. The provider identities branch above it:
- **Mage**: learned/catalogued magic centered on Iron's spellbooks, scrolls and schools;
- **Sorcerer**: intrinsic/modular spellcraft centered on Ars Nouveau glyph composition;
- neither route is an exclusive class lock; hybrid investment remains valid.

## Occult direction from the modpack guide
- Goety belongs primarily to dark magic, necromancy, servants and the Warlock/Necromancer side;
- Eidolon: Repraised belongs primarily to occult research, rituals, alchemy and pact-oriented progression;
- Malum is treated as spirit/soul arcana and may feed Occult plus hybrid branches instead of being forced into one class.

## Next Beta blocks
- provider mastery gates that make Mage and Sorcerer emerge from actual Iron's/Ars usage;
- Goety/Eidolon/Malum runtime adapters and pact/ritual progression;
- technology placement from the supplied Create/Oritech/AE2/Sable Aeronautics guide;
- gameplay-system placement from the supplied RPG/survival/boss/exploration guide.
