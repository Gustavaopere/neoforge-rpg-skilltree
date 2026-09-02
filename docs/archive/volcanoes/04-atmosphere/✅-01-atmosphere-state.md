# Atmosphere Plan — AtmosphereState and Spatial Fields

**Goal:** replace coarse GREEN/BLUE/YELLOW/RED quality with a composable vector environment.

**Planned fields:** total pressure, oxygen fraction, CO2, SO2/acid gases, generic toxic gases, particulates, smoke/smog, humidity, thermal modifier.

- [x] Unit-test normalization and derived oxygen partial pressure.
- [x] Implement immutable `AtmosphereState` and `AtmosphericSource` contribution model.
- [x] Store baseline dimension/altitude state plus indexed local sources by chunk/region; do not scan 5x5 chunks on every entity breath.
- [x] Add bounded diffusion/decay cadence for pollutants and ash with configurable persistence.
- [x] Synchronize only compressed player-relevant state to clients.

**Acceptance:** hundreds of registered sources can be sampled locally without global scanning; different hazards remain distinguishable in one state.

## Completion evidence

- Canonical Stage 04 core landed through PR #31, merge `bc91fc16a63a7422907c778a8ee9b197d1d056fe`; exact-final workflow `33165968236` passed unit tests, diff sanity, NeoForge build, JAR verification, GameTests and dedicated-server smoke.
- `AtmosphereState` is an immutable record with normalized fractions, non-negative concentrations and explicit `oxygenPartialPressureAtm()`; `DomainContractTest.atmosphereClampsInputsAndDerivesOxygenPartialPressure` verifies the normalization/derived-pressure contract.
- `AtmosphereContributionCompositionTest` verifies composable, deterministic local vector aggregation across distinct atmospheric channels.
- `AtmosphericSourceIndexTest.hundredsOfFarSourcesDoNotExpandOneLocalLookup` registers 500 remote sources plus one local source and proves the local candidate set remains bounded to the local source; the allocation-free indexed reducer preserves exact 3D attenuation.
- `AtmosphereSpatialBoundednessTest` verifies hard radius/source-capacity bounds and transactional rejection of invalid spatial replacements.
- `AtmosphereRuntime` evolves dynamic sources every 20 server ticks with a hard budget of 64 source updates per interval; `AtmosphereFieldTest` and `AtmosphereTransportTest` verify bounded diffusion/decay and transport isolation without requiring a weather mod.
- `AtmosphereConfigContractTest` verifies configurable persistence and caps persisted sources at the same 16,384-source runtime maximum.
- `AtmosphereSnapshotTest` verifies the player-relevant atmosphere vector round-trips within quantization bounds in at most 18 encoded bytes.

Stage 05 atmospheric-pressure authority remains a separate cross-stage wiring contract: this completed task owns the composable atmosphere state/spatial field and a neutral baseline-provider port, not pressure physics itself.
