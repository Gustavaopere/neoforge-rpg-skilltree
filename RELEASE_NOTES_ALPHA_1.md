# Alpha 1 — Core Architecture

Tag: `v0.1.0-alpha.1`
Date: 2026-08-22
Target: Minecraft 1.21.1 / NeoForge

Alpha 1 establishes the progression engine and data model that the playable mod will build on.

Highlights:
- one large 420-node main-tree blueprint rather than rigid class selection;
- emergent classes derived from invested regions, including hybrid Technomancer (`ARCANE + ENGINEERING`);
- mastery XP gates for specialized trees so players must actually use a discipline, not only buy passive nodes;
- canonical modifier merging to avoid duplicate/conflicting bonus systems across integrated mods;
- normalized adapters for magic, combat, and engineering actions;
- recursion protection for echo/duplicate effects;
- Curios Attunement design for tree-bound equipment without consuming ordinary Curios slots;
- initial integration contracts for Iron's, Ars Nouveau, Epic Fight, and Create.

Verification for this release:
- `CoreProgressionTest: PASS`
- `Data validation: PASS (10 archetypes, 15 tree gateways, 420 main-tree nodes budgeted)`
- generated layout: `420 nodes / 664 edges`

Known limitation: this is not yet an installable Minecraft mod JAR. It is the first source/core alpha and architecture checkpoint.
