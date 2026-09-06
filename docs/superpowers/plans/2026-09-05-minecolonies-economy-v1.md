# MineColonies Economy V1 Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement a server-authoritative, per-colony virtual currency ledger with deterministic inflation/settlement and a read-only MineColonies 1.1.1375 adapter, while keeping construction charging fail-closed until a safe upstream hook exists.

**Architecture:** Domain logic lives in `core/economy` and has no MineColonies dependency. NeoForge persistence/networking and the provider adapter live under `runtime/economy` and `runtime/compat/minecolonies/economy`. Native MineColonies `(dimension,id)` is only a binding; immutable economy UUID is the monetary identity. All mutations pass through one ledger service with replay protection.

**Tech Stack:** Java 21, NeoForge 1.21.1, MineColonies `1.1.1375-1.21.1-snapshot`, JUnit 5/pure Java tests, NeoForge GameTests, SavedData, NeoForge payload networking.

**Spec:** `plans/06-integrations/11-minecolonies-economy.md`

**Provider audit:** `plans/06-integrations/11-minecolonies-economy-audit-1.1.1375.md`

## Global Constraints

- Minecraft 1.21.1 / NeoForge / Java 21.
- MineColonies remains authority for colony/citizens/jobs/buildings/work orders/materials/logistics/research/permissions.
- V1 monetary authority is virtual ledger only; no physical coin authority.
- Native colony ID is recyclable and MUST NOT be the durable monetary key.
- Server authoritative; client sends intents only.
- One mutation = one canonical ledger entry; replay is idempotent.
- No Mastery from settlement, supply, building count, throughput, tax policy or UI packets.
- `CONSTRUCTION_CHARGE` remains disabled/fail-closed in V1.
- No mixin into MineColonies internals in this plan.
- Provider absent/unsupported must not break the RPG core.

---

### Task 1: Pure economy identity, state and arithmetic

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyColonyKey.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyParameters.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomicInputs.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomyState.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyMath.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/core/economy/EconomyMathJUnitTest.java`

**Interfaces:**
- `EconomyColonyKey(UUID value)` immutable identifier.
- `EconomyParameters` carries `baseQ`, weights, beta, price-index bounds and convergence bounds.
- `ColonyEconomicInputs(int adultWorkers, int builtLevelPoints, int warehouseCount)`.
- `ColonyEconomyState` stores supply buckets, price state, tax policy and settlement metadata without provider types.
- `EconomyMath.capacity(inputs, params)`, `targetPriceIndex(state, q, params)`, `convergePriceIndex(...)`, `nominalPrice(...)`.

- [ ] **Step 1: Write failing deterministic math tests**

Cover:

```text
5 workers + 8 built levels + 0 warehouses => Q=20
warehouse multiplier respects cap
M=Q => target index 100
M>Q => target >100
M<Q => target <100 but >= floor
settlement convergence respects asymmetric maxStepUp/maxStepDown
positive base price never rounds to zero
negative/invalid inputs reject
```

- [ ] **Step 2: Run `./gradlew testJunit` and verify RED**

Expected: compilation failure because `core.economy` types do not exist.

- [ ] **Step 3: Implement minimal immutable domain/math classes**

Use `long` for money, `double` only for index calculation, finite-value checks on every public math boundary, and exact integer arithmetic for supply.

- [ ] **Step 4: Run `./gradlew testJunit` and verify GREEN**

- [ ] **Step 5: Commit `feat(economy): add monetary domain and inflation math`**

---

### Task 2: Canonical ledger and idempotent mutations

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyTransactionKind.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyTransaction.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyCommand.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/EconomyMutationResult.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomyLedger.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomyLedgerJUnitTest.java`

**Interfaces:**

```java
EconomyMutationResult apply(ColonyEconomyState state, EconomyCommand command, long gameTime)
```

Initial executable kinds: `MINT`, `RETIRE`, `ADMIN_ADJUSTMENT`. `TAX`, `CONSTRUCTION_CHARGE`, `REFUND`, deposits/withdrawals remain modeled but reject with `UNSUPPORTED_KIND` until a real counterparty/wallet boundary exists.

- [ ] **Step 1: Write RED tests** for mint, retire, duplicate transaction ID, duplicate causal key, negative amount, insufficient treasury, overflow and conservation.
- [ ] **Step 2: Run `./gradlew testJunit` and confirm RED.**
- [ ] **Step 3: Implement one mutation service only.** Use `Math.addExact/subtractExact`; never mutate on failure.
- [ ] **Step 4: Add replay test proving second application returns duplicate result and leaves state byte-for-byte equivalent.**
- [ ] **Step 5: Run `./gradlew testJunit` GREEN.**
- [ ] **Step 6: Commit `feat(economy): add idempotent colony ledger`**

