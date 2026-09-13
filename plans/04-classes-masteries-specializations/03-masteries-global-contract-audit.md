# Stage 04.03 — Global Mastery Contract Audit

Status: **runtime/global contracts confirmed; numeric balance remains intentionally open**.

This audit supplements `03-masteries.md`. It does not replace the source-specific closures already recorded there and does not declare Stage 04.03 complete while concrete balance values remain unapproved.

## Scope

The provider/source audit has already closed the currently implemented canonical Mastery producers: physical BOW/CROSSBOW/FIST, Epic Fight, Iron's Spells, Ars Nouveau, Goety, Create, Malum, and Eidolon: Repraised. This pass verifies the global contracts shared by those producers: persistence, owner sync, threshold consumption, idempotency/deduplication, and the fail-closed Mastery→class-investment boundary.

## Persistence — CONFIRMED

- `PlayerProgressionRuntime` mutates the canonical persisted player progression state.
- `ProgressionStateCodec` serializes `state.mastery().experience()` and bounded `creditedAwards()` receipts and reconstructs `MasteryState` on decode.
- `MasteryAwardIdempotencyTest.receiptsSurviveProgressionCodecRoundTrip` explicitly proves that both Mastery XP and replay receipts survive the compatibility codec round-trip.
- No parallel Mastery persistence ledger was found in this audit.

## Owner sync / UI snapshot — CONFIRMED

- `ModNetworking.syncToOwner` serializes the full `ProgressionState` with `ProgressionStateCodec` and sends it through `ProgressionSyncPayload`.
- `ProgressionSyncPayload.fromState` uses the same codec rather than a reduced Mastery-free projection.
- `ClientProgressionState` owns the client-side synchronized full `ProgressionState` snapshot consumed by the skill-tree client runtime.
- Consequently the Mastery map persisted by the server is present in the owner snapshot used by client-side tree resolution; there is no independent client-authored Mastery authority.

## Threshold consumption — CONFIRMED

- `NodeAccessRequirement.requiredMastery` is explicit data, not inferred from a provider or lane name.
- `NodeAccessResolver` requires `state.mastery().experience(lane) >= configuredThreshold`.
- Existing specialization data already exercises concrete gate thresholds, e.g. `epicfight:sword = 80` and `irons:fire = 100`.
- `NodeAccessRequirementTest.masteryThresholdIsRequiredExactly` now characterizes the global boundary: 79 fails an 80 requirement; 80 satisfies it.

These existing per-node specialization thresholds are consumed as authored data. This audit does **not** generalize them into a new global XP curve or infer new thresholds for other lanes.

## Semantic deduplication / idempotency — CONFIRMED

- `MasteryAwardService` supports replay-safe awards through persistent receipts.
- `MasteryAwardIdempotencyTest` proves identical replay keys are no-ops, conflicting reuse fails closed, duplicate batch entries do not double-award, and repeatable provenance may accumulate only when it is intentionally not replay-keyed.
- Source-specific closures in `03-masteries.md` additionally use persistent discovery keys, provider-native one-shot claims, causal roots, and confirmed post-result boundaries as appropriate to the provider.
- Therefore the global contract is one semantic action → at most one corresponding replay-safe mutation; provider-specific adapters remain responsible for defining the semantic action boundary.

## Mastery → emergent class investment — ARCHITECTURE CONFIRMED, NUMERIC CONTENT OPEN

- `MasteryInvestmentMetadataReloader` loads explicit `data/*/mastery_investments/*.json` metadata.
- `MasteryInvestmentMetadataPolicyContract` requires canonical lanes, positive explicit thresholds, deterministic ordering, non-no-op contributions, and fail-closed duplicate lane+threshold entries.
- Empty metadata produces no inferred fallback contribution.
- The repository currently does not ship an approved default `data/rpgskilltree/mastery_investments` balance catalog.

This absence is intentional under the existing Stage 04.03 contract: no namespace/provider heuristic is allowed to fabricate class investment weights.

## Remaining blocker — NUMERIC BALANCE

The following values remain deliberately unapproved and therefore are **not** invented by this audit:

- global/per-lane Mastery XP curves;
- gameplay caps or soft caps;
- additional specialization thresholds not already authored in data;
- `mastery_investments` thresholds and `domain_weights` used by emergent class resolution;
- any normalization/conversion ratio between heterogeneous providers.

Until those values are explicitly designed/approved, `03-masteries.md` must keep `Definir curvas, caps e thresholds` open and must not claim final Stage 04.03 completion.

## Global checklist after this audit

- [x] Current implemented Mastery sources have semantic source closures documented in `03-masteries.md`.
- [x] Cancellation/failure and provider-specific fail-closed behavior are documented per source.
- [x] Semantic deduplication/idempotency has persistent global contracts and source-specific causal protections.
- [x] Mastery XP and replay receipts persist through the canonical progression codec.
- [x] Full progression owner sync carries Mastery to the client-owned snapshot used by the tree UI/runtime.
- [x] Authored Mastery thresholds are enforced exactly by `NodeAccessResolver`.
- [x] Mastery→class investment is explicit-data-only and fails closed when metadata is absent.
- [ ] Concrete curves, caps, class-investment thresholds and weights require a separate approved balance decision.

No production behavior or balance value is changed by this audit.