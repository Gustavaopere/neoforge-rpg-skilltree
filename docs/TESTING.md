# TESTING — RPG Skill Tree

Status: **canonical testing strategy**  
Target: **Minecraft 1.21.1 + NeoForge 21.1.x + Java 21**

The project currently has useful core tests and Python validators, but these are not sufficient evidence for runtime correctness. This document defines the target test pyramid and merge gates.

---

# Principles

1. A bug fix should start with a regression that fails for the intended reason when practical.
2. Pure progression rules should remain fast and testable without launching Minecraft.
3. NeoForge behavior must be tested in NeoForge runtime, not inferred from source-string checks.
4. Build success is necessary but not proof of gameplay correctness.
5. Dedicated-server safety is a permanent gate.
6. Provider integrations require both provider-absent and provider-present evidence.
7. Save migrations require fixtures from every supported version.
8. Generated resources must be deterministic and leave a clean working tree.
9. Version-sensitive resources/registries must be validated specifically against Minecraft 1.21.1.
10. Tests must be automatically discovered; adding a test must not require remembering to edit a manual class list indefinitely.

---

# Current baseline

At the consolidated audit baseline:

- existing Java tests are mainly executable `main()` classes run by `scripts/test-core.sh`;
- Python validators cover graph/data/export structure;
- CI builds the NeoForge mod and performs a dedicated-server smoke test;
- JUnit is not integrated into Gradle;
- GameTests are absent;
- client startup/UI is not automated;
- provider-present runtime matrices are absent;
- the workflow uses `git diff --check`, which does not detect generated-content drift.

Preserve useful existing tests while migrating toward the target structure.

---

# Test layers

## T0 — Pure core unit tests

Use JUnit 5 after Phase 0 migration.

Cover deterministic logic that does not require Minecraft runtime:

- level curve;
- passive ledgers/currencies;
- node purchase cost/rank;
- graph traversal/dependencies;
- respec accounting;
- `InvestmentState`;
- archetype/class resolution;
- specificity/tie ordering;
- specialization resolution/provenance rules;
- mastery policies;
- gateway requirements;
- canonical modifier ordering/caps;
- semantic-action fingerprints/dedupe;
- migrations/codecs where Minecraft types are not required.

Requirements:

- deterministic;
- no network/filesystem when avoidable;
- small fixtures;
- clear assertion messages;
- no test that passes by filtering/ignoring a known failure.

## T1 — Data/generator contract tests

Run the existing Python validators plus new validators as necessary.

Verify:

- JSON parseability;
- namespaced IDs;
- graph references;
- no self-requirement/cycles where prohibited;
- class/specialization/tree references;
- provider ownership;
- canonical stat bindings;
- 1.21.1 registry/resource naming conventions;
- optional tag entries;
- generated client/server projections;
- deterministic regeneration.

Canonical generated-data gate:

```bash
# run all official generators first
git diff --check
git diff --exit-code
```

`git diff --check` remains useful for whitespace, but it is not a substitute for `git diff --exit-code`.

## T2 — Save/migration fixtures

Maintain binary or representative fixtures for every supported persisted version.

Minimum cases:

- v1→latest;
- v2→latest;
- v3→latest;
- v4→v5 when introduced;
- migrated class→specialization identities;
- migration applied twice;
- unknown node;
- renamed node/alias;
- removed provider;
- rank above new max;
- cost changed after purchase;
- unknown specialization/provider ID;
- truncated data;
- duplicate entries;
- oversized collections;
- trailing bytes;
- invalid enum/legacy token where supported.

Assertions must include conservation rules: XP, paid points, mastery, discoveries, choices and intended specialization progress must not silently disappear.

## T3 — NeoForge GameTests/runtime tests

Use NeoForge 1.21.1 GameTests for behavior that depends on registries, players/entities/world state or lifecycle.

Priority GameTests:

### Resources/registries

- boss tag resolves from correct `entity_type` tag directory;
- optional Cataclysm entries do not break core-only loading;
- every required node attribute target resolves in the actual registry;
- canonical binding validation behaves correctly.

### Player progression

- attach initial state;
- login reconciliation;
- purchase/upgrade/respec;
- death/respawn/copy policy;
- removed-node administrative reconciliation;
- provider removed after persisted state;
- modifier apply/remove/idempotence;
- reload updates online player effects.

### Reload

- valid snapshot publishes atomically;
- invalid snapshot retains last-known-good rules;
- cross-reference failure does not partially replace catalogs;
- removed effect disappears;
- changed cost/requirement gets a new rules revision.

### Networking

- invalid purchase intent rejected;
- client cannot forge balance/rank/cost;
- owner-only state sync;
- revision mismatch handled;
- rate limits/backpressure when implemented;
- repeated invalid requests do not trigger unbounded full sync.

### Mastery/action pipeline

- duplicate observation of one semantic action rewards once;
- proc-depth policy;
- cancelled action does not reward;
- confirmed action rewards;
- multiple awards in one tick coalesce sync.

## T4 — Dedicated server smoke

Permanent gate for any common/server/runtime change.

At minimum verify:

- server starts core-only;
- no client classloading error;
- datapacks load;
- rules snapshot builds;
- no missing registry target errors;
- test world reaches a stable ready state;
- server shuts down cleanly.

