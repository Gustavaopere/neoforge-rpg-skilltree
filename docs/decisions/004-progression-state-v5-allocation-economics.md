# ADR 004 — ProgressionState v5 allocation economics foundation

Status: Accepted for allocation/economic persistence; specialization provenance and progression-domain taxonomy remain separate decisions  
Date: 2026-08-28

## Context

The compatibility `ProgressionState` currently persists passive-node ranks but not the economic facts of each acquisition. `ProgressionService.respecNode` therefore recalculates refunds from the *current* `NodePurchaseDefinition.costPerRank()`. A datapack cost change can change the refund for a rank bought under older rules, and a removed node definition can make ordinary reconciliation impossible even though the save still contains the player's rank.

The Phase 1 plan requires v5 to preserve acquisition facts, avoid silent loss of unknown IDs, and separate persisted disk format from rules/economic revision. D004, D005 and D006 must be resolved enough to implement that safely before the compatibility state can be replaced.

## Decision

### 1. Persist acquisition history as compact rank batches

A v5 node allocation is identified by its raw persisted `nodeId` and contains one or more acquisition batches. Each batch records:

- number of ranks acquired under identical terms;
- exact cost paid **per rank**;
- currency ID;
- source tree ID;
- stable provenance ID;
- rules version under which the ranks were acquired.

Adjacent acquisitions with identical economic/provenance metadata may be coalesced into one batch. This keeps persistence compact while retaining exact partial-refund history.

### 2. Refund from historical acquisition facts

Voluntary partial respec removes acquisition history in deterministic LIFO order and refunds the exact historical amount/currency stored in the removed batch. Full removal refunds the sum of retained acquisition facts. Current datapack cost is never used to reconstruct a historical refund for v5 allocations.

This resolves the v5 side of D006 in favor of historical paid cost.

### 3. Unknown/removed nodes are quarantined, not deleted

A node whose current rules definition is missing or cannot be reconciled is moved from the active allocation map to a quarantine entry that retains the complete allocation history plus a diagnostic reason and the rules version that performed quarantine.

Quarantined allocations:

- do not grant runtime effects/access;
- are not silently refunded or deleted;
- remain serializable/exportable;
- may later be restored by alias/migration/admin reconciliation without loss of economic history.

This is the accepted v5 unknown-node policy for D005.

### 4. Legacy v1–v4 decode does not invent historical cost

The raw legacy codec cannot know what each rank actually cost when purchased. Therefore v1–v4 binary decode continues to preserve the legacy rank map as compatibility state. Conversion into v5 allocation facts is a **separate rule-aware migration step**.

That migration may use an explicit migration table or a deliberately documented inferred basis. It must label inferred data as migration provenance. Missing definitions remain preservable/quarantinable rather than causing login failure. The codec itself must not silently substitute the current cost and call it historical truth.

### 5. Disk version and economic rules revision are separate

The binary format version answers “how are bytes interpreted?”. Each acquisition batch separately stores the `rulesVersion` that governed the acquisition. Changing the current rules therefore does not rewrite historical economic facts.

The eventual compatibility codec may advance to v5, but the outer `CanonicalPlayerStateCodec` only needs a format bump if its own envelope layout changes.

### 6. Bounds are technical, not gameplay caps

Persisted node/batch collections must have explicit technical serialization bounds and checked arithmetic. Those bounds protect save integrity; they are not gameplay limits on character level or the fundamental-attribute system.

## Alternatives considered

### Recalculate refund from current datapack cost

Rejected. It makes economic history mutable and creates exploits/penalties when balance changes.

### Store one aggregate paid-cost total per node

Rejected as the canonical model. It cannot reconstruct an exact one-rank refund when per-rank cost changes across rules revisions.

### Delete/refund unknown nodes automatically

Rejected. A missing definition may be temporary, renamed, or caused by an optional provider/datapack change. Silent deletion violates save-safety requirements; automatic refund may also use the wrong currency/economic basis.

### Make v1–v4 decode immediately synthesize v5 costs

Rejected. Decode lacks authoritative historical acquisition facts and should not manufacture them.

## Consequences

- New v5 purchases can be refunded exactly after later balance changes.
- Removed/unknown content no longer needs to block login or force data loss.
- Legacy saves require an explicit rule-aware migration/reconciliation pass before their passive-node ranks become exact v5 economic allocations.
- Runtime purchase/respec must eventually consume this allocation model instead of treating `PassiveNodeProgress` plus current definitions as economic authority.
- D003 (`ProgressionDomain` extensibility) and D007 (specialization provenance/respec) remain open and are not implicitly decided by this ADR.

## Migration/compatibility impact

The first implementation slice introduces Java-pure allocation/quarantine primitives only. A later slice will add a v5 persisted section and explicit migration service while retaining decode support for v1–v4 fixtures.

## Tests/verification required

- exact historical refund after simulated cost/rules change;
- LIFO one-rank refund across multiple acquisition batches;
- batch coalescing under identical metadata;
- checked overflow;
- unknown node quarantine preserves every acquisition fact;
- quarantine/restore round trip;
- later codec v5 deterministic round trip and v1–v4 compatibility fixtures.
