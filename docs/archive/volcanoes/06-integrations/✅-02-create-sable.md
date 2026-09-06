# Integration Plan — Create, Aeronautics and Sable

**Goal:** make existing air/diving/vehicle equipment participate in atmosphere and pressure.

- [x] Recognize Create Diving Helmet + Backtank as oxygen supply and consume backtank air once through the adapter. Canonical through PR #62 / HEAD `ece0aab59e9c96809dc18a648f882362481e0f0f`, workflow `33210837237` GREEN.
- [x] Investigate installed Aeronautics/Sable pressure API and ship/sub-level APIs; use direct compileOnly integration where stable, guarded reflection only as a last resort.
- [x] Detect sealed dry vehicle interiors when the host exposes reliable state; flooded/breached interiors fall back to external water pressure. Aeronautics 1.3.1 exposes no reliable generic cabin seal/leak/flood contract, so the verified adapter deliberately does not synthesize a sealed interior and falls back to external pressure.
- [x] Reconcile atmospheric-pressure curves so player physiology and vehicle systems do not disagree materially.
- [x] Test absence/version mismatch disables only the adapter, never core startup.

**Acceptance:** diving/ships feel integrated without requiring Volcanoes-specific duplicate equipment.

## Closure evidence

- `SablePressureCompat` pins Sable `2.0.5` and Aeronautics `1.3.1`, gates activation before host linkage and fails closed on version/API mismatch.
- `SablePressureIntegration` uses the verified Sable API directly (`Sable.HELPER` and `DimensionPhysicsData`), projects sub-level positions into the physical level and feeds the resulting atmospheric pressure through `ContextualAtmosphericPressureRuntime`.
- No synthetic protected cabin state is created because the verified Aeronautics version does not expose a stable generic cabin-sealing contract. The fallback therefore remains the canonical external atmosphere/water-pressure path.
- `SablePressureCompatContractTest` covers exact-version activation, absence/mismatch, linkage failure, direct API use and the no-reflection/no-synthetic-seal contract.
- Functional branch HEAD `69b031df88ebd3de34e8a2c2d902280d9293b84e` completed the Stage 06 branch workflows without failure before closure documentation was applied; final PR-head CI is required again before merge.