Later matrix:

- core only;
- each supported provider individually;
- selected provider combinations matching the target modpack.

## T5 — Client smoke/UI verification

For client-facing changes verify:

- client starts;
- tree key mapping works using the supported lifecycle;
- screen opens/closes;
- state clears on disconnect;
- reconnect to different server/rules revision works;
- large tree remains navigable;
- gateway/subtree navigation;
- unavailable provider/reason shown;
- GUI scale/resolution behavior;
- localization for new strings.

Where automation is impractical, use a documented manual checklist and screenshots rather than claiming automated coverage.

## T6 — Provider integration tests

Every supported integration needs a matrix covering absence and presence.

For each provider:

- compatible version loads;
- provider absent loads;
- provider action confirmed;
- action cancelled/failed;
- duplicate callback/event;
- fake player policy;
- creative/spectator policy;
- logout/reload cleanup;
- multiplayer attribution;
- provider removed from an existing save;
- unsupported/API-drift behavior is fail-visible.

Specific high-risk cases:

- Iron's/Ars cast gating and confirmed mastery;
- Epic Fight high-frequency hit/skill/dodge duplication;
- Goety intent→outcome correlation;
- Malum reflection/API drift;
- Eidolon ritual/alchemy contributor attribution and cleanup;
- Identity authorization/mixin visibility;
- future Create progression based on completed meaningful outcomes, never raw machine ticks.

## T7 — Performance/regression tests

Measure hot paths when modifying them:

- XP/mastery awards must not refresh all modifiers when allocations/effects did not change;
- state sync coalescing per tick;
- packet size/frequency;
- rules reload time;
- modifier refresh time;
- UI rendering/hit testing for 512+ nodes;
- cache/map growth over long sessions;
- player-placed ore provenance storage;
- many-player event throughput.

Do not optimize the entire UI without profiling, but obvious architectural amplification (full effect rebuild/full sync per award) should be removed before scaling content.

---

# Canonical commands

## Current transitional commands

Until Gradle Wrapper/JUnit/GameTests are implemented, the repository currently relies on commands such as:

```bash
bash scripts/test-core.sh
python3 scripts/validate-data.py
python3 scripts/validate-client-tree.py
python3 scripts/validate-node-effects.py
python3 scripts/validate-passive-export.py
python3 scripts/verify-runtime-contract.py
gradle --no-daemon build
gradle --no-daemon runServer
gradle --no-daemon runData
git diff --check
```

This is transitional, not the desired final contract.

## Target commands after Phase 0

```bash
./gradlew --no-daemon clean test
./gradlew --no-daemon runGameTestServer
./gradlew --no-daemon build
./gradlew --no-daemon runServer
./gradlew --no-daemon runData

git diff --check
git diff --exit-code
```

Provider/client-specific tasks may be added when the integration matrix is formalized.

---

# Merge gates by change type

## Pure core change

Required:

- relevant JUnit/core tests;
- full core suite;
- build.

## Data/schema/generator change

Required:

- validators;
- regeneration;
- `git diff --exit-code` after expected generated changes are committed;
- cross-reference tests;
- build;
- reload/GameTest if runtime semantics changed.

## Save/codec/migration change

Required:

- regression first;
- all migration fixtures;
- corrupt/truncated test;
- round trip;
- runtime login/respawn where applicable;
- dedicated server.

## Networking/runtime progression change

Required:

- core tests;
- GameTests;
- invalid/replay cases;
- owner sync verification;
- dedicated server;
- performance check if hot path changed.

## Client/UI change

Required:

- build;
- client smoke/manual regression;
- disconnect/reconnect state behavior;
- dedicated server to prove no client class leakage.

## Provider integration change

Required:

- core-only startup;
- provider-present startup;
- confirmed/cancelled action tests;
- duplicate/dedupe test;
- multiplayer attribution when relevant;
- dedicated server;
- client if the integration has client behavior.

---

# Mandatory regression cases from the consolidated audit

These should receive explicit automated coverage as the relevant phases land:

1. Boss tag singular path.
2. Cataclysm optional tag entries.
3. All seven vanilla attribute families resolve on 1.21.1.
4. Missing attribute target cannot become silent purchased no-op.
5. Removed node can be reconciled without requiring its missing definition.
6. Paid-cost refund survives rule cost changes.
7. Migrated Industrialist/Logistician/Prospector progress remains preserved.
8. Generic specialization provenance is respected once added.
9. Invalid reload keeps old snapshot.
10. Client uses the same rules revision as server.
11. XP/mastery-only update does not refresh all attributes.
12. Duplicate semantic action rewards once.
13. Generated-data drift fails CI.
14. Provider absence never leaves a purchasable useless node.
15. Dedicated server remains free of client classloading.

---

# Evidence standard

Use precise completion language:

- “compiles” means build succeeded;
- “server smoke passes” means dedicated server initialized under the tested configuration;
- “GameTest passes” means the tested runtime behavior succeeded;
- “integration works” requires provider-present runtime evidence, not only compileOnly compilation;
- “UI works” requires actual client verification;
- “save compatible” requires migration fixtures/runtime evidence.

Never promote one level of evidence into a stronger claim.