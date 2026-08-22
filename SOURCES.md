# Source Projects and Provenance

This private project studies and integrates ideas/code/data from multiple Minecraft mods and datapacks. This file records provenance so behavior can be traced when debugging or updating.

## Engine foundation

### Passive Skill Tree

- Author: Daripher
- Official source: https://github.com/Daripher/Passive-Skill-Tree
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree
- Role: primary renderer/UI/data-engine foundation
- Official latest observed during initial research: 1.20.1 `0.7.6e`

### Passive Skill Tree — NeoForge 1.21.1 community port

- Source branch: https://github.com/themarneilx/Passive-Skill-Tree/tree/1.21.1-neoforge
- Role: starting point for Minecraft 1.21.1 / NeoForge port work
- Initial observed base: `0.7.6c`
- Project policy: reconcile upstream `0.7.6c -> 0.7.6e` fixes before major extensions

## Requested progression/content references

### Magic Schools Skill Trees

- Author: TGxRedPlayer
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/addon-for-irons-spells-n-spellbooks-unofficial
- Role: Iron school-specific XP-routing and mastery design reference

### Unofficial Iron's Spells 'n Spellbooks Skill Tree

- Author: TGxRedPlayer
- Linked from Magic Schools project
- Role: related main-tree/Pufferfish implementation reference

### Iron's Spells 'n Spellbooks Dynamic Skill Trees

- Author: RaiRaimu
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks-dynamic-skill-trees
- Issue tracker: https://github.com/raimu-music/Dynamic-Skills-Trees-Issues
- Role: magic progression pacing, XP weighting and specialization design reference

### Skills Mastery Reimagined

- Author: DefinitelyNotADoctor
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/skills-mastery-reimagined
- Role: unique-node, negative-tradeoff and non-combat XP-source design reference

### Scion: Races and Skill Tree

- Author: Numberk00l / source repository owner Meteta
- Source: https://github.com/Meteta/MC_scion_skilltree
- CurseForge: https://www.curseforge.com/minecraft/data-packs/scion-skilltree
- Role: macro-region and broad-build design reference

### Waifing - Passive Skill Tree

- Author: Dankest0
- CurseForge: https://www.curseforge.com/minecraft/data-packs/waifing-passive-skill-tree
- Role: old large-tree topology/content reference; Iron/Create/Apotheosis integration ideas
- Compatibility note: designed for Passive Skill Tree 0.6.14a/b and documented to break on >=0.7

### Passive Skill Tree Additions

- Author: Thrasos
- Source: https://github.com/thrasos-dev/passiveskilltreeadditions-mod
- CurseForge: https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree-additions
- Role: inventory-button/QoL feature reference

## Primary integration APIs

### Iron's Spells 'n Spellbooks

- Source: https://github.com/iron431/irons-spells-n-spellbooks
- Target branch family: `1.21`
- Role: spell schools, casting events, spell-level/mana/cast-time/recast hooks and canonical Iron magic attributes

### Ars Nouveau

- Source: https://github.com/baileyholl/Ars-Nouveau
- Role: spell-part/glyph resolution events and `SpellStats` modification API

### Epic Fight

- Source: https://github.com/Antikythera-Studios/epicfight
- Target branch: `1.21.1`
- Role: weapon categories/capabilities, combat event hooks, skill cast/consume and category-aware combat modifiers

### Create

- Source: https://github.com/Creators-of-Create/Create
- Role: kinetics/engineering actions, machinery-aware mastery and hybrid Technomancer integrations

### Curios

- Source: https://github.com/TheIllusiveC4/Curios
- Role: expandable equipment capability and dedicated Attunement-slot integration

## Internal provenance rule

When a final node is strongly derived from a specific source project rather than merely sharing a generic RPG concept, record that origin in the development documentation/data comment or provenance metadata. This is primarily for maintenance: if the source changes, we can identify which unified mechanics should be re-evaluated.