---

### Task 3: Settlement service

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomySettlementService.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomySnapshot.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/core/economy/ColonyEconomySettlementJUnitTest.java`

**Interfaces:**

```java
ColonyEconomySnapshot settle(ColonyEconomyState state, ColonyEconomicInputs inputs,
                             EconomyParameters parameters, long gameTime)
EconomyPreflight simulateMint(...)
```

- [ ] **Step 1: RED tests** for unchanged inputs, mint pressure, Q growth reducing pressure, monotonic convergence, independent two-colony state and preflight read-only semantics.
- [ ] **Step 2: Run JUnit RED.**
- [ ] **Step 3: Implement pure deterministic settlement.** No scheduler or provider calls here.
- [ ] **Step 4: Run JUnit GREEN.**
- [ ] **Step 5: Commit `feat(economy): add deterministic settlement service`**

---

### Task 4: Versioned server persistence

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomySavedData.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyStateCodec.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyRepository.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyStateCodecJUnitTest.java`
- GameTest: `src/main/java/dev/gustavopere/rpgskilltree/gametest/ColonyEconomyPersistenceGameTests.java`

**Interfaces:**
- Single Overworld-scoped `SavedData` named `rpgskilltree_colony_economy`.
- Schema `1` with explicit codec.
- Repository methods address `EconomyColonyKey`, not native colony ID.

- [ ] **Step 1: RED codec round-trip + unsupported-newer-schema tests.**
- [ ] **Step 2: Implement schema-1 codec and migration dispatcher.**
- [ ] **Step 3: RED GameTest create/save/reload/re-query transaction identity.**
- [ ] **Step 4: Implement SavedData repository and dirty marking after successful mutation only.**
- [ ] **Step 5: Run JUnit + NeoForge GameTests GREEN.**
- [ ] **Step 6: Commit `feat(economy): persist colony ledgers server-side`**

---

### Task 5: MineColonies provider gate and read-only adapter

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/MineColoniesEconomyVersionContract.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/MineColoniesEconomyAdapter.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/NativeColonyBinding.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/MineColoniesEconomyLifecycleEvents.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/MineColoniesEconomyVersionContractJUnitTest.java`
- GameTest: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/minecolonies/economy/gametest/MineColoniesEconomyProviderGameTests.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`

**Interfaces:**

```java
Optional<NativeColonyBinding> binding(IColony colony)
Optional<ColonyEconomicInputs> economicInputs(IColony colony)
boolean mayManageEconomy(ServerPlayer player, IColony colony)
void onColonyDeleted(ColonyDeletedModEvent event)
```

- [ ] **Step 1: RED version tests** accepting only audited 1.1.1375 metadata forms and rejecting incompatible versions.
- [ ] **Step 2: Implement classloading-safe bootstrap guarded by existing `OptionalIntegrations.Provider.MINECOLONIES`.**
- [ ] **Step 3: Provider-present GameTests** prove native ID/dimension binding, adult employed counting, built level points, bounded warehouse count and `MANAGE_HUTS` authorization.
- [ ] **Step 4: GameTest deletion archives economy UUID and recreation with recycled native ID cannot inherit balance.**
- [ ] **Step 5: Provider-free startup remains GREEN.**
- [ ] **Step 6: Commit `feat(economy): bridge MineColonies read-only inputs`**

---

