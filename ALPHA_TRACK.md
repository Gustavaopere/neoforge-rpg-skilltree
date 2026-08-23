# Alpha integration track

This project remains in **Alpha** until the integrated modpack behavior is functionally complete. **Beta starts only after a functional JAR has been published in GitHub Releases** and the work has shifted primarily to correctness, balance, compatibility hardening, UX and release polish.

## Versioning rule
- Each major integration gets its own Alpha checkpoint.
- Fixes or extensions to an already completed integration use a decimal revision of that Alpha when practical (`1.1`, `1.2`, `2.1`, etc.).
- The Gradle `mod_version` tracks the latest project checkpoint; individual integration maturity is recorded below.
- A green CI build is necessary but does not by itself promote the project to Beta.
- The three current Notion guides (Magic, Technology, Gameplay/Systems) are the source of truth for which pack mods are currently installed/removed and what each integration must account for.

## Current integration map
| Track | Scope | Current state |
|---|---|---|
| Alpha 1 | Iron's Spells 'n Spellbooks | implemented; provider mastery/class correction included in 1.1 work |
| Alpha 1.2 | Current Iron addon ecosystem: Acolyte, Apprentice's Codex, Crystal Chronicles, Legendary Spellbooks, Somake Spells, ShadowsZ, Vampirism/Iron bridge and other provider extensions | planned compatibility revision; base Iron schools already inherit automatically where API-compatible |
| Alpha 2 | Ars Nouveau | implemented; composition/provider mastery correction included in 2.1 work |
| Alpha 2.2 | Ars Morph and new Ars bridge behavior | planned compatibility revision; must avoid duplicate rewards across bridge callbacks |
| Alpha 3 | Epic Fight | implemented runtime adapter; project previously reached `alpha.3.1-dev` after Iron/Ars identity corrections |
| Alpha 4 | Goety + Goety Iron + Goety Cataclysm | implementation in progress; real spell/Soul mastery feed added at `alpha.4-dev` pending CI verification |
| Alpha 5 | Malum + Gaze + Vestis | planned |
| Alpha 6 | Eidolon: Repraised | planned |
| Alpha 7 | Neo Vitae + BloodMagic AE2 Addition | planned |
| Alpha 8 | Create core + major Create industrial lanes | planned |
| Alpha 9 | Applied Energistics 2 | planned |
| Alpha 10 | Oritech | planned |
| Alpha 11 | Sable + Create Aeronautics vehicle/physics progression | planned |
| Alpha 12 | TerraFirmaCraft survival/mining/metallurgy | planned |
| Alpha 13 | Relics / Artifacts / Apotheosis / attunement consolidation | planned |
| Alpha 14 | Vampirism + Bloodlines + Vampiric Ageing + Werewolves | planned as supernatural-state progression; native transformation trees remain authoritative mechanics |
| Alpha 15 | Identity 2 + Ars Morph + Metamorph/Druid runtime integration | foundations already exist; full action/state integration planned |
| Alpha 16 | World difficulty/boss bridges: Legendary Monsters, Improved Mobs and related reward attribution | planned; should consume unified level/reward logic rather than create another player tree |

The numbering may gain point revisions when a prior integration needs correction or new addons extend that provider without forcing unrelated systems into the same checkpoint.

## Updated pack architecture — 23 Aug 2026
The current Notion inventory establishes these major progression ecosystems:

### Magic and supernatural systems
- Iron's Spells 'n Spellbooks 3.16.3 and its large school/addon ecosystem.
- Newly added Iron-side content includes Acolyte, Apprentice's Codex, Crystal Chronicles, Legendary Spellbooks, Somake Spells and ShadowsZ. These are not automatically new top-level classes: school/content addons inherit the Iron provider path where possible, while systems such as ShadowsZ may add their own mastery/state bridge.
- Ars Nouveau 5.13.0 with Elemental/Elemancy, Creo, Technica, Controle, Not Enough Glyphs, Ars Zero, Starbunclemania, Ars Morph and cross-provider bridges.
- Goety 3.1.4, including Goety Iron and Goety Cataclysm. Goety feeds OCCULT/SUMMONING-style progression through real Soul-backed spell use rather than being treated as another generic Arcane provider.
- Malum 1.8.2 with Gaze and Vestis.
- Eidolon: Repraised 0.5.0.2.
- Neo Vitae 1.1.13 as a separate blood-magic progression.
- Vampirism 1.10.12 with Bloodlines, Vampiric Ageing, Werewolves, Integrations and the Iron's Blood/Holy bridge. These are supernatural character states with native progression, not merely spell schools.
- Identity 2 + Ars Morph supports the existing Metamorph/Druid direction and should expose transformation state to the unified tree without replacing Identity's own morph rules.

### Technology
- Create 6.0.10 as the mechanical backbone.
- Applied Energistics 2 19.2.17 for digital storage/autocrafting.
- Oritech 1.2.10 for powered industrial machines.
- Sable 2.0.5 + Create Aeronautics 1.3.1 for physical vehicles/sublevels.
- TFMG, Create: New Age, Create Nuclear, Crafts & Additions, Diesel Generators, Metallurgy/Metalwork and other Create branches as specialization lanes rather than separate top-level character classes by default.
- Applied Create creates a direct Create↔AE2 hybrid path.

### Gameplay/progression systems requiring coexistence policy
- Pufferfish's Skills is a framework and may remain as a dependency/API surface.
- Skills Mastery Reimagined is a standalone ready-made skill tree and therefore competes with RPG Skill Tree for persistent character-tree progression.
- Iron's Spells Dynamic Skill Tree and Iron's Spells Magic Schools also duplicate progression that RPG Skill Tree is explicitly implementing.
- Player Stats duplicates permanent attribute growth and must not silently stack with equivalent canonical attributes.
- Professions is complementary if treated as activity mastery/input rather than as a mutually exclusive class system.
- Improved Mobs can later consume the unified character level/difficulty model rather than define player progression itself.
- Legendary Monsters is primarily a boss/content source; its kills should feed the unified boss reward system, while Legendary Spellbooks belongs to the Iron compatibility layer.

## Cross-provider rule
Bridge mods must not grant the same underlying action twice. Examples include Ars 'n' Spells, Goety Iron, Ars Creo/Technica, Create: Wizardry, Applied Create, Vampirism Iron's Spells Compatibility and Ars Morph. Runtime integrations should normalize one user action into one origin and then distribute mastery to all legitimately involved lanes. Duplicate callbacks from bridge mods must be deduplicated before rewards are persisted.

## Native-progression coexistence rule
When another mod already owns a meaningful progression system (for example Vampirism/Bloodlines/Werewolves, ShadowsZ shadow levels, Identity morph unlocks or Professions), RPG Skill Tree should read that state/action as requirements, mastery or class identity where useful. It should not recreate the same native tree unless there is a deliberate migration plan.
