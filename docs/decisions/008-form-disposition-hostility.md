# ADR 008 — Form hostility/disposition is recomputed, not persisted as independent identity

Status: Accepted  
Date: 2026-09-12

## Context

The first-party Form Engine must support real bodies without inheriting arbitrary mob AI/faction state or creating permanent disguise exploits. The old morph design left hostility/disguise memory as an open persistence question.

## Decision

Persist stable form progression facts, but **do not persist temporary hostility/disposition as an independent player-state memory**.

Persistable facts include:

- unlocked form IDs;
- form mastery/progression when defined;
- trait unlock/loadout;
- active form recovery state that is explicitly safe to restore;
- cooldown/duration facts that are designed to survive relog.

Hostility/targeting changes are derived at runtime from an explicit `disposition_profile` capability in the current `FormDefinition` or a validated provider adapter.

Rules:

- no blind inheritance of source entity AI, team, brain, target goals or faction capability;
- transform/revert/relog/dimension change recomputes disposition from canonical form state;
- revert/death clears temporary form-owned disposition overlays;
- provider-native faction integration may be exposed only by an explicit adapter and remains temporary unless a separate non-form gameplay system owns persistent faction reputation;
- unknown or unsupported disposition behavior is neutral/no-grant fail-closed.

## Consequences

A zombie-like body can look/behave mechanically like an approved form without automatically granting every undead faction relationship. A future form that intentionally changes mob reactions must declare that behavior explicitly and test it.

## Migration/compatibility impact

Old session-local hostility/disguise state is not promoted into permanent first-party save data. Form unlocks remain migratable independently.

## Tests/verification required

- transform/revert target reaction recomputation;
- relog/dimension transition with active form;
- death clears temporary overlay;
- provider adapter present/absent;
- no copy of source entity team/AI/faction state;
- multiplayer target behavior does not leak from one transformed player to another.
