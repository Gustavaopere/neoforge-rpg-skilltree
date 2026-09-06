# ✅ Volcano Plan — Eruptions

**Status:** implemented, reviewed, corrected and verified on `feat/03-eruptions`.

**Goal:** port/adapt the rich eruption lifecycle from TFC Volcanoes without TFC runtime dependencies.

**Implemented types:** `EruptionController`, `EruptionPhase`, `EruptionProfile`, `EruptionEvent`, `EruptionSignal`, `EruptionScheduler`, `EruptionSink`, `EruptionDispatcher`, `EruptionRuntimeCoordinator`, `EruptionEffectRuntime`, `VolcanoLifecycleStep`.

- [x] Detailed eruption state is persistent and bound to the stable volcano UUID through `VolcanoSavedData`.
- [x] Phase sequencing is deterministic: `PRECURSORS → OPENING → SUSTAINED → WANING → DORMANT`.
- [x] `EruptionProfile` is derived from persisted magma chamber pressure, gas fraction, temperature and composition rather than a random one-shot event.
- [x] The existing `VolcanoTickScheduler` remains the sole cadence owner. Detailed eruption work is advanced only when the existing lifecycle update is due; no parallel eruption scheduler was introduced.
- [x] `VolcanoLifecycleStep` is the single bridge from coarse magma evolution to the detailed eruption runtime, persisting site/chamber state before dispatching physical eruption signals.
- [x] Global block/entity work is bounded per server tick by `EruptionScheduler`; overflow is retained as bounded numeric backlog per eruption and explicitly cleared when the eruption is retired.
- [x] `EruptionDispatcher` isolates failing consumers and deterministically partitions each `WorkGrant` across the registered sink snapshot, so multiple consumers cannot multiply the scheduler's global mutation allowance.
- [x] `VolcanoLifecycleRuntime.registerEruptionSink` / `unregisterEruptionSink` exposes the live authoritative eruption stream to future lava, ash, gas, seismic and integration adapters without hard-wiring those downstream implementations into this task.
- [x] Leaving coarse `ERUPTING` retires the detailed event and clears deferred eruption work.
- [x] No TFC runtime dependency and no premature physical ash/gas/pyroclastic implementation were introduced.
- [x] A NeoForge `gameTestServer` acceptance test proves save/reload continuity and end-to-end completion on a dedicated GameTest server.

## Verified cadence contract

`VolcanoLifecycleEruptionWiringContractTest` proves that the pre-existing lifecycle scheduler owns detailed eruption cadence:

- tick `199`: no detailed update;
- tick `200`: the eruption exists in `PRECURSORS`;
- tick `399`: no additional update;
- tick `400`: the same persisted eruption advances by another 200 ticks.

## TDD / CI evidence

### Core lifecycle integration

- RED HEAD: `f0eebf8461c43ea624ee79c7d2ae50fcc7b83d7c`.
- RED workflow: `32970027530` — failed at `compileTestJava` because `VolcanoLifecycleStep` and the test-visible runtime boundary did not yet exist.
- GREEN HEAD: `a6fcddcfe1013d0e35a34685539e8fd6cb6a2aae`.
- GREEN workflow: `32976746966` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke passed.

### Shared runtime sink boundary

- RED HEAD: `81f8801e4bda23a9839174e7e0abfdac0a8beb26`.
- RED workflow: `32977156372` — failed because the live runtime did not yet expose sink registration.
- GREEN HEAD: `f2e0554d6fa34cbe1fc16f4845d0883795e042cb`.
- GREEN workflow: `32977464513` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke passed.

### Review correction — aggregate sink budget

Automated review of PR #21 found a P1 issue: the first dispatcher implementation delivered the same complete `WorkGrant` to every sink, allowing multiple world-mutating consumers to multiply the scheduler's advertised global budget. This was corrected before merge.

- RED HEAD: `f4c6290c2d5e6727622e415f23adc841549adf8e`.
- RED workflow: `32978356471` — failed in unit tests on the new aggregate-budget contract.
- GREEN HEAD: `15a550fcc2c1d142b5b5f54d5e2ee089e4801873`.
- GREEN workflow: `32978631528` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke passed.

`EruptionDispatcherContractTest` now proves that all grant components are partitioned deterministically across consumers and that aggregate delivered allowance never exceeds the scheduler grant. A failing sink loses only its own partition; the allowance is not redistributed.

### Dedicated GameTest acceptance

- Initial GameTest HEAD: `a619524a23de522ff3b0639a0f97001dabdff404`.
- RED workflow: `32979007123` — project evaluation failed because NeoGradle `7.1.26` does not expose the stale `setForceExit false` DSL call used by the first GameTest run configuration.
- Final GREEN HEAD: `c93c0d1b8671509f9031840eaaa578be6e93607f`.
- Final PR workflow: `32980078604` — unit tests, diff sanity, NeoForge build, built-JAR verification, `runGameTestServer` and dedicated-server smoke all passed.

`EruptionGameTests.eruptionResumesAfterPersistenceRoundTrip` starts an eruption, advances it, performs an NBT save/reload round-trip, rebuilds runtime state from the restored data, then proves the original eruption continues through all phases to `DORMANT` without changing its original `startedTick`.

## Merge verification

- Functional PR: #21 — `feat: implement persistent bounded eruption runtime`.
- Final feature HEAD: `c93c0d1b8671509f9031840eaaa578be6e93607f`.
- Merge SHA on `main`: `6a1ba7a8df321af6745ae94da2c2e88a80c456d9`.
- Post-merge `main` workflow: `32980592095` — GREEN, including unit tests, diff sanity, NeoForge build, built-JAR verification, eruption GameTest server and dedicated-server smoke.

## Acceptance

The eruption lifecycle now runs end-to-end on the authoritative server path, persists across save/reload, resumes the same eruption after restoration, completes deterministically without TFC installed, emits immutable physical signals for downstream adapters, and keeps aggregate block/entity work bounded even with multiple consumers.

Physical ash deposition, volcanic bombs and pyroclastic flows remain intentionally outside this task. They are owned by `05-ash-pyroclastics.md` and must consume the eruption sink boundary established here rather than create a second eruption lifecycle.
