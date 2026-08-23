# Alpha 2 Runtime Progression Model Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Connect Alpha 2's independent Character XP, passive-point ledger, boss rewards and class unlocks into a single deterministic progression state, then add class-specialization and Warlock pact contracts that runtime adapters can drive.

**Architecture:** Keep an immutable dependency-free progression aggregate in `core`. Runtime integrations will translate NeoForge/mod events into `CharacterXpAward`, boss credits, class unlocks, mastery awards and specialization choices.

**Tech Stack:** Java 21, JSON resources, Python validation.

**Spec:** `docs/ALPHA2_DESIGN.md`

## Global Constraints
- Vanilla XP is not consumed or replaced.
- One passive point per Character Level gained.
- Boss points preserve BOSS provenance and first-credit semantics.
- Class trees appear after completed Final Triads.
- Specializations come after class progression and may depend on actual mod mastery lanes.
- Warlock pact selection is modeled as a class-tree choice, not as an external class label.

---

### Task 1: Aggregate progression state and XP application
- Create `CharacterXpAward`, `ProgressionState`, `ProgressionService`.
- TDD level-up point grants, multi-level jumps and boss point credits.
- Commit `feat: connect xp and boss rewards to progression state`.

### Task 2: Specialization unlock contracts
- Create `SpecializationDefinition`, `SpecializationUnlockResult`, `SpecializationResolver`.
- Add initial Iron school, Ars composition, Epic Fight weapon and technology specializations as data.
- TDD class + mastery + tag requirements.
- Commit `feat: add post-class specialization unlock model`.

### Task 3: Warlock pact choice contracts
- Create `ClassChoiceDefinition`, `ClassChoiceState`, `ClassChoicePolicy`.
- Add pact data for Blade, Familiar, Grimoire, Souls and Blood.
- TDD one-major-pact default and explicit extra-choice capacity.
- Commit `feat: add warlock pact progression model`.

### Task 4: Validation and documentation
- Extend validator for specialization/pact data.
- Add integration notes describing runtime mapping for Iron's, Ars, Epic Fight, Create/Oritech/AE2, Identity 2/Ars Morph and Apothic.
- Run all verification and commit `docs: define alpha 2 runtime adapter boundaries`.
