# TESTING — RPG Skill Tree

Status: **canonical testing strategy and local reproduction guide**  
Target: **Minecraft 1.21.1 + NeoForge 21.1.248 + Java 21 + Gradle Wrapper 8.14**

The repository has multiple test layers. A green build is necessary but is not treated as equivalent to runtime or provider-present evidence.

## Evidence rules

- A bug fix should start with a regression that fails for the intended reason when practical.
- Pure progression rules stay outside Minecraft runtime whenever possible.
- NeoForge lifecycle, registry, world, player and entity behavior is verified in NeoForge runtime.
- Dedicated-server safety is a permanent gate for common/server/runtime changes.
- Generated resources must be deterministic and leave a clean working tree after official generators run.
- Provider absence and provider presence are different claims. Foundation proves core-only absence safety; each integration stage must separately certify provider-present versions and behavior.
- Do not convert compile success into a stronger claim such as runtime, UI or save compatibility.

## Supported local environment

Use the checked-in wrapper; do not substitute a system Gradle installation.

```bash
java -version
./gradlew --version
```

The canonical environment contract is enforced by:

```bash
python3 scripts/verify-gradle-wrapper.py
python3 scripts/verify-foundation-bootstrap.py
```

## Fast local loop

Run the pure/core suite before Minecraft runtime when changing deterministic progression logic, codecs, policies, parsers or validators:

```bash
bash scripts/test-core.sh
./gradlew --no-daemon test
```

`scripts/test-core.sh` compiles the pure Java/core fixtures with Java 21 and runs the project validators that do not require a Minecraft process. Gradle `test` runs the JUnit 5 suite.

Foundation contracts can be reproduced directly:

```bash
python3 scripts/verify-foundation-bootstrap.py
python3 scripts/verify-optional-integrations.py
python3 scripts/verify-foundation-diagnostics.py
```

## NeoForge GameTests

Runtime behavior that needs Minecraft registries, players/entities, world state or lifecycle belongs in NeoForge GameTests.

```bash
./gradlew --no-daemon runGameTestServer
```

The Foundation suite includes `FoundationGameTests.dedicatedServerGameTestBoots`; additional GameTests should be added when a change cannot be proved correctly by pure unit tests or static validators.

## Data and generated-resource gates

Run the official generators/validators relevant to the changed catalog. The full CI regenerates the derived skill-tree outputs and then requires a clean diff.

Canonical drift checks are:

```bash
git diff --check
git diff --exit-code
```

`git diff --check` catches whitespace errors; only `git diff --exit-code` proves that regeneration produced no uncommitted content drift.

## NeoForge build

```bash
./gradlew --no-daemon build
```

The CI additionally inspects the produced `RPGSkillTree*.jar` for `META-INF/neoforge.mods.toml` and `RpgSkillTreeMod.class`.

## Dedicated-server smoke

A plain runtime invocation is:

```bash
./gradlew --no-daemon runServer
```

For an exact reproduction of the CI smoke gate, use a disposable server run with `eula=true`, set `RPGSKILLTREE_COMPENDIUM_INVENTORY=1`, capture the server log, wait for the vanilla `Done (...)!` ready marker plus the Compendium startup marker, and then run:

```bash
python3 scripts/verify-optional-provider-smoke.py build/server-smoke.log
```

The CI smoke is bounded and fails if the server exits early, does not reach ready state, does not publish the expected runtime inventory, loads an optional provider in the core-only configuration, or emits `ClassNotFoundException`/`NoClassDefFoundError`.

## Full pre-PR Foundation sequence

For Foundation/runtime changes, the local sequence corresponding to the principal CI gates is:

```bash
python3 scripts/verify-gradle-wrapper.py
python3 scripts/verify-foundation-bootstrap.py
python3 scripts/verify-optional-integrations.py
python3 scripts/verify-foundation-diagnostics.py
bash scripts/test-core.sh
./gradlew --no-daemon test
./gradlew --no-daemon runGameTestServer
./gradlew --no-daemon build
./gradlew --no-daemon runServer
```

