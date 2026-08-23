# Alpha 2 NeoForge Runtime Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Turn the dependency-free Alpha 2 progression core into a NeoForge 1.21.1 runtime that persists and synchronizes player progression and can accept gameplay/boss events without consuming vanilla XP.

**Architecture:** Keep all game-independent rules in `core`. The NeoForge layer stores one immutable `ProgressionState` attachment on each player, replaces it through `setData` after mutations, and sends a versioned byte snapshot to the owning client. Mod integrations translate their own events into the same runtime service instead of mutating state directly.

**Tech Stack:** Java 21, NeoForge 21.1.248, NeoGradle userdev 7.1.26, Minecraft 1.21.1, NeoForge attachments, custom payload networking.

**Spec:** `docs/ALPHA2_DESIGN.md`

## Global Constraints
- `minecraft_version=1.21.1` and `neo_version=21.1.248`.
- Vanilla experience level/points are never replaced or consumed.
- `ProgressionStateCodec` is the canonical persistence/network representation for Alpha 2 runtime state.
- Only the owning player receives full progression-state synchronization.
- Server state is authoritative; client requests never send a replacement `ProgressionState`.
- Player progression copies on death.
- Optional mod adapters must not be mandatory dependencies of the base runtime.

---

### Task 1: NeoForge build scaffold
**Files:**
- Create: `settings.gradle`
- Create: `build.gradle`
- Create: `gradle.properties`
- Create: `src/main/resources/META-INF/neoforge.mods.toml`
- Create: `src/main/resources/pack.mcmeta`
- Create: `scripts/verify-runtime-scaffold.py`

**Interfaces:**
- Produces mod id `rpgskilltree`, Java 21 toolchain and NeoForge `21.1.248` dependency.

- [ ] Write scaffold validator requiring exact Minecraft/NeoForge/mod-id metadata and expected runtime entrypoint paths.
- [ ] Run validator and verify RED while files are absent.
- [ ] Add Gradle/resource scaffold.
- [ ] Run validator and verify GREEN.
- [ ] Commit `build: scaffold neoforge alpha 2 runtime`.

### Task 2: Persistent player progression attachment
**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/ModAttachments.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/ProgressionAttachmentSerializer.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/PlayerProgressionRuntime.java`

**Interfaces:**
- `ModAttachments.PROGRESSION` stores `ProgressionState`, serializes with `ProgressionStateCodec`, and uses `copyOnDeath()`.
- `PlayerProgressionRuntime.get(ServerPlayer)` returns current state.
- `PlayerProgressionRuntime.set(ServerPlayer, ProgressionState)` replaces attachment and triggers owner sync.

- [ ] Extend scaffold validator to require attachment registration, `copyOnDeath`, and serializer usage.
- [ ] Verify RED.
- [ ] Implement attachment and runtime access service.
- [ ] Verify structure and core tests.
- [ ] Commit `feat: add persistent neoforge progression attachment`.

### Task 3: Owner-only progression synchronization
**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ProgressionSyncPayload.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/network/ModNetworking.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/client/ClientProgressionState.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/events/PlayerProgressionEvents.java`

**Interfaces:**
- `ProgressionSyncPayload` carries only `byte[] snapshot` encoded by `ProgressionStateCodec`.
- `ModNetworking.syncToOwner(ServerPlayer, ProgressionState)` uses `PacketDistributor.sendToPlayer`.
- Login and respawn events send a full snapshot.
- Client handler updates `ClientProgressionState` only after decoding the versioned snapshot.

- [ ] Extend validator for payload type/codec, owner-only packet distribution and login/respawn sync hooks.
- [ ] Verify RED.
- [ ] Implement payload registration and client cache.
- [ ] Verify structure and core tests.
- [ ] Commit `feat: sync progression state to owning client`.

### Task 4: Runtime mutation API and initial boss hook
**Files:**
- Modify: `PlayerProgressionRuntime.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/events/BossProgressionEvents.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/BossRewardKeyResolver.java`
- Test core resolver behavior in `Alpha2ProgressionTest.java` through a game-independent `BossIdentity` contract.

**Interfaces:**
- Server adapters call runtime methods that apply `ProgressionService` and immediately persist/sync resulting immutable state.
- Boss reward keys are stable identities, never entity UUIDs.
- Vanilla Ender Dragon/Wither map to `minecraft:*`; Cataclysm uses entity registry id; randomized Apothic bosses use bounded category keys supplied by an Apothic adapter rather than UUID.

- [ ] Write failing core tests for stable boss-key classification contract.
- [ ] Verify RED.
- [ ] Implement minimal resolver and NeoForge `LivingDeathEvent` hook for vanilla/Cataclysm registry IDs.
- [ ] Verify core tests and scaffold validator.
- [ ] Commit `feat: wire first kill boss progression runtime`.

### Task 5: Build verification handoff
**Files:**
- Modify: `docs/ALPHA2_STATUS.md`
- Modify: `RELEASE_NOTES_ALPHA_2_DRAFT.md`

**Interfaces:**
- Document exact command `./gradlew compileJava` and runtime smoke-test checklist for an environment with Maven access.

- [ ] Run `scripts/test-core.sh`, `scripts/validate-data.py`, `scripts/generate-tree-skeleton.py`, `scripts/verify-runtime-scaffold.py`, and `git diff --check`.
- [ ] Attempt Gradle compilation only if a Gradle wrapper/online dependency resolution is available; record the actual result without claiming a JAR if it cannot run.
- [ ] Commit `docs: checkpoint neoforge alpha 2 runtime`.
