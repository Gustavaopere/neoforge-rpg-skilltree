# Stage 10.04 Compendium Discovery Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Implement persistent, server-authoritative, idempotent discovery progression and one-shot rewards for canonical Compendium entries without depending on the final UI/network protocol.

**Architecture:** `compendium/discovery` is a pure-Java domain that consumes the canonical `CompendiumEntryId` from Stage 10.03 and owns immutable discovery records, transitions, reward claims, completion summaries and a strict bounded codec. NeoForge-specific persistence/events/reward execution live under `runtime/compendium`, where a player attachment stores `DiscoveryProgress` and trusted server events feed the pure runtime. Stage 10.13 may wrap this codec in the final Compendium save envelope, but must preserve its entry identities and records.

**Tech Stack:** Java 21, NeoForge 1.21.1, existing Compendium Stage 10.03 API, NeoForge attachments, existing `ProgressionRewardService`/`CorePlayerProgressionRuntime`, shell-based pure-Java contract tests, GitHub Actions CI.

**Spec:** `plans/10-compendio-natural/04-descoberta-progresso.md`

## Global Constraints

- Minecraft NeoForge 1.21.1 and Java 21.
- Discovery identity is always `CompendiumEntryId`; translated names never key progress.
- Progression is monotonic: `UNKNOWN < SEEN < STUDIED < MASTERED`.
- Client-originated claims are never authoritative; only a server-validated boundary may create a trusted discovery signal.
- Replaying a discovery event or reward claim is idempotent.
- Removed catalog content preserves its discovery record; catalog presence is not required to decode a save.
- No unbounded coordinate history is stored; first location is optional and chunk-granular.
- Pure Compendium domain code must not import Minecraft/NeoForge classes.
- Stage 10.13 retains ownership of the final network protocol, global save migrations and cache/reload lifecycle.
- Stage 10.08 retains ownership of expensive structure/worldgen resolution; Stage 10.11 retains optional-mod adapters such as Exposure.

---

### Task 1: Canonical discovery state and immutable player progress

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryState.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryOrigin.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRecord.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryProgress.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryProgressTest.java`

**Interfaces:**
- `DiscoveryState` exposes `boolean atLeast(DiscoveryState other)` and `DiscoveryState max(DiscoveryState other)`.
- `DiscoveryOrigin` stores canonical dimension id plus first-seen chunk X/Z only.
- `DiscoveryRecord` stores canonical entry id, highest state, first game time, optional origin, immutable variant/objective/reward-claim sets.
- `DiscoveryProgress.empty()`, `record(CompendiumEntryId)`, `withRecord(DiscoveryRecord)`, `records()` produce immutable per-player state.

- [ ] Write tests proving state monotonicity, canonical-ID lookup, defensive copying and preservation of records whose entry is absent from any current catalog.
- [ ] Run `bash scripts/compendium/test_discovery.sh` and verify RED because the production types do not exist.
- [ ] Implement the minimum immutable state/progress types.
- [ ] Re-run the focused test and verify GREEN.

### Task 2: Trusted signals, criteria, idempotent transitions and one-shot rewards

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryTriggerType.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRewardKind.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRewardDefinition.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryCriterion.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoverySignal.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryTransition.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRuntime.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryInspectionValidator.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRuntimeTest.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryIdempotencyTest.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryRewardTest.java`
- Modify `scripts/compendium/test_discovery.sh`

**Interfaces:**
- `DiscoveryCriterion` binds one stable criterion id to an entry, trigger, target state, optional objective id and immutable reward definitions.
- `DiscoverySignal` is constructed only by trusted server boundaries and carries entry, trigger, game time, optional first origin and optional variant id.
- `DiscoveryRuntime.apply(DiscoveryProgress, DiscoveryCriterion, DiscoverySignal)` returns `DiscoveryTransition` containing the new progress plus only newly earned reward definitions.
- `DiscoveryInspectionValidator.validate(...)` accepts server-observed target/distance/item facts and rejects spoofed/mismatched inspection attempts; it does not trust a client-provided discovery result.

- [ ] Write RED tests for observation `UNKNOWN -> SEEN`, no state regression, duplicate same criterion producing no second reward, duplicate kill after a one-shot reward producing no reward, new variant/objective recording without invalid species rediscovery, and forged inspection rejection.
- [ ] Update `scripts/compendium/test_discovery.sh` to execute all Stage 10.04 pure tests.
- [ ] Run the new tests and capture RED before production classes exist.
- [ ] Implement minimal criterion/signal/runtime/validator behavior.
- [ ] Run the script and verify all Stage 10.04 tests GREEN.

### Task 3: Strict bounded discovery persistence codec

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryProgressCodec.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoverySaveRoundTripTest.java`
- Modify `scripts/compendium/test_discovery.sh`

**Interfaces:**
- `DiscoveryProgressCodec.CURRENT_VERSION = 1`.
- `encode(DiscoveryProgress)` produces deterministic bytes sorted by canonical serialized entry ID.
- `decode(byte[])` is fail-closed on unsupported version, truncated payload, trailing bytes, oversized collections/strings or malformed IDs.
- Decoding does not consult the current catalog and therefore preserves removed-mod/tombstone records.

- [ ] Write RED round-trip tests covering states, origin, variants, objectives and reward claims.
- [ ] Add tests proving an absent/unknown current-mod entry survives encode/decode and malformed/oversized payloads are rejected.
- [ ] Implement strict bounded codec with explicit maxima suitable for thousands of catalog entries but finite memory use.
- [ ] Run `bash scripts/compendium/test_discovery.sh` and verify GREEN.

### Task 4: Derived completion by category/namespace with explicit exclusions

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryCompletionCount.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryCompletionSummary.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryCompletionService.java`
- Create `src/test/java/dev/gustavopere/rpgskilltree/compendium/discovery/DiscoveryCompletionTest.java`
- Modify `scripts/compendium/test_discovery.sh`

