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

## Main tree
The first blueprint budgets 420 nodes on a 4096x4096 canvas. Regions: Martial, Agility, Vitality, Arcane, Engineering, Survival and Summoning. Hybrid corridors include Spellblade, Battlemage, Arcane Archer, Technomancer, Artillerist, Beastmaster and Warlock.

## Specialized trees
Iron's Spellbooks: Fire, Ice, Lightning, Holy, Ender, Blood, Evocation, Nature and Eldritch.
Ars Nouveau: delivery/projectile, amplification, area/duration/control and summoning-oriented masteries, derived from the actual spell composition.
Epic Fight: weapon-category and skill/combat masteries driven by Epic Fight's own capability/event model.
Create: Engineering with Kinetics, Automation/Logistics, Artillery, Aeronautics/Propulsion and magic-tech bridge paths.

## Attunement
Tree nodes may unlock dedicated Attunement slots. These should be implemented as real Curios-backed storage where possible so equipped items keep native tick/equip/unequip/attribute behavior. The normal Curios inventory is not consumed. Respecs must eject overflow safely and deterministically.

## Save compatibility
Stable namespaced IDs are required for nodes, archetypes, mastery lanes and canonical stats. Renames use aliases/migrations instead of silently deleting player progression.