### Task 6: Periodic runtime settlement

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyRuntime.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyEvents.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyRuntimeJUnitTest.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`

**Interfaces:**
- One bounded server settlement cadence.
- Runtime enumerates active colonies only when provider gate is active.
- No per-citizen tick listener.

- [ ] **Step 1: RED scheduler tests** proving no settlement before interval, one settlement at boundary and no duplicate same-tick execution.
- [ ] **Step 2: Implement cadence and diagnostic counters.**
- [ ] **Step 3: Assert provider absent = zero adapter calls and no crash.**
- [ ] **Step 4: Run JUnit/GameTests GREEN.**
- [ ] **Step 5: Commit `feat(economy): settle colony economy on bounded cadence`**

---

### Task 7: Server-authoritative snapshots, preflight and mutation intents

**Files:**
- Create payloads under `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/economy/`:
  - `EconomySnapshotRequestPayload.java`
  - `EconomySnapshotPayload.java`
  - `EconomyMintPreflightPayload.java`
  - `EconomyMintPreflightResultPayload.java`
  - `EconomyMintPayload.java`
  - `EconomyRetirePayload.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/economy/ClientColonyEconomyState.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java`
- Test: `src/test/java/dev/gustavopere/rpgskilltree/runtime/economy/ColonyEconomyIntentJUnitTest.java`

**Interfaces:**
- Client specifies native colony context + UUID intent + amount.
- Server resolves current colony, maps to economy UUID, checks `MANAGE_HUTS`, validates amount/limits, then invokes canonical ledger.
- Preflight never persists state.

- [ ] **Step 1: RED intent tests** for replay, wrong colony, permission denial, negative amount, cap violation and preflight immutability.
- [ ] **Step 2: Implement handlers and bump `ModNetworking.NETWORK_VERSION`.**
- [ ] **Step 3: Add client snapshot cache with no mutation API.**
- [ ] **Step 4: Run JUnit + build GREEN.**
- [ ] **Step 5: Commit `feat(economy): add authoritative economy networking`**

---

### Task 8: Administrative UI seam decision

**Files:**
- Audit/update: `plans/06-integrations/11-minecolonies-economy-audit-1.1.1375.md`
- Only if public/stable seam is proven: create client UI classes under `runtime/client/economy/`.

- [ ] **Step 1: Audit exact BlockUI/MineColonies 1.1.1375 Town Hall extension surface.**
- [ ] **Step 2: If a public extension seam exists, write RED UI/controller tests around snapshot/preflight/intents, then implement it.**
- [ ] **Step 3: If no stable public seam exists, document `TOWN_HALL_UI=FAIL_CLOSED` and expose no fragile mixin/invasive GUI replacement.**
- [ ] **Step 4: Commit the evidence/result.**

This task may legitimately close fail-closed; it must not invent an internal injection point.

---

### Task 9: Construction gate remains explicitly disabled

**Files:**
- Test: `src/test/java/dev/gustavopere/rpgskilltree/core/economy/ConstructionChargeFailClosedJUnitTest.java`
- Update audit/spec status only.

- [ ] **Step 1: Add contract test asserting `CONSTRUCTION_CHARGE` and `REFUND` cannot mutate in V1.**
- [ ] **Step 2: Run JUnit GREEN.**
- [ ] **Step 3: Verify no source references `BuildRequestMessage` or MineColonies internal requestWorkOrder interception.**
- [ ] **Step 4: Commit `test(economy): keep construction charging fail-closed`**

---

### Task 10: Full verification, documentation and merge

**Files:**
- Modify: `plans/06-integrations/11-minecolonies-economy.md`
- Modify: `plans/STATUS.md` only after actual acceptance.
- Rename to `plans/06-integrations/✅-11-minecolonies-economy.md` only when all in-scope V1 acceptance is truthfully satisfied.

- [ ] **Step 1: Run `./gradlew testJunit`.**
- [ ] **Step 2: Run provider-free NeoForge GameTests.**
- [ ] **Step 3: Run MineColonies provider-present economy GameTests.**
- [ ] **Step 4: Run NeoForge build and dedicated-server smoke.**
- [ ] **Step 5: Run Sonar/CodeQL and any MineColonies compatibility lanes required by CI.**
- [ ] **Step 6: Confirm no open review thread and synchronize current `main` into the feature branch.**
- [ ] **Step 7: Re-run all required gates on synchronized HEAD.**
- [ ] **Step 8: Merge PR with expected HEAD SHA.**
- [ ] **Step 9: Confirm `main` contains merge and post-merge CI is GREEN.**
- [ ] **Step 10: Finalize canonical status/✅ closeout without claiming construction charging or fragile Town Hall injection if those remain fail-closed.**

## Self-review

- Spec coverage: monetary domain, conservation, idempotency, Q, inflation, persistence, provider adapter, permissions, lifecycle, server networking, anti-abuse, Mastery exclusion and construction fail-closed all have explicit tasks.
- Out-of-scope preserved: physical coins, citizen wallets/salaries, FX, Bank building and construction charge are not silently implemented.
- Type boundaries: provider types appear only under runtime compat; `core.economy` remains provider-free.
- No placeholder implementation steps require an unknown hook; Task 8 explicitly resolves UI through evidence, and Task 9 locks unsafe construction charging closed.
