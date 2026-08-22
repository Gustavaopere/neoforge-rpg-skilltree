# Changelog

## 0.2.0-alpha.2-dev - Unreleased

Alpha 2 is the progression/runtime foundation phase. It is not yet an installable release.

### Added
- Independent Character Level 1-100 with a piecewise XP curve and 1 passive point per level gained.
- Source-aware passive point ledger for LEVEL, BOSS, ADVANCEMENT, ADMIN and MIGRATION awards.
- First-credit boss rewards: Cataclysm 5, vanilla 3, Apotheosis/Apothic 2 by default.
- 11-domain main-tree vocabulary with 3/3/3 Final Triads.
- Persistent class unlocks and 10-point abnormal bridges for non-adjacent classes.
- 24 seed classes, including Paladin, Cleric, Technomancer, Druid, Warlock, Geomancer and Metamorph.
- 25 post-class specialization definitions across Iron's, Ars, Epic Fight, Create, AE2 and Oritech.
- Path-of-Exile-style target path planner.
- Identity 2 / Ars Morph class-gating contracts for Druid and Metamorph forms.
- Warlock pact choice model with Blade, Familiar, Grimoire, Souls and Blood.
- Apotheosis/Apothic gem socket and attribute integration contracts.
- Main-tree blueprint expanded to 512 nodes and 803 generated graph edges.

### Still required before Alpha 2 release
- NeoForge persistence/network synchronization.
- Passive Skill Tree runtime/UI source integration.
- Real adapters for Identity 2/Ars Morph, Apotheosis, Iron's, Ars, Epic Fight, Create, Oritech, AE2 and other selected pack mods.
- Conversion of abstract layout nodes into actual playable Passive Skill Tree node JSON.
- Gradle/JAR build and isolated in-game validation on NeoForge 1.21.1.

## 0.1.0-alpha.1 - 2026-08-22

First architecture/core alpha for the NeoForge 1.21.1 RPG Skilltree project.

### Added
- Canonical RPG stat catalog and deterministic modifier resolution.
- Emergent archetype resolver with 10 initial classes/hybrids, including Spellblade and Technomancer.
- Specialized-tree unlock gates combining main-tree investment, gateway nodes, and mastery XP.
- Normalized spell, combat, and engineering action contracts.
- Proc recursion guard for derived/echo actions.
- Mastery reward policies for Iron's Spells 'n Spellbooks, Ars Nouveau, Epic Fight, and Create-facing adapters.
- Safe Curios Attunement slot resize/ejection planning model.
- 420-node Path-of-Exile-style main-tree blueprint with 664 graph edges.
- 15 initial specialized tree gateways for Iron's schools, Ars, Epic Fight, and Create.
- Integration architecture notes for Iron's, Ars Nouveau, Epic Fight, Create, and Curios.

### Status
This alpha is a source/core architecture milestone. It is not yet an installable gameplay JAR. NeoForge runtime adapters, UI integration with the Passive Skill Tree port, concrete keystones/notables, persistence/networking, and in-game validation remain subsequent milestones.
