# NeoForge RPG Skilltree

Private NeoForge 1.21.1 RPG progression project.

The project consolidates overlapping passive/skill-tree ideas around the visual/runtime model of Passive Skill Tree, then adds native adapters for the target modpack rather than forcing several competing progression UIs.

Current implemented core:
- deterministic canonical modifier resolution;
- emergent archetype resolution with explicit specificity scoring, Primary Class + ordered Secondary Classes, and datapack-loaded archetype definitions;
- specialized-tree gateway + mastery XP requirements, including classless specialization support;
- semantic save migration for legacy class identities reclassified as specializations, with v1-v4 compatibility regression coverage;
- canonical stat catalog for Minecraft, Iron's and Ars-facing virtual stats;
- normalized spell/combat/engineering action contracts with proc recursion guard;
- mastery reward policies for Iron's, Ars, Epic Fight, Goety, Malum and Create-facing progression;
- safe Attunement resize/ejection model;
- 512-node PoE-style main-tree layout blueprint across 11 progression domains;
- server-authoritative purchase/respec and persistent final-triad progression;
- semantic tree-architecture runtime/catalog with 83 main, specialist, provider and hybrid tree definitions;
- data-driven branches, gates, mastery requirements and provider ownership for the planned specialist ecosystem;
- data-driven morph taxonomy/ecology with optional Identity 2 current-form integration, faction reactions and temporary disguise-compromise memory.

Semantic architecture lives under `data/rpgskilltree/tree_architecture/`. It is intentionally separate from the purchase graph in `node_rules/`: `node_rules` determines what can be bought and how nodes connect, while `tree_architecture` describes what each tree/branch means so the planner, UI and future generators can share one source of truth.

The modern archetype catalog is loaded and validated, but it does not yet replace the legacy live class rules. Final purchased-node -> investment metadata and physical Bridge Node corridors are intentionally deferred until the parallel perk/topology work is reconciled.

Run local dependency-free core checks with:

```bash
./scripts/test-core.sh
./scripts/validate-data.py
./scripts/generate-tree-skeleton.py
```

See `docs/ARCHITECTURE.md`, `docs/integrations/`, and `docs/MODPACK_SCOPE.md`.
