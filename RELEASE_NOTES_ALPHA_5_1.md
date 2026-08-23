# Alpha 5.1 — Iron's depth pass

This revision begins the retroactive depth audit requested after the Malum integration. It revisits Alpha 1 instead of moving directly to the next provider.

## Iron's Spells 'n Spellbooks

### Permanent study is now progression
- `InscribeSpellEvent` is used as a real gameplay gate for putting a scroll spell permanently into a spellbook.
- Arcane Awakening remains the universal prerequisite.
- Level 1 spells can be inscribed immediately after awakening.
- Level 2 spells require demonstrated Iron provider mastery and mastery in that spell's actual school.
- Level 3+ spells additionally require the emergent `mage` identity.
- Requirements scale linearly for spell levels above the base game's normal tiers, so addon spells inherit the rule without hard-coded spell lists.
- Scroll casting is deliberately left available as the apprenticeship/practice route; this prevents a deadlock where the player needs mastery but cannot practice the spell.

Current study thresholds per spell level are derived from the number of tiers above level 1:
- `irons:casting`: 30 XP per tier.
- the spell's `irons:<school>` lane: 15 XP per tier.
- Mage identity: required from spell level 3 onward.

The values are intentionally isolated in `IronStudyPolicy` so they can be balanced later without changing provider event code.

### Casting mastery now reflects real resource commitment
Iron casting previously awarded the same provider/school mastery for a cheap spell and an expensive spell. Alpha 5.1 now derives a bounded intensity from the mana actually spent:
- cheap spells retain the old 3 provider / 5 school mastery floor;
- higher-cost spells award progressively more provider and school practice;
- intensity is capped, so a single extreme addon spell cannot produce unbounded mastery;
- shared `magic:casting` remains fixed at 2 to keep cross-provider identity neutral.

### Addon compatibility
School identity continues to come from Iron's `SchoolType` registry ID. Base schools use short lanes such as `irons:fire`; addon schools use namespace-qualified lanes such as `irons:<addon_namespace>/<school>`. This avoids a spell-name whitelist and lets compatible Iron addons participate automatically.

## Why this is deeper than Alpha 1
Alpha 1 proved real casts and schools could feed mastery. Alpha 5.1 makes the resulting mastery alter an actual Iron system: permanent spellbook study. The Mage identity is therefore no longer only a label derived from `irons:casting`; it becomes a prerequisite for advanced learned/catalogued magic while scrolls remain the practical training route.

## Next retroactive audits
- Ars Nouveau: glyph unlock/composition depth, Source/ritual/familiar hooks and bridge deduplication.
- Epic Fight: stamina, dodge, guard/parry, skill execution and weapon-skill state beyond damage hits.
- Goety: Soul economy plus servant summon/command/upkeep/state beyond spell expenditure.
