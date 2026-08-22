# Unified RPG Skill Tree — Design Specification

**Date:** 2026-08-22  
**Target:** Minecraft 1.21.1 / NeoForge  
**Status:** Design baseline for review  
**Primary UI/engine:** Passive Skill Tree community port for NeoForge 1.21.1

## 1. Product goal

Create one coherent RPG progression system for the modpack instead of stacking several unrelated skill-tree mods. The final experience should feel closer to Path of Exile / Path of Exile 2 than to a small class menu: one visually dense main passive tree with meaningful routes, notables and keystones, plus specialized mastery trees unlocked organically from the main tree.

The project must integrate the actual gameplay systems already present in the pack, especially Iron's Spells 'n Spellbooks, Ars Nouveau and Epic Fight/Battle Arts. The tree must change how those systems behave, not merely add generic +damage nodes.

## 2. Experience principles

### 2.1 Large, readable, attractive tree

The main tree is intentionally large. It should use the Passive Skill Tree visual language: dark background, connected node graph, small nodes, larger notables, distinctive keystones, pan/zoom and enough spatial separation that the player can visually read regions.

The target is not "as many nodes as possible". Density must come from build choices. Repeated filler bonuses are allowed only as connective tissue between meaningful nodes.

### 2.2 Organic specialization

The player is not asked to pick a permanent class at character creation. Specializations emerge from investment.

Example:

`Arcane Initiate -> Spell Power / Mana / Cast Control -> Pyromancy -> Master of Fire`

`Master of Fire` acts as a portal/unlock for a dedicated Fire mastery tree. Similar portals can exist for Ice, Lightning, Blood, Holy, Nature, Ender, Evocation, Eldritch, summoning, weapon families, Ars disciplines and hybrid archetypes.

### 2.3 Use earns mastery

General passive points and specialized mastery progression are separate concepts.

- Main-tree progression represents the character as a whole.
- Specialized mastery XP is earned by using the relevant system.
- Fire casts advance Fire mastery, not Ice mastery.
- Epic Fight sword play advances sword-oriented mastery, not greatsword mastery.
- Ars progression is inferred from glyph/effect/form usage rather than the player merely naming a spell "Fire".

This prevents a player from becoming a specialist without interacting with the specialization.

### 2.4 One canonical attribute per concept

The project must not blindly stack equivalent attributes exposed by multiple mods.

For each gameplay concept we choose one canonical backing mechanism:

- use the owning mod's native attribute when it already exists and is stable;
- use an event/stat modifier when an attribute is unnecessary;
- introduce a project-specific attribute only when no suitable canonical mechanism exists.

The project must document every canonical choice and why it was selected.

## 3. Architecture

The project is divided into six responsibilities.

### 3.1 Passive Tree Engine Layer

Base: the NeoForge 1.21.1 community port of Passive Skill Tree.

Responsibilities:

- retain the Passive Skill Tree rendering and navigation model;
- retain data-driven skill/tree definitions where practical;
- support a much larger custom main tree;
- add project-specific bonus/effect types;
- add tree-portal nodes that unlock/open specialized trees;
- expose enough internal hooks that integration adapters do not need to modify the renderer.

We should prefer extending the port rather than replacing its GUI. Rebuilding the GUI would discard the strongest part of the original mod and create unnecessary maintenance.

### 3.2 Progression Core

Responsibilities:

- player main passive-point state;
- mastery XP and mastery levels per domain;
- specialized-tree point balances;
- unlock requirements;
- respec rules;
- persistence and synchronization;
- anti-double-counting for events that can fire more than once per action.

The data model should identify domains by `ResourceLocation`, not Java enums, so addons/data packs can register additional domains later.

Proposed conceptual IDs:

- `nrst:main`
- `nrst:iron/fire`
- `nrst:iron/ice`
- `nrst:iron/lightning`
- `nrst:ars/projectile`
- `nrst:ars/summoning`
- `nrst:epicfight/sword`
- `nrst:epicfight/greatsword`

The exact registry format is implementation detail, but IDs must remain namespaced and data-friendly.

### 3.3 Integration Adapters

Adapters translate mod-specific events/capabilities into a stable internal event model.

Initial adapters:

- `IronSpellAdapter`
- `ArsNouveauAdapter`
- `EpicFightAdapter`

Later integrations should be separate adapters rather than conditionals scattered through node classes.

Internal events should describe gameplay semantics, for example:

- `MagicCastEvent(domain, spellId, level, manaCost, castType)`
- `GlyphResolvedEvent(form, effect, stats, spellFingerprint)`
- `WeaponActionEvent(category, moveset, skill, damage, result)`

This lets the tree logic operate on semantics rather than depending everywhere on third-party classes.

### 3.4 Effect/Bonus Engine

Nodes need two broad kinds of bonuses.

**Stat effects** modify stable numeric values:

- max mana
- mana regeneration
- spell power
- school spell power
- spell resistance
- cast-time reduction
- cooldown reduction
- attack speed
- base damage
- stamina-like resources when exposed by combat systems

**Behavioral effects** alter mechanics:

