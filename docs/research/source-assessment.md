# Source Project Assessment

**Date:** 2026-08-22  
**Purpose:** decide what to retain, port, reimplement or discard from the requested source projects.

## Summary

The unified project should not bundle seven independent progression systems. It should use Passive Skill Tree as the engine/UI and selectively absorb mechanics, layouts and progression ideas from the other projects.

## 1. Passive Skill Tree — engine/UI base

Official project: https://github.com/Daripher/Passive-Skill-Tree  
CurseForge: https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree  
NeoForge 1.21.1 community port: https://github.com/themarneilx/Passive-Skill-Tree/tree/1.21.1-neoforge

### Current state

- Official Passive Skill Tree is still actively maintained on Forge 1.20.1.
- Official latest observed release: `0.7.6e` (2026-07-22).
- The NeoForge 1.21.1 community branch examined is based on `0.7.6c`.

### Decision

**Use as the code/GUI foundation, but first forward-port the official fixes from 0.7.6c to 0.7.6e where applicable.**

Do not build major custom features on top of the community port until that delta is understood. Otherwise later bug fixes become unnecessarily hard to reconcile.

### What to retain

- skill-tree rendering and navigation;
- data-driven skill and tree definitions;
- editor concepts;
- node connections and requirements;
- existing bonus infrastructure where it maps cleanly.

### What to extend

- one enormous custom main tree;
- portal nodes to specialized trees;
- per-domain mastery XP/points;
- custom semantic integrations/effects;
- improved in-inventory entry point;
- project-specific persistence/migration data.

## 2. Waifing — topology/content donor, not drop-in dependency

CurseForge: https://www.curseforge.com/minecraft/data-packs/waifing-passive-skill-tree

### Verified behavior

Waifing modifies the older large Passive Skill Tree and adds/changes progression for Iron's Spells 'n Spellbooks, Create and Apotheosis. It targets Passive Skill Tree `0.6.14a` (possibly `0.6.14b`) and explicitly breaks on versions `>= 0.7`.

It replaces several default class identities, including:

- Miner -> Berserk
- Hunter -> Mage
- Cook -> Merchant

Its Mage path adds Iron-oriented magic damage/resistance, mana and regeneration. Other regions alter Apotheosis gem/affix behavior and Create progression.

### Decision

**Do not attempt to load Waifing directly.**

Use it for:

- large-tree spatial/layout inspiration;
- content ideas worth preserving;
- examples of integrating Iron's/Create/Apotheosis into Passive;
- potentially migrate selected old JSON definitions after comparing 0.6.14 vs 0.7.x schemas.

The old class names/layout should not dictate the final tree. The target is an organic PoE-style world, not six isolated Minecraft professions.

## 3. Magic Schools Skill Trees — keep mastery routing, replace balance/tree implementation

CurseForge: https://www.curseforge.com/minecraft/mc-mods/addon-for-irons-spells-n-spellbooks-unofficial

### Verified behavior

The addon gives every Iron school its own tree. A cast contributes XP to the main Magic tree and only to its matching school tree; e.g. Fireball advances general Magic + Fire but not Ice.

The published variants provide very large total school-power completion bonuses (normal and OP variants).

### Decision

**Absorb the XP-routing idea; do not inherit its final percentage balance.**

This is the correct conceptual model for our Iron specialized trees:

`cast -> general arcane progression + matching Iron school mastery`

Our final school trees should add behavior and trade-offs rather than being almost entirely school-power accumulation.

## 4. Iron's Dynamic Skill Trees — progression pacing and stage ideas

CurseForge: https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks-dynamic-skill-trees

### Verified behavior

The project currently provides four Pufferfish-based dynamic trees. Its published progression is:

`Magic I -> Primary School -> Secondary School -> Magic II`

Primary and Secondary school selections are mutually exclusive. XP is earned by casting; published logic weights XP by spell rarity/mana cost and gives extra value to Eldritch spells.

