# Alpha integration track

This project remains in **Alpha** until the integrated modpack behavior is functionally complete. **Beta starts only after a functional JAR has been published in GitHub Releases** and the work has shifted primarily to correctness, balance, compatibility hardening, UX and release polish.

## Versioning rule
- Each major integration gets its own Alpha checkpoint.
- Fixes or extensions to an already completed integration use a decimal revision of that provider track when practical (`1.1`, `1.2`, `2.1`, etc.).
- The Gradle `mod_version` tracks the latest chronological project checkpoint; provider maturity is recorded separately below. Retrospective Alpha 1.x/2.x/3.x/4.x work after Alpha 5 therefore ships as chronological `alpha.5.x-dev` checkpoints without pretending the project went backwards.
- A green CI build is necessary but does not by itself promote the project to Beta.
- The three current Notion guides (Magic, Technology, Gameplay/Systems) are the source of truth for which pack mods are currently installed/removed and what each integration must account for.

## Integration depth rule
The Notion guides identify the active pack and provide orientation, but implementation decisions must be grounded in deeper research of each provider: official project pages/wiki/changelogs plus source/API where available. An integration is not considered complete merely because it awards generic mastery or flat damage/resistance. Prefer real provider mechanics: resource economies, spell composition, rituals, summons, transformations, equipment states, automation, native progression, tradeoffs and persistent commitments.

After Alpha 5, the already landed providers are explicitly scheduled for depth revisions before new magic providers continue:
- Alpha 1.x: Iron's + its current addon/bridge ecosystem;
- Alpha 2.x: Ars Nouveau + mana/glyph/source/ritual/familiar/automation/morph bridges;
- Alpha 3.x: Epic Fight stamina/guard/dodge/skills/combo semantics beyond weapon-hit mastery;
- Alpha 4.x: Goety Soul economy, servants/commanding and bridge deduplication beyond cast mastery.

## Current integration map
| Track | Scope | Current state |
|---|---|---|
| Alpha 1 | Iron's Spells 'n Spellbooks | live provider mastery + Arcane gate implemented |
| Alpha 1.1 | Iron learned/catalogued-magic depth pass | **verified green** in project `alpha.5.1.1-dev`: permanent inscription uses real provider/school practice, tier 3+ requires emergent Mage, mastery scales from bounded real mana expenditure; core/build/JAR/server smoke passed |
| Alpha 1.2 | Current Iron addon ecosystem: Acolyte, Apprentice's Codex, Crystal Chronicles, Legendary Spellbooks, Somake Spells, ShadowsZ, Vampirism/Iron bridge and other provider extensions | API-compatible school/spell addons inherit 1.1 rules automatically; bespoke native-progression addons remain scheduled |
| Alpha 2 | Ars Nouveau | live composition mastery + Arcane gate implemented |
| Alpha 2.2 | Ars native-mana/familiar depth pass | **verified green** in project `alpha.5.2-dev`: Arcane tree + Sorcerer affect Ars native max mana/regen, SUMMONING entry gates familiar binding, cast mastery scales from real mana cost; core/build/JAR/server smoke passed |
| Alpha 2.3 | Ars glyph/source/ritual/automation + Ars Morph/new bridges | planned; glyph learning has no dedicated public unlock event in 5.13.0, so no fragile generic right-click pseudo-gate is used |
| Alpha 3 | Epic Fight | live weapon-category adapter implemented |
| Alpha 3.2 | Epic Fight native stamina/guard/dodge/skill depth pass | **verified green** in project `alpha.5.3-dev`: MARTIAL/AGILITY nodes modify Epic Fight stamina attributes and actual stamina costs, skill-resource use feeds dedicated mastery lanes, successful `ON_DODGE` feeds dodge + unified agility practice, proc/creative/fake-player farming rejected; core/build/JAR/server smoke passed |
| Alpha 3.3 | Epic Fight combo/skillbook/passive-skill and current addon compatibility | planned; must preserve Epic Fight's own learnable skill system rather than clone it |
| Alpha 4 | Goety + Goety Iron + Goety Cataclysm | Soul-backed spell mastery verified green in CI |
| Alpha 4.1 | Goety Soul economy + servant outcome depth pass | **verified green** in project `alpha.5.4.1-dev`: OCCULT/Warlock modify native Soul gain/cast cost, Necromancer gets summon-specific efficiency, Soul-backed spell mastery scales with actual adjusted cost, hostile servant kills feed servants/SUMMONING and owner class lanes; core/build/JAR/server smoke passed |
| Alpha 4.2 | Goety commanding/servant orders + Goety Iron/Cataclysm bridge dedupe | **verified green** in project `alpha.5.4.2-dev`: Command/Order Focus entity and block orders are observed as intent and rewarded only after Goety's own servant command target/position/tick confirms a real state change; selection, invalid/no-op clicks, derived callbacks, creative and fake players do not award mastery. Iron's player adapter only accepts `ServerPlayer` SPELLBOOK/SCROLL casts, so Goety Iron servant casts do not double-credit player Iron mastery. Exact catalog passives that consume target correlation remain separate effect-mapping work |
| Alpha 5 | Malum + Gaze + Vestis | **verified green**: Spirit Reaping/collection, dynamic spirit affinities and native Malum attribute/tree integration; build, JAR verification and dedicated-server smoke passed |
| Alpha 5.x provider follow-up | Malum Spirit Rites/Locus/Anchors and richer Gaze state hooks | planned only where a stable completion/state hook is available; no fragile mixin solely for XP |
| Alpha 6 | Eidolon: Repraised | next active provider track after completion of the retrospective Goety depth pass |
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

