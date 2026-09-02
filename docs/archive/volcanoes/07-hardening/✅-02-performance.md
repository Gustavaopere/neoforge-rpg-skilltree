# Hardening Plan — Performance

**Goal:** keep simulations viable in a 500+ mod pack.

- [x] Add counters/profiling hooks for atmosphere sampling, active volcano updates, plate sampling, pressure depth queries and block mutations.
- [x] Benchmark 0, 1, 10 and 50 active volcano sites with representative loaded chunks.
- [x] Verify dormant sites do not tick every server tick.
- [x] Verify breathing does not scan arbitrary surrounding chunks; local source lookup is indexed/cached.
- [x] Set configurable per-tick budgets for ash deposition, lava specialization and eruption terrain work.

**Acceptance:** satisfied by bounded structural contracts plus the stored profiling baseline in `docs/performance.md`. Normal ticks do not introduce an unbounded `O(loadedChunks × entities)` loop. Timing values are recorded as diagnostic evidence rather than hardware-dependent CI thresholds.

## Canonical evidence

- TDD RED: `33340567895` — profiling/config contracts absent.
- Integrated regression: `33341068493` — two existing lifecycle tests caught pre-NeoForge-config-load access; corrected without weakening runtime config authority.
- Focused performance gate: `33341293339` GREEN on implementation head `a25fb9d1f13c8eb527e3d91b1598a47e44ca54ef`.
- Full Volcanoes CI: `33341293365` GREEN through unit tests, diff sanity, NeoForge build, built-JAR verification, Eruption GameTests and dedicated-server smoke.
- Benchmark artifact: `performance-hardening-a25fb9d1f13c8eb527e3d91b1598a47e44ca54ef`, artifact ID `9740626783`.

## Closed contract

The performance hardening layer now provides monotonic diagnostics for the five requested hot paths, explicit local/indexed Atmosphere sampling proof, pressure cache visibility, a 24,000-tick dormant scheduling regression, 0/1/10/50-site scheduler baselines and configurable server-side mutation budgets with defaults of 64 ash block-work, 32 specialized lava block-work and 8 eruption terrain mutations per tick.

Task 03 (`03-world-upgrade.md`) remains unopened and is not part of this task.