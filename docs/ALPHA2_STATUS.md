# Alpha 2 Status

## Implemented and verified in the dependency-free core
- Character Level / XP curve / point ledger.
- Boss reward registry and first-credit tracking.
- Final Triads and class unlock cost resolution.
- Persistent class unlock state and one-time abnormal bridge spending.
- Target-node shortest path planner.
- Druid vs Metamorph morph access policy.
- Apothic gem socket / gem-power contracts and canonical Apothic Attributes.
- Post-class specialization requirements.
- Warlock pact choice capacity.
- 24 class definitions, 25 specialization definitions and 5 pact definitions.
- 512-node main-tree blueprint.

## Runtime work still required
1. Bring the Passive Skill Tree NeoForge 1.21.1 runtime/UI into this project or otherwise make it the concrete rendering/allocation engine.
2. Add NeoForge player attachments/capabilities for `ProgressionState`, mastery, triads, class choices and planned paths.
3. Add client/server synchronization and migration/versioning.
4. Convert the abstract 512-node layout into real skill JSON and class-branch data.
5. Implement real XP event adapters with anti-cheese rules.
6. Implement boss detection/reward keys, especially bounded Apotheosis boss tiers/archetypes.
7. Wire mod adapters in vertical slices: Iron's, Ars, Epic Fight, Create/Oritech/AE2, Apothic, Identity 2/Ars Morph, then other pack systems.
8. Build the actual NeoForge 1.21.1 JAR and test in an isolated instance before the full modpack.

## Known environment blocker
The current execution container has no outbound DNS access to GitHub/Maven (`Could not resolve host: github.com`), so it cannot clone the Passive Skill Tree source or download Gradle dependencies. Public source/API research is still possible through the web/GitHub connectors, but a real Gradle build needs an environment with dependency network access or the required sources/dependencies supplied locally.
