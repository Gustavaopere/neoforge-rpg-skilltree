# Beta 2 — Emergent Mage and Sorcerer identities

Version target: `1.0.0-beta.2-dev`
Target: Minecraft 1.21.1 / NeoForge

## Included
- class definitions can now require live mastery lanes and learned passive nodes in addition to completed final-triad domains;
- the original four-field class constructor remains supported, preserving all existing pure and hybrid class definitions;
- **Mage** is an automatic provider identity requiring Arcane Awakening plus `irons:casting >= 60`;
- **Sorcerer** is an automatic provider identity requiring Arcane Awakening plus `ars:casting >= 60`;
- Mage and Sorcerer are deliberately non-exclusive and may coexist on the same character;
- removing Arcane Awakening through respec removes the provider identity while preserving mastery already earned;
- Iron's direct casts now award a provider-wide `irons:casting` lane while retaining school mastery;
- Ars Nouveau direct casts now award a provider-wide `ars:casting` lane while retaining composition mastery;
- class JSON validation now accepts provider identities with no completed-domain requirement only when node and/or mastery requirements are present.

## Design rationale
`Arcanist` remains the deep pure-ARCANE class reached through the final ARCANE triad. Mage and Sorcerer describe how the character actually practices magic after Arcane Awakening rather than replacing that trunk:
- Iron's spellbooks, scrolls and fixed schools cultivate Mage;
- Ars Nouveau glyph composition cultivates Sorcerer;
- training both supports both identities and feeds later hybrids instead of imposing a class lock.

The 60-XP provider threshold corresponds to twenty qualifying direct casts at the current +3 provider mastery award. School/glyph specialization XP remains separate so provider identity cannot replace specialization progression.

## Next Beta blocks
- connect Iron school specialization eligibility more explicitly to Mage and Ars composition specialization eligibility to Sorcerer without breaking hybrid access;
- add real Goety, Eidolon: Repraised and Malum progression adapters using their actual 1.21.1 APIs/events;
- continue placement of technology and gameplay systems from the supplied Notion guides.