Published completion values include generic spell power/resist, cooldown reduction, max mana, mana regen and primary/secondary-school power.

### Decision

**Use its pacing formulas and node ideas as reference, not its rigid Primary/Secondary locking model.**

The unified tree should permit organic multi-school investment, with strong specialization opportunity costs created by distance/points/keystones rather than a hard class-style selection unless a specific keystone intentionally imposes exclusivity.

Useful ideas to preserve/rework:

- rarity/mana-cost-aware mastery XP;
- late-game Eldritch weighting;
- distinct general-magic and school-specific progression;
- reset/respec experience that does not require operator intervention.

## 5. Skills Mastery Reimagined — unique-node/trade-off donor

CurseForge: https://www.curseforge.com/minecraft/mc-mods/skills-mastery-reimagined

### Verified behavior

Version 1.2.1 for 1.21.1 is effectively a bundled Pufferfish datapack. Published design has:

- 71 total nodes;
- 36 unique nodes;
- a 36-node unlock cap;
- unique nodes with negative effects/trade-offs;
- many specialized attributes;
- XP from more than mob kills: ore mining, fishing, enchanting and high-value boss kills.

### Decision

**Treat as a design/content donor, not a second engine.**

Most valuable ideas:

- negative-effect keystones;
- unique node identity;
- non-combat routes for main progression;
- boss/activity-weighted XP.

Do not import all exposed attributes just because nodes exist for them. Every attribute must pass the canonical-attribute review.

## 6. Scion — macro-region donor

Source: https://github.com/Meteta/MC_scion_skilltree  
CurseForge: https://www.curseforge.com/minecraft/data-packs/scion-skilltree

### Verified behavior

Scion is a Pufferfish skill-tree datapack for NeoForge 1.21.1, loosely inspired by D&D and Path of Exile. Its main tree is divided into:

- Dexterity — movement/ranged;
- Strength — melee;
- Constitution — absorption/healing;
- Charisma — villagers/summons/tames;
- Wisdom — farming/fishing/mining/harvesting;
- Intelligence — Iron's magic.

Its individual nodes can rely on Pufferfish Attributes, Additional Attributes, Unofficial Additions, Apothic Attributes, Artifacts, Pehkui, Iron's and other mods.

### Decision

**Use the broad region taxonomy as inspiration but do not inherit its dependency graph wholesale.**

The unified tree can use similar macro-regions while choosing one canonical backing mechanism per stat.

Scion's separate race-choice tree is outside the initial scope.

## 7. Passive Skill Tree Additions — fold feature into core

Source: https://github.com/thrasos-dev/passiveskilltreeadditions-mod  
CurseForge: https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree-additions

### Verified behavior

The addon mainly adds an inventory-screen button that opens Passive Skill Tree, with configurable X/Y position and tooltip.

### Decision

**Reimplement/fold this convenience directly into the unified project.**

Keeping a separate addon only for an entry button would add another dependency for negligible architectural benefit.

## 8. Resulting ownership model

| Source | Final role |
|---|---|
| Passive Skill Tree | Base code / renderer / data engine |
| Waifing | Layout + selected content donor |
| Magic Schools | Iron school XP-routing donor |
| Dynamic Skill Trees | Magic pacing + stage/content donor |
| Skills Mastery Reimagined | Keystone/trade-off + XP-source donor |
| Scion | Macro-region + broad build donor |
| Passive Skill Tree Additions | Convenience feature folded into core |

## 9. Immediate technical consequences

Before building final tree content:

1. Diff official Passive `0.7.6c -> 0.7.6e` and apply relevant fixes to the NeoForge 1.21.1 port.
2. Diff Passive `0.6.14a -> 0.7.x` data schemas to understand whether Waifing content can be converted mechanically.
3. Prove separate mastery currencies/trees without breaking Passive persistence.
4. Prove Iron, Ars and Epic Fight adapters on a tiny vertical-slice tree.
5. Only then scale layout/content toward the final huge PoE-like graph.
