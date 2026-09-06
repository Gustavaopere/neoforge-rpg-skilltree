# Atmosphere Plan — Pollution and Acid Rain

**Goal:** integrate volcanic pollution with Destroy while retaining safe standalone behavior.

- [x] Define an internal pollution adapter interface for acidifying gases, smog/particulates, greenhouse contribution and ozone-affecting pollutants if exposed by installed Destroy version.
- [x] When Destroy is present, translate volcanic emissions into its supported pollution mechanisms and read relevant local pollution back into `AtmosphereState` where API-safe.
- [x] Standalone fallback applies respiratory/environmental effects but does not create a duplicate industrial progression system.
- [x] Acid rain trigger derives from atmospheric acidifying load plus precipitation, with Destroy material/protection behavior left to Destroy when its adapter is authoritative.
- [x] Add integration/contract tests proving one volcanic emission is not counted twice by internal and Destroy paths.

**Acceptance:** one coherent pollution source drives both volcanic air hazards and Destroy acid-rain/smog behavior without double application.

## Closure notes

- Stage03 gas and ash lifecycle metadata are observed by `VolcanicPollutionRuntime`; publication does not consume eruption work budget.
- Gas pulses map to Destroy `ACID_RAIN` and `GREENHOUSE`; ash smoke maps to Destroy `SMOG`. Volcanoes particulate exposure remains in Atmosphere because verified Destroy 0.4.1 exposes no distinct particulate pollution type. `OZONE_DEPLETION` is adapter-supported but remains zero until a canonical volcanic ozone-affecting source exists.
- Pulse UUIDs are deterministic per source and 20-tick Atmosphere interval. A retry in the same interval is deduplicated; the next interval receives a fresh pulse.
- Destroy publication is reflective and exact-version-gated, so Destroy classes never enter Volcanoes' linkage surface when the optional host is absent or mismatched.
- Destroy stores aggregate pollution without source provenance. Its adapter therefore advertises publication authority but deliberately does **not** advertise external-only readback. `AtmosphereState` skips that aggregate readback instead of re-importing Volcanoes' own pollution and double-counting it. There is currently no API-safe source-exclusive Destroy value to read back.
- The existing gas/ash Atmosphere bridges remain the standalone environmental/respiratory path. The pollution coordinator's standalone callback is intentionally a no-op because injecting the same source a second time would duplicate those hazards.
- Destroy's own pollution system remains responsible for acid-rain material/protection semantics when active; Volcanoes only publishes to the verified host pollution type and does not clone Destroy's industrial/protection rules.
- Component-level host writes are idempotent across partial failures: a successful `acid_rain`, `smog`, `greenhouse` or `ozone_depletion` mutation is not applied again when another component of the same pulse fails and retries.
- A transient Destroy publication failure stops only the current pollution interval. The authoritative coordinator remains installed for the loaded level, and the coordinator's failed-emission rollback allows the same pulse to retry without permanently disabling later Destroy pollution.

## Verification

- PR: #75 (`feat/04-destroy-pollution-runtime`).
- Causal RED: `a348e14735ad3faab2e0a938f7d6cb5818afd86f`, workflow `33244608232`; 624 tests ran and only `VolcanicPollutionRuntimeFailureRecoveryTest.transientHostFailureDoesNotPermanentlyDisablePollutionForLevel()` failed, proving the transient-failure regression before the production fix.
- Corrected implementation HEAD: `bb8cae2fccacef5082c85945c951cd95f689520c`, workflow `33244687292` GREEN through 625 unit tests, diff sanity, NeoForge build, built-JAR verification, Eruption GameTests and dedicated-server smoke.
- Present/absent/mismatch gating, anti-double-count behavior, component-level partial-failure retry and level-level transient-failure recovery are covered by focused unit/contract tests.
- The verified 1.21.1 Destroy port does not publish a downloadable release artifact and documents a build-time local migration-toolkit requirement, so no exact-host binary GameTest is claimed here. The reflection contract is pinned to the verified 0.4.1 API surface and fails closed if that surface cannot be resolved.
