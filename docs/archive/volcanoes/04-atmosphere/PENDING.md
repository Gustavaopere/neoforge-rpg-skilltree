# Stage 04 Atmosphere — Pending Contracts

This file records only dependency-accurate blockers that remain after the canonical Stage 04 core and completed cross-stage handoffs. These are not generic TODOs and do not authorize speculative adapters.

Canonical closures intentionally absent from this list:
- `ATM-03-ASH-WIRING` — closed through PR #31;
- `ATM-03-GEOTHERMAL-WIRING` — closed through PR #48;
- `ATM-03-ERUPTION-EMISSION` — closed through canonical volcanic-gas lifecycle/projection PR #67, merge `5813d51f99475f6221cba9346bf7234dcf2daf1a`, with current-main workflow `33224094364` GREEN;
- `ATM-05-PRESSURE-WIRING` — closed through canonical Pressure reconciliation PR #57 and Atmosphere pressure wiring PR #59;
- `ATM-06-CREATE-RESPIRATION` — closed through exact Create 6.0.10 integration PR #62, including shared Pressure/Respiration protection-use transactions and exact-once host-air debit semantics.

## ATM-06-DESTROY-ADAPTER

ID: ATM-06-DESTROY-ADAPTER
Blocked task: Task 04 concrete Destroy pollution authority adapter.
Depends on stage: 06 — Integrations.
Missing contract: The exact installed Destroy 0.4.1 public `PollutionHelper` surface has been verified, but the remaining problem is source provenance/ownership for safe `sampleExternalOnly` after Volcanoes publishes into aggregate host pollution, plus Stage 06 ownership of adapter installation. Destroy also does not expose a distinct SO2 pollution type, so unsupported semantic channels cannot be invented.
Expected semantics: When Destroy is installed and selected as authority, each Volcanoes emission is published exactly once through Destroy. Atmosphere reads only external/industrial pollution back through its single external-contribution slot and must not re-add Volcanoes' own already-published contribution. Greenhouse/ozone channels remain host pollution semantics unless an explicit Atmosphere mapping exists.
Current implementation: Canonical Stage 04 already contains `PollutionAdapter`, `PollutionCoordinator`, five-component `PollutionLoad`, standalone fallback, bounded anti-double-count/retry routing, `AtmosphereExternalContributionProvider` and `PollutionAtmosphereReadbackProvider`. No reflection or concrete Destroy dependency exists in the core.
Blocked tests: Exact Destroy 0.4.1 present/absent/mismatch tests; one-emission-exactly-once publication; external acidification/smog readback without reflection; proof that `sampleExternalOnly` excludes Volcanoes' own publication; supported acid-rain material/protection behavior only through verified host surfaces.
Files expected to wire later: Stage 06 Destroy adapter/runtime-installation files; `PollutionAdapter.java`; `PollutionAtmosphereReadbackProvider.java`; `AtmosphereExternalContributionProvider.java`; exact-version integration tests.
Removal condition: Stage 06 implements and installs a supported Destroy 0.4.1 adapter with provenance-safe external readback, and all present/absent/mismatch plus anti-double-count integration tests pass.