- chance to recast/echo a spell;
- extra projectile under defined conditions;
- projectile split/fork;
- conditional amplification;
- improved AOE/duration/acceleration for matching Ars glyphs;
- bonuses after a perfect dodge/parry;
- bonuses based on Epic Fight weapon category;
- execute effects at low enemy health;
- conversions/trade-offs such as damage for mana cost or cast speed;
- hybrid triggers such as melee hit empowering the next spell.

Behavioral effects are the main source of build identity. They must have server-authoritative checks and explicit cooldown/proc guards where necessary.

### 3.5 Data-Driven Tree Definitions

Tree topology, node positions, connections, display text and most simple bonuses should remain data-driven.

Java should implement reusable bonus types and integrations; JSON/data should assemble those building blocks into trees.

This separation is required because the project will eventually contain hundreds of nodes and will need balance/layout iteration without recompiling Java for every small change.

### 3.6 UI Extensions

Retain the Passive Skill Tree presentation. Add only features needed by the unified design:

- portal/linked-tree action from special nodes;
- clear visual states for locked mastery trees;
- mastery XP/level/points display for specialized trees;
- breadcrumb/back-to-main navigation;
- consistent icon language for normal, notable, keystone and portal nodes;
- optional tooltip provenance/debug information in developer mode.

## 4. Iron's Spells 'n Spellbooks integration

Iron's already provides native attributes suitable as canonical magic attributes:

- `max_mana`
- `mana_regen`
- `cooldown_reduction`
- `spell_power`
- `spell_resist`
- `cast_time_reduction`
- `summon_damage`
- `casting_movespeed`
- per-school spell power
- per-school magic resistance

The integration must reuse these instead of inventing duplicate project attributes.

Iron's `AbstractSpell` exposes school type, spell level, mana cost, spell power, cast time and recast behavior, and posts `SpellPreCastEvent` / `SpellOnCastEvent`. Those hooks are sufficient for the first implementation of:

- general magic XP;
- school-specific mastery XP;
- mana-cost modifiers;
- spell-level modifiers;
- conditional proc tracking;
- school-aware bonuses.

### 4.1 Iron's mastery model

A spell cast contributes to:

1. general arcane usage/progression;
2. its actual Iron's school domain;
3. optional behavioral tags detected from spell metadata or registered integration rules.

Example Fire mastery nodes:

- small: +Fire spell power;
- notable: Fire spells cost less mana while target is burning;
- notable: Fire spells gain cast-time reduction after dealing fire spell damage;
- keystone: `Conflagration` — stronger burning interactions with a defensive or mana trade-off;
- behavioral notable: controlled chance for eligible projectile Fire spells to echo/recast, with safeguards against recursive procs.

The adapter should recognize addon spells automatically when they inherit the Iron's spell API and declare a normal school.

## 5. Ars Nouveau integration

Ars must not be modeled as if it were Iron's. Ars spells are compositions of forms, effects and augments.

The Ars API exposes `SpellModifierEvent` before effects resolve and `EffectResolveEvent.Pre/Post` around individual effect resolution. The event context includes caster, spell, current effect, spell context and `SpellStats`.

`SpellStats.Builder` supports direct modification of:

- damage modifier;
- amplification;
- AOE;
- duration;
- acceleration;
- augments/items.

Therefore the initial Ars design is component-aware.

### 5.1 Ars mastery domains

Domains should be based on gameplay behavior, not hardcoded spell names. Initial examples:

- Projectile / delivery-form mastery;
- Amplification;
- Area manipulation;
- Duration/control;
- Mobility;
- Summoning;
- elemental/effect families where a reliable glyph/category identity exists;
- hybrid casting nodes that react to combinations of glyphs.

Example nodes:

- `Projectile Savant`: matching projectile forms gain acceleration;
- `Overcharge`: amplification bonus with increased mana cost or another balancing trade-off;
- `Wide Weave`: AOE bonus for eligible effects;
- `Lingering Script`: duration bonus for eligible effects;
- `Echoed Pattern`: controlled chance for an eligible spell/effect sequence to repeat, guarded against recursion;
- `Focused Formula`: less AOE but more damage/amplification.

Addon glyphs that participate in the same Ars APIs should be discoverable by registry identity and/or configurable tags rather than requiring a source-code patch per addon.

## 6. Epic Fight / Battle Arts integration

Epic Fight 1.21.1 exposes weapon categories/capabilities and an event system that already powers its own passive skills.

Relevant hooks include:

- attack speed modification;
- base attack damage modification;
- damage pre/post;
- dodge;
- stun/apply stun;
- kill;
- combo attack/counter;
- skill cast/consume;
- weapon capability/category data.

The project should use those APIs rather than applying vanilla-only attack modifiers that ignore Epic Fight behavior.

### 6.1 Combat mastery model

Weapon mastery should key primarily off Epic Fight `WeaponCategory` / capability identity. This makes compatibility more resilient when addons register weapons correctly.

Examples:

