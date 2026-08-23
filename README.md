# NeoForge RPG Skilltree

Private NeoForge 1.21.1 RPG progression project.

The project consolidates overlapping passive/skill-tree ideas around the visual/runtime model of Passive Skill Tree, then adds native adapters for the target modpack rather than forcing several competing progression UIs.

Current implemented core:
- deterministic canonical modifier resolution;
- emergent archetype/class resolution, including hybrids such as Spellblade and Technomancer;
- specialized-tree gateway + mastery XP requirements;
- canonical stat catalog for Minecraft, Iron's and Ars-facing virtual stats;
- normalized spell/combat/engineering action contracts with proc recursion guard;
- mastery reward policies for Iron's, Ars, Epic Fight and Create;
- safe Attunement resize/ejection model;
- 420-node PoE-style main-tree layout blueprint with 664 graph edges;
- initial 10 archetypes and 15 specialized tree gateways.

Run local dependency-free core checks with:

```bash
./scripts/test-core.sh
./scripts/validate-data.py
./scripts/generate-tree-skeleton.py
```

See `docs/ARCHITECTURE.md`, `docs/integrations/`, and `docs/MODPACK_SCOPE.md`.