## Updated pack architecture — 23 Aug 2026

### Magic and supernatural systems
- Iron's Spells 'n Spellbooks 3.16.3 and its large school/addon ecosystem. Mage is learned/catalogued magic: scroll practice and school mastery lead into permanent spellbook study rather than being only a display identity.
- Iron-side content includes Acolyte, Apprentice's Codex, Crystal Chronicles, Legendary Spellbooks, Somake Spells and ShadowsZ. School/content addons inherit the Iron provider path where possible; native systems such as ShadowsZ may add their own state bridge.
- Ars Nouveau 5.13.0 with Elemental/Elemancy, Creo, Technica, Controle, Not Enough Glyphs, Ars Zero, Starbunclemania, Ars Morph and cross-provider bridges. Sorcerer is intrinsic/modular magic: its identity now changes Ars's own mana economy while composition mastery remains glyph-semantic.
- Goety 3.1.4, including Goety Iron and Goety Cataclysm. Goety feeds OCCULT/SUMMONING-style progression through real Soul-backed use. The unified tree modifies Goety's own Soul Energy change event rather than adding a second mana pool, and servant progress is attributed through Goety ownership state. Command/Order Focus practice is credited only after native servant state confirms the command.
- Malum 1.8.2 with Gaze and Vestis. Malum is Spirit Arcana: Soul Reaping, dynamic spirit affinities, Soul Ward/Arcane Resonance and Geas are first-class mechanics. Gaze extends those registries/systems; Vestis is cosmetic and receives no artificial mastery lane.
- Eidolon: Repraised 0.5.0.2.
- Neo Vitae 1.1.13 as a separate blood-magic progression.
- Vampirism 1.10.12 with Bloodlines, Vampiric Ageing, Werewolves, Integrations and the Iron's Blood/Holy bridge. These are supernatural character states with native progression, not merely spell schools.
- Identity 2 + Ars Morph supports the existing Metamorph/Druid direction and should expose transformation state to the unified tree without replacing Identity's own morph rules.

### Technology
- Create 6.0.10 as the mechanical backbone.
- Applied Energistics 2 19.2.17 for digital storage/autocrafting.
- Oritech 1.2.10 for powered industrial machines.
- Sable 2.0.5 + Create Aeronautics 1.3.1 for physical vehicles/sublevels.
- TFMG, Create: New Age, Create Nuclear, Crafts & Additions, Diesel Generators, Metallurgy/Metalwork and other Create branches are specialization lanes rather than separate top-level character classes by default.
- Applied Create creates a direct Create↔AE2 hybrid path.

### Gameplay/progression coexistence policy
- Epic Fight 21.17.3.1 is treated as the authoritative combat-state provider. RPG Skill Tree may modify its exposed stamina/impact/etc. attributes and consume its public hooks, but it should not replace Epic Fight's skill slots, skillbooks, combo engine or animation state machine.
- Pufferfish's Skills is a framework and may remain as a dependency/API surface.
- Skills Mastery Reimagined is a standalone ready-made skill tree and competes with RPG Skill Tree for persistent character-tree progression.
- Iron's Spells Dynamic Skill Tree and Iron's Spells Magic Schools also duplicate progression RPG Skill Tree is explicitly implementing.
- Player Stats duplicates permanent attribute growth and must not silently stack with equivalent canonical attributes.
- Professions is complementary if treated as activity mastery/input rather than as a mutually exclusive class system.
- Improved Mobs can later consume the unified character level/difficulty model rather than define player progression itself.
- Legendary Monsters is primarily a boss/content source; its kills should feed the unified boss reward system, while Legendary Spellbooks belongs to the Iron compatibility layer.

## Cross-provider rule
Bridge mods must not grant the same underlying action twice. Examples include Ars 'n' Spells, Goety Iron, Ars Creo/Technica, Create: Wizardry, Applied Create, Vampirism Iron's Spells Compatibility and Ars Morph. Runtime integrations should normalize one user action into one origin and then distribute mastery to all legitimately involved lanes. Duplicate callbacks from bridge mods must be deduplicated before rewards are persisted.

## Native-progression coexistence rule
When another mod already owns a meaningful progression system (for example Vampirism/Bloodlines/Werewolves, ShadowsZ shadow levels, Identity morph unlocks, Epic Fight skillbooks/skill slots, Goety Soul Energy/servant ownership or Professions), RPG Skill Tree should read or modify that native state through stable public hooks where useful. It should not recreate the same native tree or resource system unless there is a deliberate migration plan.
