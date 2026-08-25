# P-0035 ImpactStaminaBridge Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Certify generic Epic Fight 21.17.3.1 impact-to-stamina transaction infrastructure while leaving A0107 disconnected and fail-closed.

**Architecture:** Pure quote policy computes only pressure that can be reduced without invalidating the provider's prior SHORT/LONG shield decision. A server-only transactional adapter debits exact native stamina before mutating the captured `impact` local. A required, version-gated Mixin injects only immediately before `damageStunShield(...)`, after `ON_STUNNED` cancellation has already returned, and an ASM contract proves that bytecode ordering against the exact provider JAR.

**Tech Stack:** Java 21, NeoForge 21.1.248, Epic Fight 21.17.3.1, Mixin/MixinExtras, ASM 9.7.1, existing main-method contract tests.

**Spec:** P-0035 on `Pendências Técnicas — RPG Skill Tree`; A0107 remains disconnected on this branch.

## Global Constraints
- Work only on `fix/p-0035-impact-stamina-bridge`.
- Do not alter or register A0107.
- Do not use `calculateImpact()` as the transaction commit point.
- Do not reproduce or overwrite `onCalculateDamagePre(...)`.
- Stamina insufficient means zero debit and unchanged impact.
- HOLD/KNOCKDOWN/NEUTRALIZE/FALL fail closed.
- Exact Epic Fight artifact is Modrinth version id `8HHhJt6i`, SHA-256 `8b882554cf10086398340fbdc741819ee72a801a3adce516c7f4768326a39526`.

---

### Task 1: Pure conversion policy
**Files:** create `src/main/java/dev/gustavopere/rpgskilltree/core/ImpactStaminaConversionPolicy.java`; test `src/test/java/dev/gustavopere/rpgskilltree/core/ImpactStaminaConversionPolicyTest.java`.
**Produces:** `Optional<Quote> quote(double impact, double stunShieldSnapshot, PressureClass pressureClass, double fraction)`.
- [ ] Verify existing NONE RED fails for missing production policy.
- [ ] Implement minimum NONE behavior and verify GREEN.
- [ ] Add RED cases for SHORT/LONG boundary preservation, special controls, invalid values, and precision.
- [ ] Implement only the validation and boundary logic required by those tests and verify GREEN.

### Task 2: Exact native stamina transaction
**Files:** create `src/main/java/dev/gustavopere/rpgskilltree/core/ImpactStaminaTransaction.java`; test `src/test/java/dev/gustavopere/rpgskilltree/core/ImpactStaminaTransactionTest.java`.
**Produces:** `tryDebitExactNativeStamina(double cost, NativeStaminaAccess stamina, Runnable mutateImpact)`.
- [ ] Write RED proving full debit precedes mutation and insufficient stamina performs neither.
- [ ] Implement exact non-partial debit with postcondition and exception-safe fail-closed behavior.
- [ ] Add duplicate/nested/two-actor/exception cleanup tests through an invocation guard and verify GREEN.

### Task 3: Restricted Epic Fight bridge and Mixin
**Files:** create `EpicFightImpactStaminaBridge.java`, `ImpactStaminaInvocationGuard.java`, `EpicFightImpactMixinPlugin.java`, `VanillaEntityImpactStaminaMixin.java`, and `rpgskilltree-compat-epicfight-impact.mixins.json`; modify `META-INF/neoforge.mods.toml` only to register the compatibility mixin config.
**Produces:** inert generic request-source registration plus one post-cancel/pre-shield commit hook; no A0107 registration.
- [ ] Write structural RED requiring the injection target to be only `damageStunShield(FF)V` with `shift=BEFORE` and mutable `@Local(name="impact")` capture.
- [ ] Implement the minimum bridge/mixin/plugin to satisfy the structural test.
- [ ] Verify source/victim mismatch, duplicate callback, nested damage, actor isolation, controls, and exception cleanup fail closed.

### Task 4: Exact JAR and addon compatibility contract
**Files:** create `EpicFightImpactStaminaJarContractTest.java`; modify `build.gradle` and CI workflow to execute the isolated contract.
**Produces:** a red gate on provider bytecode drift.
- [ ] Write ASM RED asserting one `calculateImpact`, SHORT/LONG comparison, `ON_STUNNED`, cancel-to-return, `damageStunShield` ordering, same `impact` local downstream use, LVT capture, and commit-point order.
- [ ] Run against exact Epic Fight JAR and make the contract GREEN without weakening assertions.
- [ ] Pin the already-audited addon version set in compatibility policy so unrecognized installed versions disable the Mixin.

### Task 5: Full verification and branch evidence
**Files:** no new behavior; only test/build integration changes if required.
- [ ] Run focused policy, transaction, structure, ASM, and compatibility tests.
- [ ] Run `scripts/test-core.sh`, Gradle `check`, build, and dedicated-server smoke path if available.
- [ ] Commit/push only P-0035 files, record final SHA and CI, and leave A0107 disconnected.
