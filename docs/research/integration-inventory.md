# Integration Inventory

**Snapshot target:** user's NeoForge 1.21.1 modpack, August 2026.  
**Purpose:** identify which systems should be integrated directly, which should provide design ideas/content, and where attributes overlap.

## 1. Core systems

| System | Confirmed version/context | Role in this project |
|---|---|---|
| Passive Skill Tree | NeoForge 1.21.1 community port based on 0.7.6c | **Engine/UI base** |
| Iron's Spells 'n Spellbooks | 1.21.1-3.16.3, `irons_spellbooks` | **Primary magic integration** |
| Ars Nouveau | 5.13.0, `ars_nouveau` | **Primary compositional-magic integration** |
| Epic Fight | 1.21.1 branch/API family; exact pack build to re-verify against next current modlist | **Primary combat integration** |
| Battle Arts API | 21.17.7, `battle_arts_api` | Combat/addon ecosystem context |
| Pufferfish's Skills | 0.18.3, `puffish_skills` | Source-system/design compatibility; avoid retaining as a second visible tree unless required |
| Pufferfish's Attributes | 0.8.3, `puffish_attributes` | Candidate attribute source only where no owning-mod/native equivalent is better |
| Apothic Attributes | 2.10.1, `apothic_attributes` | Candidate generic attribute source; must deduplicate against other systems |

## 2. User-requested source projects to merge conceptually

These are not assumed to remain installed independently in the final private build.

1. Addon for Iron's Spells 'n Spellbooks Unofficial / Magic Schools Skill Trees  
   https://www.curseforge.com/minecraft/mc-mods/addon-for-irons-spells-n-spellbooks-unofficial
2. Iron's Spells 'n Spellbooks Dynamic Skill Trees  
   https://www.curseforge.com/minecraft/mc-mods/irons-spells-n-spellbooks-dynamic-skill-trees
3. Skills Mastery Reimagined  
   https://www.curseforge.com/minecraft/mc-mods/skills-mastery-reimagined
4. Scion: Races and Skill Tree  
   https://www.curseforge.com/minecraft/data-packs/scion-skilltree
5. Waifing Passive Skill Tree  
   https://www.curseforge.com/minecraft/data-packs/waifing-passive-skill-tree
6. Passive Skill Tree Additions  
   https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree-additions
7. Passive Skill Tree  
   https://www.curseforge.com/minecraft/mc-mods/passive-skill-tree

### Planned use

| Source | Planned extraction |
|---|---|
| Passive Skill Tree | renderer, navigation model, data-driven skill/tree engine, editor concepts |
| Waifing | large-tree layout philosophy, dense PoE-like topology, integration ideas for Iron's/Create/Apotheosis |
| Dynamic Skill Trees | progression from general magic into school specialization; useful node/effect ideas |
| Unofficial/Magic Schools | usage-based school mastery: casting a school advances that school |
| Skills Mastery Reimagined | mastery/progression ideas and useful effects after deduplication |
| Scion | broad stat-region ideas and cross-system passive concepts; do not inherit every dependency blindly |
| Passive Skill Tree Additions | convenience/UI entry-point ideas; likely fold directly into unified mod |

## 3. Iron's current pack context

Confirmed/recent entries:

- Iron's Spells 'n Spellbooks — 1.21.1-3.16.3 — `irons_spellbooks`
- Iron's Spells 'n Spellbooks Dynamic Skill Tree — 1.1.0 — `irons_spells_dynamic_skilltree`
- Iron's Spells: Incantation — 0.10.3 — `voicespells`
- Iron's Lib — 1.21.1-2.1.0 — `irons_lib`
- Iron's Spells x Sable Compat — 1.0.4 — `ironssablecompat`
- Ironsable — 1.2.0 — `ironsable`
- ironsattributes — 1.2.0 — `ironsattributes`
- Paladin Spells - Iron's Spells 'n Spellbooks Addon — 1.21.1-1.1.1 (seen in recent pack context)
- Pufferfish's Unofficial Additions — 2.2.8 (seen in recent pack context)
- Reliquified Iron's Spells 'n Spellbooks — 0.2.7 (seen in recent pack context)

The full current Iron-addon inventory will be refreshed from the next exact modlist/log snapshot before adapter-specific compatibility tables are finalized.

### Native Iron attributes — canonical candidates

From the Iron's 1.21 codebase:

- `irons_spellbooks:max_mana`
- `irons_spellbooks:mana_regen`
- `irons_spellbooks:cooldown_reduction`
- `irons_spellbooks:spell_power`
- `irons_spellbooks:spell_resist`
- `irons_spellbooks:cast_time_reduction`
- `irons_spellbooks:summon_damage`
- `irons_spellbooks:casting_movespeed`
- school-specific spell power
- school-specific magic resistance

**Decision:** reuse these for Iron-specific magic instead of creating parallel attributes.

### Iron API hooks already verified

`AbstractSpell` exposes or routes:

- spell ID/resource;
- school type;
- level;
- mana cost;
- spell power;
- cast type/time;
- recast behavior;
- `SpellPreCastEvent`;
- `SpellOnCastEvent`;
- `ModifySpellLevelEvent`.

This is enough for the first school-mastery vertical slice without patching each spell class.

## 4. Ars Nouveau current pack context

Confirmed current entries:

