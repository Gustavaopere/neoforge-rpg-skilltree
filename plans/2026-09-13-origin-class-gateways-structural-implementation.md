# Structural implementation plan — Origin Class, Gateways and Specialist progression

Status: execution plan for PR #428  
Baseline before implementation: `main@594cbeea70cb3e16d698933f35391e05eddd7c60`  
Branch baseline: `aed5ad687063c6a370d3077e4ab670dd5479477c`  
Authority snapshot: physical modlist 2026-09-08, 595 top-level entries, NeoForge 21.1.248, MineColonies 1.1.1381.

This plan implements accepted ADR 016 without reopening product design. It does not start a perk batch and does not authorize merge.

## Invariants

- `CanonicalPlayerState` remains the single persisted player envelope.
- `ProgressionStateCodec` remains a legacy compatibility codec; new authority must not be hidden only inside it.
- Existing `ClassProgressionState`/bridge state remains migration input until explicit gateway migration is complete.
- New persisted/extensible identifiers are namespaced.
- Old saves with no explicit origin decode to **unselected origin**; origin is never guessed silently.
- Origin selection is immutable through ordinary gameplay mutation; admin/migration replacement requires a future explicit path.
- No hard class-count cap and no hard Tree 2 allocation budget may be reintroduced.
- No provider-removed identity is silently remapped.

## Task 1 — Canonical Origin Class persistence

**Files**
- Create `src/main/java/dev/gustavopere/rpgskilltree/core/OriginClassState.java`.
- Modify `src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerState.java`.
- Modify `src/main/java/dev/gustavopere/rpgskilltree/core/CanonicalPlayerStateCodec.java`.
- Add `src/test/java/dev/gustavopere/rpgskilltree/core/OriginClassStatePersistenceJUnitTest.java`.

**TDD acceptance**
1. RED first: tests require empty origin, namespaced IDs, one normal selection, idempotent same-selection, rejection of a second different selection, canonical round-trip, and v2 canonical payload migration to empty origin.
2. GREEN: introduce the smallest immutable `OriginClassState`; bump outer canonical codec version and append a bounded origin section while still decoding v1/v2.
3. Existing two-argument `CanonicalPlayerState` construction remains source-compatible and defaults to empty origin.

## Task 2 — Origin selection mutation boundary

**Files**
- Create/modify a dedicated core mutation service only after inspecting current mutation/query boundaries.
- Tests must prove server-side idempotency, origin-eligible validation from rules data, and confluence-only rejection.

**Gate**
Do not hardcode 16/7 as runtime constants. Eligibility must come from the effective catalog/snapshot introduced by the class-oriented schema.

## Task 3 — Class Gateway canonical state

**Files**
- Extend canonical class identity state instead of creating a parallel class authority.
- Reconcile legacy `ClassProgressionState.unlockedClassIds` / `paidBridgeClassIds` only through an explicit migration boundary.

**TDD acceptance**
- cross-region investment alone does not grant a formal class;
- gateway grant is namespaced and idempotent;
- no hidden class-count cap;
- origin remains distinct from additional formal identities;
- removed/unsupported provider gate fails closed.

## Task 4 — Tree 2 pressure cost and origin affinity

**Files**
- Extend purchase definition/policy only after inspecting `NodeAllocation`, `NodeAllocationBatch`, `NodePurchaseDefinition`, `NodePurchaseMutationService` and current budget path.

**TDD acceptance**
- `pressureTier = floor(P / 20)` from current pressure-weighted paid ranks;
- role base costs 1/2/3/4/4;
- origin affinity discount applies only to explicit origin affinity and never to secondary classes;
- free origin root has pressure weight 0;
- actual paid cost is persisted in existing allocation batches;
- respec returns historical cost;
- no global hard Tree 2 budget blocks purchase.

## Task 5 — Specialist Points foundation

**Files**
- Add a provenance-aware specialist ledger in the canonical envelope or existing compatible canonical progression structure after inspecting current specialization persistence.

**TDD acceptance**
- SP is not Core Progression Points;
- no automatic Character Level drip;
- credits require explicit specialist source/provenance;
- spending is specialization-scoped;
- respec returns SP to the same specialization;
- provider absence never converts historical SP to generic currency.

## Task 6 — Migration and UI vertical slice preparation

- Add explicit migration diagnostics for legacy class/bridge/specialization inputs.
- Preserve ambiguous legacy origin as unselected; do not guess.
- Prepare server-projected data required for two class regions, one shared node, one Class Gateway and one Tree 3 gateway.
- Do not scale to full 23-region UI before the vertical slice passes authority, reconnect and multiplayer validation.

## Verification per completed task

For every task:
1. capture genuine RED evidence from the branch CI/test lane;
2. implement minimum production change;
3. rerun fresh CI on the resulting HEAD;
4. review diff against ADR 016 and current `main`;
5. update STATUS only for the exact structural slice that is actually present;
6. do not declare `IMPLEMENTAÇÃO CONFIRMADA` or merge from this implementation phase.