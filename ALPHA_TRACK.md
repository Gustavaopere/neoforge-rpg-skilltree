# Alpha integration track

This project remains in **Alpha** until the integrated modpack behavior is functionally complete. **Beta starts only after a functional JAR has been published in GitHub Releases** and the work has shifted primarily to correctness, balance, compatibility hardening, UX and release polish.

## Versioning rule
- Each major integration gets its own Alpha checkpoint.
- Fixes to an already completed integration use a decimal revision of that Alpha when practical (`1.1`, `1.2`, `2.1`, etc.).
- The Gradle `mod_version` tracks the latest project checkpoint; individual integration maturity is recorded below.
- A green CI build is necessary but does not by itself promote the project to Beta.

## Current integration map
| Track | Scope | Current state |
|---|---|---|
| Alpha 1 | Iron's Spells 'n Spellbooks | implemented; provider mastery/class correction included in 1.1 work |
| Alpha 2 | Ars Nouveau | implemented; composition/provider mastery correction included in 2.1 work |
| Alpha 3 | Epic Fight | implemented runtime adapter; current project checkpoint is `alpha.3.1-dev` because Iron/Ars identity logic was corrected after Alpha 3 landed |
| Alpha 4 | Goety | next magic integration |
| Alpha 5 | Malum | planned |
| Alpha 6 | Eidolon: Repraised | planned |
| Alpha 7 | Neo Vitae | planned |
| Alpha 8 | Create core + major Create industrial lanes | planned |
| Alpha 9 | Applied Energistics 2 | planned |
| Alpha 10 | Oritech | planned |
| Alpha 11 | Sable + Create Aeronautics vehicle/physics progression | planned |
| Alpha 12 | TerraFirmaCraft survival/mining/metallurgy | planned |
| Alpha 13 | Relics / Artifacts / Apotheosis / attunement consolidation | planned |

The numbering may gain point revisions when a prior integration needs correction without forcing unrelated systems into the same checkpoint.

## Updated pack architecture — 23 Aug 2026
The current Notion inventory establishes these major progression ecosystems:

### Magic
- Iron's Spells 'n Spellbooks 3.16.3 and its large school/addon ecosystem.
- Ars Nouveau 5.13.0 with Elemental/Elemancy, Creo, Technica, Controle, Not Enough Glyphs, Ars Zero, Starbunclemania and cross-provider bridges.
- Goety 3.1.4, including Goety Iron and Goety Cataclysm.
- Malum 1.8.2 with Gaze and Vestis.
- Eidolon: Repraised 0.5.0.2.
- Neo Vitae 1.1.13 as a separate blood-magic progression.

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

## Cross-provider rule
Bridge mods must not grant the same underlying action twice. Examples include Ars 'n' Spells, Goety Iron, Ars Creo/Technica, Create: Wizardry and Applied Create. Runtime integrations should normalize one user action into one origin and then distribute mastery to all legitimately involved lanes. Duplicate callbacks from bridge mods must be deduplicated before rewards are persisted.
