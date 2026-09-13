# ADR 006 — Legacy allocations migrate only from explicit historical economic evidence

Status: Accepted  
Date: 2026-09-12

## Context

ADR 004 defines exact paid-cost provenance for new v5 acquisitions, but v1-v4 saves do not contain enough information to reconstruct historical prices by decoding bytes alone. The redesign also changes Tree 2 cost rules, so substituting the new progressive cost as if it were historical truth would corrupt refunds.

## Decision

Legacy v1-v4 allocations are migrated economically only through a **versioned migration catalog** backed by explicit repository/historical rules evidence.

For each legacy allocation that can be resolved, the migration catalog supplies:

- legacy schema/rules selector;
- old node ID and alias mapping;
- rank count/range covered;
- historical or explicitly documented inferred cost basis;
- currency;
- source tree;
- migration provenance ID;
- destination node or quarantine action.

The codec itself never guesses a paid cost.

When a legacy rank cannot be assigned a defensible economic basis:

- preserve it as legacy compatibility state/quarantine with full original rank fact;
- keep login safe;
- do not silently delete it;
- do not silently synthesize a refundable v5 cost;
- automatic voluntary refund of that unresolved historical rank is blocked until an explicit migration/admin reconciliation rule exists.

New v5 acquisitions may proceed with exact provenance independently of unresolved legacy entries, provided the runtime prevents double-counting rank/effect ownership for the same logical node.

## `maxRank` reduction

If migrated historical ranks exceed the current `maxRank`:

- keep only the current allowed ranks active;
- preserve excess acquisitions in quarantine with their economic provenance;
- do not auto-spend or auto-convert the excess;
- an explicit migration/refund rule may later reconcile quarantined excess.

## Consequences

- no false historical refund is manufactured;
- old saves remain login-safe;
- migration work becomes data/evidence work rather than codec heuristics;
- the new progressive pricing model can launch without rewriting old purchase history.

## Migration/compatibility impact

The repository must ship representative v1-v4 fixtures and migration-catalog fixtures before the old compatibility path can be retired. Class/specialization aliases are resolved before deciding destination/quarantine.

## Tests/verification required

- v1/v2/v3/v4 known migration entries;
- missing migration entry preserves state without invented cost;
- alias then migrate;
- migration twice is idempotent;
- max-rank reduction quarantines excess;
- new v5 purchase beside unresolved legacy state does not double-count;
- export/admin reconciliation preserves original legacy facts.