The GitHub workflow also executes Compendium tests, data/client/node validators, canonical provider-binding validation, generated-data drift checks, JAR verification and the bounded dedicated-server smoke.

## Test layers

### T0 — Pure core and JUnit 5

Use for deterministic state transitions and policies: level curves, ledgers/currencies, node cost/rank, graphs, respec, classes/archetypes, specializations, mastery, canonical modifier policies, semantic-action dedupe, migrations and codecs that do not require runtime types.

Requirements: deterministic fixtures, no ignored known failure, clear assertions, and no network/filesystem dependency unless the unit under test is specifically a parser/serializer for those resources.

### T1 — Data/generator contracts

Use Python/Java validators for JSON parseability, namespaced IDs, graph and catalog references, provider ownership, canonical stat bindings, Minecraft 1.21.1 resource conventions, optional tag entries and deterministic generated projections.

### T2 — Save/migration fixtures

Every supported persisted schema needs representative migration and corruption cases. Assertions must protect conservation of XP, paid points, mastery, discoveries, choices and intended specialization progress. Unknown IDs, duplicate entries, truncated data, removed providers and repeat migration must fail closed or reconcile according to the explicit migration policy.

### T3 — NeoForge GameTests

Use when behavior depends on real registries or lifecycle. Typical targets are attachments, login/respawn reconciliation, modifier apply/remove/idempotence, reload publication, networking authority, semantic-action dedupe and registry-sensitive resources.

### T4 — Dedicated-server smoke

Permanent gate for common/server/runtime changes. The core-only server must reach ready state without client leakage or optional-provider classloading failures. Provider-present matrices are owned by the relevant integration plans.

### T5 — Client/UI verification

Client-facing changes require actual client verification for startup, key mapping, screen lifecycle, disconnect/reconnect state, large-tree navigation, unavailable-provider reasons, GUI scale and localization. If automation is not available, record the manual checklist and evidence instead of claiming automated coverage.

### T6 — Provider integration tests

Each supported provider requires provider-present startup and behavior evidence in addition to the Foundation core-only gate. Test compatible versions, confirmed/cancelled actions, duplicate callbacks, fake-player/creative/spectator policy, cleanup, multiplayer attribution and API-drift behavior.

### T7 — Performance/regression tests

Profile hot paths when they change: XP/mastery award frequency, modifier refreshes, state-sync coalescing, packet size/frequency, reload time, UI hit testing, cache growth and high-player event throughput. Do not invent performance budgets without measured baselines.

## Merge gates by change type

Pure core changes require relevant core/JUnit tests and build. Data/schema/generator changes require validators, deterministic regeneration and build, plus runtime reload tests when semantics change. Save/codec changes require regression/migration fixtures and runtime evidence when lifecycle is involved. Networking/runtime progression changes require core tests, GameTests, invalid/replay cases and dedicated server. Client/UI changes require client evidence plus dedicated-server safety. Provider integration changes require both the core-only gate and provider-present evidence.

## Mandatory high-risk regressions

Maintain explicit automated coverage as the relevant stages land for: registry/tag paths, optional tag entries, missing attribute targets, removed-node reconciliation, paid-cost refunds after rule changes, specialization migration/provenance, invalid reload retaining last-known-good state, client/server rules revision, XP-only updates avoiding unnecessary modifier rebuilds, semantic-action dedupe, generated-data drift, provider absence not leaving purchased useless behavior, and dedicated-server freedom from client/optional classloading.

## Evidence vocabulary

- **compiles**: NeoForge build completed.
- **JUnit/core tests pass**: the deterministic/pure test layers completed.
- **GameTest passes**: the named Minecraft runtime behavior completed under the GameTest server.
- **server smoke passes**: the dedicated server reached the tested ready-state contract.
- **integration works**: requires provider-present runtime evidence, not only `compileOnly` compilation or core-only startup.
- **UI works**: requires actual client verification.
- **save compatible**: requires the applicable migration fixtures/runtime evidence.

Never promote one level of evidence into a stronger claim.