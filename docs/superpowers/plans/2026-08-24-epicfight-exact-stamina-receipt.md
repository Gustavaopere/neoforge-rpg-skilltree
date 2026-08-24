# Epic Fight Exact Stamina Receipt Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add a server-authoritative, causal Epic Fight stamina-debit receipt bridge without implementing A0029 or A0042 refunds.

**Architecture:** Wrap the four audited `Skill.Resource.ResourceConsumer.consume(...)` call sites with MixinExtras `@WrapOperation`, measure real stamina delta around exactly one `original.call(...)`, and attach positive STAMINA debits only to an explicit server execution scope. Correlate that scope to `START_ACTION`, an animation playback generation, and finally the existing `CanonicalActionIdentity` at Epic Fight damage PRE; ambiguous or uncorrelated debits never enter `CanonicalStaminaService`.

**Tech Stack:** Java 21, NeoForge 21.1.248, Epic Fight `21.17.3.1-mc1.21.1-neoforge` / Modrinth `8HHhJt6i`, Sponge Mixin + MixinExtras, ASM structural verification, existing shell core-test harness.

**Spec:** Accepted P-0004/P-0005 technical design in the project conversation; no Notion edits are part of this implementation.

## Global Constraints

- Base commit is exactly `79cec9af9c86a2f9a0fd7e72fe145ce482e225aa`.
- Work only on `feat/epicfight-exact-stamina-receipt`.
- Do not implement A0029 or A0042 behavior and do not remove P-0004/P-0005.
- Use `@WrapOperation`, never `@Redirect`.
- A physical consumer invocation executes `original.call(...)` exactly once.
- Exact cost is `staminaBefore - staminaAfter`; event amount is metadata only.
- Only positive, finite, server-side STAMINA deltas are eligible.
- Never correlate to the next hit by temporal proximity.
- Multiple distinct positive debits for one execution are `AMBIGUOUS_MULTIPLE_DEBITS` and fail closed.
- A debit that cannot be tied to the same action is `EXACT_DEBIT_UNCORRELATED` and unusable.
- Epic Fight compatibility is exact-version gated to `21.17.3.1-mc1.21.1-neoforge`.
- The critical Epic Fight mixins live in their own required compatibility config; unsupported/absent Epic Fight skips them, while target drift on the supported version fails injection.

---

### Task 1: Pure causal receipt correlation

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/ExactStaminaReceiptCorrelation.java`
- Create: `src/test/java/dev/gustavopere/rpgskilltree/core/ExactStaminaReceiptCorrelationTest.java`
- Modify: `scripts/test-core.sh`

**Interfaces:**
- Produces execution tokens, debit recording statuses, animation playback generations, canonical action binding, uncorrelated/ambiguous states, actor cleanup, and a correlated debit result that runtime code can pass to `CanonicalStaminaService`.

- [ ] Write failing tests covering positive/zero/negative/non-finite delta handling, duplicate evidence, multiple real debits, two executions in one tick, reused animation generations, multi-hit/multi-target action reuse, delayed/unbound scope close, and actor cleanup.
- [ ] Run the core harness and verify RED because `ExactStaminaReceiptCorrelation` does not exist.
- [ ] Implement the minimal pure-Java correlation state machine.
- [ ] Run the core harness and verify GREEN.

### Task 2: Canonical stamina receipt query API

**Files:**
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalStaminaService.java`
- Modify: `src/test/java/dev/gustavopere/rpgskilltree/core/CanonicalStaminaServiceTest.java`

**Interfaces:**
- Produces `receipt(action, nowMillis)` for read-only exact receipt inspection while keeping `refundAmount(...)` as the canonical once-per-consumer claim path.

- [ ] Add failing tests for exact receipt lookup, expiry, action correlation, and duplicate observations.
- [ ] Run core tests and verify RED.
- [ ] Add the minimal query record/API without weakening existing deduplication.
- [ ] Run core tests and verify GREEN.

### Task 3: Server execution scope and Epic Fight bridge

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightExecutionScope.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightExactStaminaReceiptBridge.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/runtime/compat/epicfight/EpicFightCombatPerkHooks.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/runtime/CombatPerkRuntimeState.java`
- Modify: `src/main/java/dev/gustavopere/rpgskilltree/RpgSkillTreeMod.java`

**Interfaces:**
- `beginExecution/endExecution` bound to a concrete server skill execution.
- `observeConsumer(...)` records only exact positive STAMINA delta in the current execution.
- `START_ACTION` advances playback generation and binds only the current execution.
- Damage PRE binds the existing `CanonicalActionIdentity` to the matching active playback; subsequent targets reuse that action.
- Public API exposes receipt lookup and canonical deduplicated refund-amount claiming for later PR #8 integration.

- [ ] Add source/contract tests that require server-only scope behavior, no next-hit fallback, duplicate callback safety, multi-hit/multi-target reuse, and cleanup.
- [ ] Implement the runtime bridge and lifecycle cleanup with no A0029/A0042 calls.

### Task 4: Critical WrapOperation mixins and version gate

**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/EpicFightReceiptMixinPlugin.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/PlayerPatchStaminaReceiptMixin.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/SkillContainerStaminaReceiptMixin.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/runtime/mixin/epicfight/ComboAttacksStaminaReceiptMixin.java`
- Create: `src/main/resources/rpgskilltree-compat-epicfight-receipt.mixins.json`
- Modify: `src/main/resources/META-INF/neoforge.mods.toml`

**Interfaces:**
- `PlayerPatch.consumeForSkill(...FZCompoundTag)Z`: exactly two wrapped consumer invokes (`require=2`, `expect=2`, `allow=2`).
- `SkillContainer.requestHold(...)`: exactly one wrapped invoke.
- `ComboAttacks.executeOnServer(...)`: exactly one wrapped invoke.
- Scope entry/exit wraps `requestCasting` and `requestHold`; critical mixin config is skipped when Epic Fight is absent/unsupported and is strict when the validated version is present.

- [ ] Add failing source/bytecode contract checks for the mixin annotations/config.
- [ ] Implement mixins and plugin.
- [ ] Build to verify MixinExtras signatures compile.

### Task 5: Exact distributed-JAR ASM proof

**Files:**
- Create: `src/test/java/dev/gustavopere/rpgskilltree/compat/epicfight/EpicFightReceiptJarContractTest.java`
- Modify: `build.gradle`

**Interfaces:**
- Resolves exactly `maven.modrinth:vu3NZ5Ma:8HHhJt6i` as a non-transitive audit artifact.
- ASM asserts the target method descriptor and consumer invoke counts 2/1/1, globally rejects new production call sites, validates the STAMINA consumer bytecode path, and prints/asserts artifact version + SHA-256.

- [ ] Write the structural test first and verify it fails before the Gradle audit wiring exists.
- [ ] Add isolated audit configuration plus ASM/JUnit test dependencies and pass the exact resolved JAR path into tests.
- [ ] Run `test`, `test-core.sh`, `git diff --check`, `build`, and dedicated-server smoke verification.

### Task 6: Branch completion

- [ ] Verify no Notion/perk specification files changed and A0029/A0042 policies contain no new refund logic.
- [ ] Compare branch to base and inspect every changed file for scope creep.
- [ ] Run full CI on the branch without merging.
- [ ] Report branch, final SHA, CI result, files, ASM/JAR proof, remaining limitations, and the public receipt API PR #8 must call.
