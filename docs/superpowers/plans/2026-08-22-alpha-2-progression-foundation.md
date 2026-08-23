# Alpha 2 Progression Foundation Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build the dependency-free Alpha 2 progression foundation for Character Level, passive-point provenance, first-kill boss rewards, Final Triads, class confluences, tree path planning, Morph gating, and Apothic integration contracts.

**Architecture:** Extend the existing pure-Java `core` package with immutable records/policies and data-driven definitions. Keep all Minecraft/NeoForge/mod API types out of the core so behavior can be TDD-tested with `javac --release 21`; later runtime adapters translate NeoForge events into these contracts.

**Tech Stack:** Java 21, JSON data resources, Python validation scripts, shell test runner.

**Spec:** `docs/ALPHA2_DESIGN.md`

## Global Constraints
- Target Minecraft/loader: NeoForge 1.21.1.
- Character Level is independent from vanilla Minecraft XP.
- Default max Character Level: 100.
- Default passive points per level: 1.
- Boss passive points are first-kill-only per reward key.
- Cataclysm boss default: 5 points; Apothic boss default: 2; vanilla boss default: 3.
- Final Triad completion requires three capstones at rank 3 (9 points total).
- Default non-adjacent class bridge surcharge: 10 points.
- Identity 2 / Ars Morph and Apothic integrations remain optional adapter targets.

---

### Task 1: Character Level and passive point ledger
**Files:**
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/CharacterLevelCurve.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/CharacterProgress.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/PassivePointSource.java`
- Create: `src/main/java/dev/gustavopere/rpgskilltree/core/PassivePointLedger.java`
- Create/Test: `src/test/java/dev/gustavopere/rpgskilltree/core/Alpha2ProgressionTest.java`
- Modify: `scripts/test-core.sh`

**Interfaces:**
- Produces `CharacterLevelCurve.defaultCurve()`, `levelForTotalXp(long)`, `xpRequiredForLevel(int)`.
- Produces immutable `PassivePointLedger` with `earned`, `spent`, `available`, `award`, `spend`, and `refund`.

- [ ] Write failing tests for level boundaries, max level, provenance, spending and refund.
- [ ] Run tests and verify missing-symbol failures.
- [ ] Implement minimal curve/progress/ledger.
- [ ] Run all core tests.
- [ ] Commit `feat: add character progression and passive point ledger`.

### Task 2: First-kill boss reward registry
**Files:**
- Create: `BossRewardDefinition.java`, `BossRewardRegistry.java`, `BossProgress.java`, `BossRewardResult.java`.
- Test: `Alpha2ProgressionTest.java`.
- Create data: `src/main/resources/data/rpgskilltree/boss_rewards/defaults.json`.

**Interfaces:**
- `BossRewardRegistry.defaults()` resolves exact entity IDs and namespace defaults.
- `BossProgress.creditFirstDefeat(String rewardKey, BossRewardDefinition)` returns points only once.

- [ ] Write failing tests for Cataclysm=5, Apothic=2, vanilla=3 and repeated-kill=0.
- [ ] Verify RED.
- [ ] Implement registry/progress.
- [ ] Verify GREEN.
- [ ] Commit `feat: add configurable first-kill boss rewards`.

### Task 3: Final Triads and class confluences
**Files:**
- Modify: `ProgressionDomain.java`.
- Create: `FinalTriadProgress.java`, `ClassUnlockDefinition.java`, `ClassUnlockResult.java`, `ClassUnlockResolver.java`.
- Create data directory: `data/rpgskilltree/classes/` with initial pure/hybrid definitions.
- Test: `Alpha2ProgressionTest.java`.

**Interfaces:**
- `FinalTriadProgress.complete(domain)` means all three capstone ranks equal 3.
- `ClassUnlockResolver.evaluate(...)` reports eligibility and abnormal bridge surcharge.

- [ ] Write failing tests for 3/3/3 completion, pure class, adjacent hybrid, non-adjacent +10 bridge.
- [ ] Verify RED.
- [ ] Implement minimal class unlock model.
- [ ] Verify GREEN.
- [ ] Commit `feat: add final triads and class confluence unlocks`.

### Task 4: Target path planner
**Files:**
- Create: `SkillGraph.java`, `PlannedPath.java`, `SkillPathPlanner.java`.
- Test: `Alpha2ProgressionTest.java`.

**Interfaces:**
- `SkillPathPlanner.shortestPath(graph, ownedNodes, target)` returns the minimum unowned-node cost path and deterministic tie-break.

- [ ] Write failing shortest-path tests including an already-owned segment.
- [ ] Verify RED.
- [ ] Implement deterministic Dijkstra/BFS for unit-cost nodes.
- [ ] Verify GREEN.
- [ ] Commit `feat: add passive tree target path planner`.

### Task 5: Morph class gating contracts
**Files:**
- Create: `MorphFormCategory.java`, `MorphFormDescriptor.java`, `MorphPermission.java`, `MorphAccessPolicy.java`.
- Test: `Alpha2ProgressionTest.java`.
- Create data: `data/rpgskilltree/morph/form_rules.json`.

**Interfaces:**
- Policy accepts class/node permissions plus category/tags and denies blacklisted/technical entities first.
- DRUID and METAMORPH permissions remain separate.

- [ ] Write failing tests for Druid animal access, Metamorph monster access, and blacklist denial.
- [ ] Verify RED.
- [ ] Implement policy.
- [ ] Verify GREEN.
- [ ] Commit `feat: add druid and metamorph form gating core`.

### Task 6: Apothic gem/affix integration contracts
**Files:**
- Create: `GemSocketAction.java`, `GemPowerModifier.java`, `ApothicIntegrationPolicy.java`.
- Modify: `CanonicalStatCatalog.java`.
- Test: `Alpha2ProgressionTest.java`.
- Create: `docs/integrations/apothic.md`.

**Interfaces:**
- Core contracts represent real gem/socket/affix operations without cloning Apothic inventory.
- Catalog includes canonical Apothic-facing stat keys only where stable generic concepts exist.

- [ ] Write failing tests for socket/gem modifier validation and canonical aliases.
- [ ] Verify RED.
- [ ] Implement contracts/catalog entries.
- [ ] Verify GREEN.
- [ ] Commit `feat: add apothic gem and affix integration contracts`.

### Task 7: Alpha 2 data validation
**Files:**
- Modify: `scripts/validate-data.py`.
- Modify: `src/main/resources/data/rpgskilltree/tree_blueprints/main.json` only to add Alpha 2 region vocabulary/class-confluence metadata without pretending final node layout is complete.

**Interfaces:**
- Validator enforces class domains, triad rules, bridge costs, boss reward values, and optional integration IDs.

- [ ] Add validator assertions that fail against missing Alpha 2 data.
- [ ] Verify RED.
- [ ] Add/repair data resources.
- [ ] Verify `test-core.sh`, `validate-data.py`, and tree generation all pass.
- [ ] Commit `chore: validate alpha 2 progression data`.
