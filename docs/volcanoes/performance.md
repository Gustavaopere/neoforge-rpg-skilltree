# Performance hardening baseline

This document is the canonical profiling record for Stage 07 Task 02. The goal is to keep normal server ticks bounded in a 500+ mod pack without turning runner-dependent wall-clock timings into flaky release gates.

## CI contract

`Performance Hardening Acceptance` runs the focused performance contracts on Java 21 and uploads `build/performance/volcano-sites.csv` plus the focused JUnit reports as an artifact.

Hard acceptance is structural:

- active-volcano scheduling may process at most 8 due sites in one server tick;
- dormant sites use the coarse 24,000-tick cadence rather than entering every-tick work;
- Atmosphere breathing samples only the spatial bucket covering the sample position; distant registered sources do not enlarge a local lookup;
- pressure depth resolution remains bounded and its cache is observable through miss/hit counters;
- ash deposition, specialized lava work and eruption terrain mutations have explicit positive server-config budgets;
- block-mutation counters increment only after successful world mutations.

Elapsed nanoseconds are recorded for trend analysis only. They are deliberately not used as an absolute pass/fail threshold because GitHub-hosted runner hardware and contention are not stable enough for a defensible fixed latency budget.

## Instrumented hot paths

`PerformanceProfiler` exposes monotonic counters for:

- Atmosphere samples and local candidate-source visits;
- active volcano lifecycle updates;
- tectonic/plate samples;
- pressure depth-query cache misses and cache hits;
- successful volcanic world block mutations.

The counters are lightweight `LongAdder`-backed diagnostics and can be snapshotted/reset by tests and future operational tooling. They do not change gameplay authority.

## Configurable mutation budgets

The server config owns these defaults under `performance.budgets`:

| Budget | Default | Runtime authority |
| --- | ---: | --- |
| `ashDepositionBlocksPerTick` | 64 | global eruption block-work scheduler / ash deposition |
| `lavaSpecializationBlocksPerTick` | 32 | canonical configured `VolcanicLavaController` factory |
| `eruptionTerrainMutationsPerTick` | 8 | bomb + pyroclastic terrain mutation runtime |

All values must remain positive. Loader-neutral tests that instantiate runtime components before NeoForge has loaded the config use the same defaults; a loaded server config remains authoritative in normal runtime.

## 0 / 1 / 10 / 50-site baseline

The benchmark simulates 24,000 scheduler ticks with 256 representative loaded chunks and checks exact scheduler cardinality plus the 8-updates-per-tick ceiling. The final pre-documentation acceptance run was `33341293339` on implementation head `a25fb9d1f13c8eb527e3d91b1598a47e44ca54ef`.

| Active sites | Representative loaded chunks | Simulated ticks | Due updates | Elapsed ns | ns/tick |
| ---: | ---: | ---: | ---: | ---: | ---: |
| 0 | 256 | 24,000 | 0 | 7,235,471 | 301.478 |
| 1 | 256 | 24,000 | 20 | 7,497,339 | 312.389 |
| 10 | 256 | 24,000 | 198 | 7,429,298 | 309.554 |
| 50 | 256 | 24,000 | 958 | 5,244,319 | 218.513 |

The non-monotonic wall-clock values are expected for a small JVM microbenchmark on shared CI infrastructure. The invariant that matters for acceptance is that work remains scheduler-bounded and does not become an `O(loadedChunks × entities)` normal-tick scan.

Artifact: performance-hardening-a25fb9d1f13c8eb527e3d91b1598a47e44ca54ef, GitHub Actions artifact ID `9740626783`.

## Regression evidence

TDD started with run `33340567895`, which failed because `PerformanceProfiler` and `PerformanceConfig` did not yet exist.

The first integrated candidate then exposed a real loader-neutral bootstrap issue in Volcano lifecycle tests: run `33341068493` compiled successfully but failed two existing lifecycle contracts because NeoForge `IntValue#get()` was called after the spec had been defined but before a config file was loaded. The fix keeps runtime config authority while falling back to the same defaults only during that pre-load state.

After the fix, `Performance Hardening Acceptance` run `33341293339` is GREEN and the full `Volcanoes CI` run `33341293365` is GREEN through unit tests, diff sanity, NeoForge build, built-JAR verification, Eruption GameTests and dedicated-server smoke.

## Release interpretation

Task 02 does not claim a universal milliseconds-per-tick guarantee. It establishes reproducible bounded-work contracts, observable hot paths, a stored 0/1/10/50-site timing baseline and configurable mutation budgets. Future performance work should compare against the stored CSV while treating structural regressions as hard failures and timing drift as evidence requiring investigation.