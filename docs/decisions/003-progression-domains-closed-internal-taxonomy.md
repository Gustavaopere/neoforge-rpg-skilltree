# ADR 003 — Progression domains remain a closed internal taxonomy

Status: Accepted  
Date: 2026-09-12

## Context

The legacy architecture exposes 11 `ProgressionDomain` values and historically used them as the visible topology of the main tree. The approved redesign changes the player-facing topology to 23 connected class regions. Providers/addons still need extensible contribution/mastery metadata, but adding arbitrary new visible macro domains would reintroduce a competing taxonomy and destabilize persisted class rules.

## Decision

`ProgressionDomain` remains a **closed internal enum/taxonomy** for compatibility, migration, diagnostics and any runtime logic that still benefits from it.

- Domains are not the player-facing topology of Tree 2.
- External providers/addons do not register new `ProgressionDomain` values.
- Extensibility happens through namespaced node tags, class affinities, mastery lanes, provider capabilities, specialist IDs and explicit metadata.
- No class identity may be inferred from domain name, node coordinate, provider namespace or display text.
- Future removal of the enum requires its own versioned migration; it is not required by the class-oriented redesign.

## Consequences

- ProgressionState v5 does not need namespaced dynamic domain IDs.
- Existing domain-tagged content can remain a compatibility input while Tree 2 migrates to class-affinity metadata.
- Addons can extend gameplay without changing the closed macro taxonomy.
- UI must not render 11 domain sectors as authoritative class structure.

## Migration/compatibility impact

Legacy `required_completed_domains` and domain contribution tags remain readable during migration. New class gateways use explicit class-affinity/contribution requirements rather than completed-domain inference.

## Tests/verification required

- unknown external domain IDs fail validation rather than mutate the enum;
- namespaced class/mastery/provider metadata remains extensible;
- class resolution is invariant under node position/name/provider namespace;
- legacy domain-tagged fixtures remain readable during migration.