**Interfaces:**
- `DiscoveryCompletionService.summarize(Collection<CompendiumEntry> loadedEntries, DiscoveryProgress progress, Set<CompendiumEntryId> excludedIds)` derives eligible/discovered counts globally, by category and by namespace.
- `excludedIds` is the explicit boundary for Stage 10.02 coverage states such as ignored/unavailable content; excluded entries never inflate the denominator.
- Legacy progress absent from `loadedEntries` remains persisted but does not inflate current denominators.

- [ ] Write RED tests for fauna/category counts, per-namespace counts, explicit exclusion and orphaned progress.
- [ ] Implement deterministic summaries from the loaded canonical catalog.
- [ ] Run discovery tests and verify GREEN.

### Task 5: NeoForge player attachment and server-authoritative runtime bridge

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/DiscoveryProgressAttachmentSerializer.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryRuntime.java`
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryRewardBridge.java`
- Modify `src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java`
- Create `scripts/compendium/verify_discovery_runtime.py`
- Modify `.github/workflows/alpha2-build.yml`

**Interfaces:**
- Register attachment `compendium_discovery` using `DiscoveryProgress::empty`, `DiscoveryProgressAttachmentSerializer.INSTANCE`, and `copyOnDeath()`.
- `CompendiumDiscoveryRuntime.progress(ServerPlayer)` reads authoritative attachment state.
- `CompendiumDiscoveryRuntime.apply(ServerPlayer, DiscoveryCriterion, DiscoverySignal)` rejects mismatched signals before writing, persists only changed progress, and executes only `transition.newRewards()`.
- `CompendiumDiscoveryRewardBridge` maps `DiscoveryRewardKind.CHARACTER_XP` to `ProgressionReward.characterXp(...)` and `CorePlayerProgressionRuntime.applyProgressionReward(...)`; unsupported future reward kinds fail explicitly rather than silently pretending success.

- [ ] Write a RED static/runtime validator asserting the attachment registration, serializer use, server-only runtime boundary and existing typed reward bridge calls.
- [ ] Implement serializer/attachment/runtime/reward bridge following existing NeoForge attachment patterns.
- [ ] Run `python3 scripts/compendium/verify_discovery_runtime.py` and Gradle compile/build; verify GREEN.
- [ ] Add `Compendium discovery tests` and `Compendium discovery runtime validation` CI steps before NeoForge build.

### Task 6: Initial trusted NeoForge event feeds

**Files:**
- Create `src/main/java/dev/gustavopere/rpgskilltree/runtime/compendium/CompendiumDiscoveryEvents.java`
- Modify `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- Modify `scripts/compendium/verify_discovery_runtime.py`

**Interfaces:**
- Defeat events derive `ENTITY|<registry id>` from the dead entity on `LivingDeathEvent` only when the damage source entity is a `ServerPlayer`.
- Interaction events derive entity identity from the server event target; no client-supplied identity is accepted.
- Dimension/biome observation hooks derive IDs from the server player's current level/holder, not packet payloads.
- Observation scanning, where used, is bounded and throttled; no full-registry or unbounded entity scan occurs every tick.
- Optional photo/Exposure, structure detection, flora/crop semantics and mod-specific triggers remain adapter/stage-owned, but can call the same trusted runtime boundary.

- [ ] Add RED validator checks that event methods are registered on the NeoForge bus and reject client-side execution.
- [ ] Implement the minimum generic server event feeds for defeat, interaction and environment entry/observation without optional-mod dependencies.
- [ ] Verify runtime validator, NeoForge build and dedicated-server smoke.

### Task 7: Full regression, review and Stage 10.04 acceptance audit

**Files:**
- Modify `.github/workflows/alpha2-build.yml` only if final gate ordering needs correction.
- Do not mark `plans/10-compendio-natural/04-descoberta-progresso.md` complete until after merge + post-merge CI evidence.

**Interfaces:**
- PR must expose independent `Compendium discovery tests` and `Compendium discovery runtime validation` gates in addition to all existing gates.

- [ ] Run/capture a deliberate RED CI from tests committed before production implementation.
- [ ] Obtain GREEN PR CI covering discovery tests, existing Compendium tests, all RPG validators, NeoForge build, JAR verification and dedicated-server smoke.
- [ ] Review diff against the 10.04 spec, especially idempotency, server authority, bounded persistence and stage-boundary deferrals.
- [ ] Fix all critical/important findings with new RED -> GREEN cycles.
- [ ] Merge only after final GREEN and unchanged/reconciled base.
- [ ] Verify post-merge `main` CI GREEN.
- [ ] Only then create the separate documentary closeout that renames the plan to `✅-04-descoberta-progresso.md`, updates `plans/STATUS.md`, and advances the causal pointer to Stage 10.05.
