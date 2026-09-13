# ADR 007 — Specialization provenance is explicit and player-choice identities are not provider-derived

Status: Accepted  
Date: 2026-09-12

## Context

The legacy specialization catalog mixes provider schools/composition lanes/weapon styles with character specialization identity. That made derived specialization state dependent on provider presence/mastery and does not fit the new class-centric Tree 3.

ProgressionState v5 also needs enough provenance to distinguish a chosen Tree 3 identity from a provider discipline, node grant or legacy migration fact.

## Decision

Every persisted specialization/unlock fact records an explicit provenance kind and stable source ID.

Minimum provenance kinds:

- `PLAYER_CHOICE_GATEWAY` — class-centric Tree 3 identity explicitly activated by the player;
- `MILESTONE_GRANT` — specialist-point/unlock fact granted by an explicit milestone;
- `NODE_GRANT` — compatibility/content grant from a node;
- `PROVIDER_DISCIPLINE` — provider school/composition/style mastery; not a Tree 3 identity;
- `EXTERNAL_GRANT` — explicit integration-owned grant;
- `MIGRATION_LEGACY` — state preserved from older schemas/catalogs.

The 25 old provider-centric definitions migrate as discipline/legacy facts according to the readiness audit. They never auto-create one of the new class-centric Tree 3 identities.

Tree 3 identity acquisition uses `PLAYER_CHOICE_GATEWAY` after all class/attribute/mastery/provider/SP requirements are satisfied.

## Respec semantics

- Tree 3 node respec returns Specialist Points to the **same specialization ledger**.
- Respec does not convert specialist-scoped SP to generic Core Progression Points or another specialization.
- Removing the gateway/class requirement disables dependent Tree 3 effects/nodes and reconciles according to their paid provenance; it does not rewrite the historical acquisition source.
- Provider discipline loss/absence disables content that requires it but does not masquerade as the player unchoosing a specialization.
- `MIGRATION_LEGACY` state is retained until an explicit migration/alias/reconciliation rule consumes it.
- Unknown specialization IDs are quarantined/retained, never silently deleted.

## Consequences

- Character identity and provider mechanics no longer share one ambiguous field.
- A player can satisfy the same Iron's/Ars/Epic discipline for several class specializations without cloning provider state.
- Removing a provider cannot silently delete a chosen RPG identity; provider-dependent effects remain fail-closed until eligibility returns or an explicit migration is applied.

## Migration/compatibility impact

Legacy `industrialist`, `logistician`, `prospector` and the 25 provider-centric IDs remain readable. New class-centric specialist IDs use namespaced persisted form according to ADR 015. Legacy unnamespaced IDs are compatibility inputs only.

## Tests/verification required

- round trip of every provenance kind;
- explicit choice survives unrelated provider reload;
- provider discipline cannot auto-grant Tree 3 identity;
- provider removal disables dependent effect without data loss;
- respec refunds SP only to owning specialization;
- unknown/removed specialist ID quarantine/restore;
- legacy ID migration is idempotent.