- Sword region: attack speed, combo-oriented benefits, precision/crit windows;
- Greatsword region: poise/stun/heavy-hit identity;
- Dual wield region: alternating-hit/combo benefits;
- Guard region: guard efficiency, retaliation windows;
- Dodge region: short buffs after successful dodge;
- Skill region: effects triggered by weapon-skill casts/consumption.

Behavioral nodes should avoid replacing Epic Fight's animation/moveset model. They should modify values or react to events unless a specific custom moveset feature is explicitly designed later.

## 7. Main-tree topology

The main tree should be a connected world rather than independent class columns.

Initial macro-regions:

1. **Vitality / Defense** — health, mitigation, resistances, sustain.
2. **Martial** — melee fundamentals, weapon routes, Epic Fight gateways.
3. **Precision / Mobility** — speed, ranged/projectile ideas, dodge, positioning.
4. **Arcane** — mana, generic spell power, cast control, Iron's school gateways.
5. **Scribing / Ars** — Ars composition/glyph manipulation gateways.
6. **Summoning / Companions** — summon damage, survivability, command-oriented mechanics.
7. **Utility / Exploration** — only mechanics that justify passive-tree investment; avoid turning every convenience mod into a skill region.
8. **Hybrid corridors** — spellblade, battlemage, arcane archer, blood warrior and other cross-system builds.

The map should allow multiple entrances to several regions so builds are not forced down a single corridor.

## 8. Specialized trees and portal nodes

A specialized tree is unlocked by a main-tree portal node plus optional mastery requirements.

Example:

`Master of Fire`

Requirements:

- connected investment path in main tree;
- minimum Fire mastery level;
- optional minimum general arcane investment.

On activation, the node unlocks the Fire tree. Clicking/opening the portal should navigate to that tree through the same Passive Skill Tree UI.

Specialized points are earned from relevant mastery progression and cannot automatically be spent in unrelated specialized trees.

This is a deliberate extension beyond simply having several independent Passive Skill Tree presets.

## 9. Balance model

### 9.1 Node classes

- **Small:** connective numeric improvement.
- **Notable:** stronger or conditional effect.
- **Keystone:** build-defining rule/trade-off.
- **Portal:** unlocks/navigates to a specialized tree.

### 9.2 Stacking discipline

Every multiplicative modifier must declare its stacking group. Where upstream mods already apply multipliers, the adapter must document whether our value is additive to the upstream attribute, multiplicative after it, or a conditional event modifier.

Unbounded multiplicative chains are prohibited.

### 9.3 Proc safety

Echo, recast, extra projectile and chained effects require:

- source marker/proc context;
- recursion guard;
- maximum trigger count per originating action;
- server-side authority;
- cooldown/chance caps where appropriate.

## 10. Compatibility strategy

### 10.1 Required vs optional dependencies

The engine should have the smallest viable hard-dependency set. Integration modules should load conditionally when the target mod is present wherever NeoForge architecture allows it cleanly.

The first private build may intentionally hard-require the user's core pack mods if that substantially reduces complexity; this decision is implementation-level and can be relaxed later.

### 10.2 Addon compatibility

Prefer API/registry discovery:

- Iron's: spell + declared school;
- Ars: registered spell parts/effects/forms and events;
- Epic Fight: weapon categories/capabilities/skills/events.

Use explicit compatibility tables only for addons that do not expose enough semantic information.

## 11. Persistence and migration

Player progression is valuable save data. All custom persistent structures must include a data version.

Migrations must preserve:

- allocated main nodes;
- unlocked mastery trees;
- mastery XP/levels;
- specialized points;
- allocated specialized nodes.

Unknown/removed node IDs should not corrupt the save. They should be reported and handled by an explicit migration/refund policy.

## 12. Testing requirements

Minimum automated/testable coverage before calling an integration stable:

- node allocation prerequisites;
- portal unlock requirements;
- no spending specialized points in the wrong tree;
- persistence round trip;
- migration behavior;
- Iron school XP routed only to the matching school;
- Ars modifier applied only to matching glyph/effect conditions;
- Epic Fight category bonus applied only to matching weapon category;
- recursive spell proc guard;
- server/client synchronization of points and unlock state.

Manual integration testing must include a real 1.21.1 NeoForge instance with the target mods.

## 13. Initial non-goals

- Replacing Iron's spellcasting UI.
- Replacing Ars spellbook/glyph construction UI.
- Replacing Epic Fight animations or core combat engine.
- Giving every mod in the pack its own region.
- Implementing hundreds of final-balance nodes before the three core adapters are proven.
- Publishing/distributing the combined private project.

## 14. First implementation slice after design approval

The first vertical slice should prove the architecture with a small test tree, not build the full final tree immediately.

It should demonstrate:

- Passive Skill Tree UI running on NeoForge 1.21.1;
- a custom main tree;
- one portal node;
- one Iron Fire specialized tree;
- school-specific XP from real Iron casts;
- one Ars modifier node using official Ars events;
- one Epic Fight weapon-category node using official Epic Fight events;
- persistence and synchronization.

Once this slice works, tree content can scale without risking hundreds of nodes on an unstable foundation.
