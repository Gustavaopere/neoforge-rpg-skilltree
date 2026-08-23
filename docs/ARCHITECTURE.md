# Architecture

## Goal
Build a private NeoForge 1.21.1 progression mod around the Passive Skill Tree UI/runtime, with a large Path of Exile-style main tree and specialized mastery trees.

## Core rules
- One visible progression language: Passive Skill Tree-style nodes, connections, icons, tooltips and editor/data format.
- Classes are emergent labels, not character-creation locks. A player can qualify for several archetypes at once.
- Hybrid archetypes rank above generic parents when their investment requirements are met.
- Generic passive points and mastery XP are separate currencies.
- A specialized tree requires: main-tree investment + gateway node/tag + relevant mastery XP.
- All numeric bonuses pass through canonical stat IDs to avoid duplicate meanings across mods.
- Proc-generated actions carry a depth marker so echoes/duplicates cannot recursively generate themselves or farm mastery XP.
- Native provider progression remains authoritative where it already represents a real mechanic; the RPG tree specializes and bridges it instead of cloning it.

## Main tree
The current blueprint budgets **512 nodes** on a 4608x4608 canvas across **11 universal regions**: Martial, Agility, Vitality, Healing, Arcane, Engineering, Mining, Survival, Summoning, Occult and Logistics.

The topology is deliberately physical. Adjacent regions form natural confluences; non-adjacent identities can require paid bridge corridors. The central wheel, domain fans, hybrid corridors and outer keystones are generated deterministically from `tree_blueprints/main.json`.

The three outermost nodes of each domain are the persistent 3/3/3 final triad used by class-completion rules.

## Semantic tree architecture
The purchase graph and the semantic architecture are separate layers:

- `data/rpgskilltree/node_rules/` owns buyable node IDs, ranks, point cost, access requirements, specialization grants and graph neighbors.
- `data/rpgskilltree/tree_architecture/` owns tree identity, type, provider, domains, ordered branches, gateway requirements, tags and intentional cross-tree bridges.
- `TreeRuleCatalog` is the runtime purchase/graph catalog.
- `TreeArchitectureCatalog` is the runtime semantic catalog.
- `NodeRulesReloader` and `TreeArchitectureReloader` reload the two layers independently from datapacks.

This split allows one stable purchase engine to serve many specialist trees while the planner/UI can reason about concepts such as “Fire -> Ignite -> Flameborne -> Lava” instead of treating every node as an anonymous coordinate.

The first semantic pass materializes **83 tree definitions** covering the main tree, Iron schools, Ars composition families, combat weapons, TFC survival, technology/logistics providers, occult/summoning systems, morph/ecology and hybrid classes.

## Specialized trees
### Iron's Spellbooks
Fire, Ice, Lightning, Holy, Ender, Blood, Evocation, Nature and Eldritch. School practice comes from the real provider school/action and gateway mastery, not from a second invented spell-school system.

### Ars Nouveau
Projectile, Touch, Self, AoE, Amplification, Duration, Control, Summoning, Movement and Manipulation/Automation. These trees are derived from spell composition/glyph semantics rather than pretending Ars uses Iron-style schools.

### Combat
Sword, Greatsword, Spear, Axe, Dagger, Hammer, Bow, Crossbow, Polearm, Guard/Stagger and Mobility. Epic Fight remains the combat-state authority where applicable; ParCool remains the parkour authority.

### TFC / Survival
Metabolism, nutrition, climate, food preservation, farming, forestry, fauna, prospecting and Beneath expedition. These branches specialize real TFC/Cold Sweat/Firmalife systems and must not remove the underlying survival loop.

### Technology / Logistics
Create Kinetics/Processing/Contraptions, TFMG, New Age, Nuclear, AE2 Networks/Autocrafting/Spatial, Applied Create, Oritech Power/Processing/Mining/Logistics, Create Aeronautics and Technomancy.

Machine automation never grants passive mastery per tick. Progress comes from meaningful configuration, first-use/milestone or validated outcomes.

### Summoning / Occult
Goety Soul/Servants/Necromancy, Malum Spirits/Spirit Knight, Eidolon Ritual/Theurgy, Neo Vitae Blood, Occultism Spirits and the distinct sustain families for weapon leech, spell vampirism, DoT siphon and universal vampirism.

Provider resources remain separate: Soul Energy is not mana, Essentia Vitae is not Soul Energy, Malum spirits are not generic mana, and universal vampirism has lower coefficients/caps than narrow leech specialists.

### Morph / Ecology
Druid Wild Shape, Metamorph Assimilation, Morph Ecology and Beastmaster. Identity 2 is the intended morph backend; RPG Skill Tree owns gates, form categories, mastery and ecological/social rules rather than copying per-entity identity NBT.

### Hybrid classes
Spellblade, Battlemage, Arcane Archer, Paladin, Death Knight, Geomancer, Technomancer, Necromancer, Warlock and Beastmaster hybrids have semantic subtrees that deliberately consume two or more domain/provider identities.

## Attunement
Tree nodes may unlock dedicated Attunement slots. These should be implemented as real Curios-backed storage where possible so equipped items keep native tick/equip/unequip/attribute behavior. The normal Curios inventory is not consumed. Respecs must eject overflow safely and deterministically.

## Validation
`scripts/validate-data.py` validates both legacy progression data and the semantic architecture. Architecture validation rejects:
- duplicate tree IDs;
- unknown progression domains;
- invalid tree types;
- missing/duplicate branches;
- invalid branch order;
- unknown required classes or specializations;
- invalid mastery gates;
- malformed tags/bridges;
- bridges to unknown trees or to the tree itself.

## Save compatibility
Stable namespaced IDs are required for nodes, archetypes, mastery lanes, canonical stats and semantic trees. Renames use aliases/migrations instead of silently deleting player progression.
