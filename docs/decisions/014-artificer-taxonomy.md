# ADR 014 — Artificer is not a 24th top-level class

Status: Accepted  
Date: 2026-09-12

## Context

Historical plans contain inconsistent Artificer assumptions. The final Tree 2 taxonomy is explicitly 23 class regions, with Engineer as the engineering origin and Technomancer as the arcane-engineering confluence. Adding Artificer as another top-level class would duplicate both territory and gateway semantics without a distinct audited identity.

## Decision

`Artificer` is **not** a top-level class and is not added to the 23-region Tree 2 catalog.

- Engineer owns general engineering/automation identity.
- Technomancer owns explicit magic + engineering confluence.
- Historical Artificer references are treated as inactive/legacy compatibility labels.
- A future Artificer-themed Tree 3 specialization is allowed only through a separate design that proves a distinct fantasy/mechanic; it cannot appear automatically from old Artificer metadata.
- No existing save fact is silently mapped to Engineer or Technomancer solely because the name Artificer is similar.

## Consequences

The player-facing class count remains 23. The design avoids a redundant 24th branch and keeps technology specialization inside Engineer/Technomancer Tree 3.

## Migration/compatibility impact

Any persisted/historical Artificer identifier remains a legacy migration input. It requires an explicit mapping, quarantine or administrative reconciliation rule before being consumed.

## Tests/verification required

- class catalog rejects accidental active `artificer` top-level duplicate;
- legacy Artificer ID remains load-safe as migration input;
- no automatic mapping to Engineer/Technomancer without migration rule;
- UI renders exactly the canonical 23 class anchors.