- Ars Nouveau — 5.13.0 — `ars_nouveau`
- Ars 'n' Spells — 3.0.2 — `ars_n_spells`
- Ars Additions — 1.21.1-21.3.0 — `ars_additions`
- Ars Controle — 1.21.1-1.6.15 — `ars_controle`
- Ars Creo — 5.4.0 — `ars_creo`
- Ars Elemancy — 1.18.3 — `ars_elemancy`
- Ars Elemental — 0.7.10.1 — `ars_elemental`
- Ars Nouveau's Flavors & Delight — 2.2.2 — `arsdelight`
- Ars Nouveau: Two-Way Portals — 2.0.0 — `ars_two_way_portals`
- Ars Polymorphia — 1.0.3 — `ars_polymorphia`
- Ars Sable — 1.1.2 — `ars_sable`
- Ars Sophisticated Compatibility — 0.3.0 — `arssophisticatedcompat`
- Ars Technica — 2.7.6 — `ars_technica`
- Ars Zero — 2.0.2 — `ars_zero`
- Reliquified Ars Nouveau — 0.8.1 (seen in recent pack context)

### Ars API hooks already verified

`SpellModifierEvent` provides a `SpellStats.Builder` before effect resolution.

`EffectResolveEvent.Pre/Post` exposes:

- caster;
- full `Spell`;
- current effect/glyph;
- `SpellContext`;
- `SpellStats`;
- resolver.

`SpellStats.Builder` can modify:

- damage;
- amplification;
- AOE;
- duration;
- acceleration;
- augments/modifier items.

**Decision:** make Ars progression component-aware. Do not map Ars to Iron-style schools unless an addon provides a reliable semantic category that explicitly warrants it.

### Automatic addon compatibility target

If an Ars addon registers normal spell parts/effects/forms and they flow through these events, the adapter should see them automatically. Explicit addon tables are a fallback, not the default.

## 5. Epic Fight / Battle Arts current pack context

Confirmed/recent entries include:

- Battle Arts API — 21.17.7 — `battle_arts_api`
- Ice and Fire CE x Epic Fight Armor Compat — 1.0.0 — `iceandfire_epicfight_armor_compat`
- Punchy Epic Fight Compat — 1.0.0 (seen in recent pack context)

The base Epic Fight build and the complete Battle Arts compat set must be refreshed from the next exact current modlist snapshot before final compatibility declarations.

### Epic Fight API hooks already verified on branch `1.21.1`

`EpicFightEventHooks` includes:

- `MODIFY_ATTACK_SPEED`
- `MODIFY_ATTACK_DAMAGE`
- `DELIVER_DAMAGE_PRE/POST`
- `TAKE_DAMAGE_PRE/POST`
- `ON_DODGE`
- `KILL_ENTITY`
- `APPLY_STUN` / `ON_STUNNED`
- `COMBO_ATTACK`
- `MODIFY_COMBO_COUNTER`
- `CAST_SKILL`
- `CONSUME_SKILL`
- weapon capability/category registries

The built-in `SwordmasterSkill` demonstrates the intended pattern: inspect Epic Fight `CapabilityItem` / `WeaponCategory`, then modify attack behavior through Epic Fight's own event hooks.

**Decision:** weapon mastery keys off Epic Fight capabilities/categories where available, not hardcoded item IDs.

## 6. Attribute deduplication policy

Before adding a node, classify the backing mechanic:

1. **Owning-mod native attribute** — preferred for mechanics controlled by that mod.
2. **Stable generic attribute already in pack** — use if semantics match exactly.
3. **Event-time modifier** — preferred for conditional/behavioral effects.
4. **New project attribute** — last resort.

### Examples

| Concept | Canonical direction |
|---|---|
| Iron spell power | Iron's `spell_power` |
| Iron Fire power | Iron's Fire school power |
| Iron max mana | Iron's `max_mana` |
| Iron cast-time reduction | Iron's `cast_time_reduction` |
| Ars amplification | Ars `SpellStats.Builder` event modifier |
| Ars AOE | Ars `SpellStats.Builder` event modifier |
| Ars duration | Ars `SpellStats.Builder` event modifier |
| Epic Fight weapon attack speed | Epic Fight `MODIFY_ATTACK_SPEED` |
| Epic Fight weapon base damage | Epic Fight `MODIFY_ATTACK_DAMAGE` |
| Generic health/armor/etc. | evaluate vanilla vs Apothic/Pufferfish source before selecting |

## 7. First proof nodes

These are test nodes for architecture, not final balance.

### Main tree

- `Arcane Initiate` — small general magic gateway.
- `Pyromancy` — Fire-route notable.
- `Master of Fire` — portal node; requires Fire mastery threshold and opens Fire specialized tree.
- `Combat Discipline` — martial gateway.
- `Sword Discipline` — Epic Fight sword-category test node.
- `Scribing Initiate` — Ars gateway.
- `Projectile Savant` — Ars projectile/form test node.

### Fire specialized tree

- Fire spell-power small nodes.
- Mana-efficiency notable for Fire.
- Conditional cast-time notable after valid Fire use.
- One guarded echo/recast prototype for eligible Fire projectile spells.

### Ars proof effect

One node modifies `SpellStats.Builder` only when a configured/recognized projectile form participates in the resolving spell.

### Epic Fight proof effect

One node modifies attack speed or base damage only while the current capability belongs to the configured sword-category set.

## 8. Research still required before full tree content

- exact latest complete Iron addon list from the active instance;
- exact latest complete Epic Fight/Battle Arts addon list;
- source/API inspection of each requested skill-tree source project;
- Passive 0.6.14a vs 0.7.x data-format comparison;
- Waifing v5 data migration feasibility;
- Passive community port persistence/point model and easiest insertion point for separate mastery currencies;
- precise stacking interactions with Apothic Attributes/Pufferfish Attributes/ironsattributes;
- balance baselines for major spells, glyph combinations and Epic Fight weapon categories.
