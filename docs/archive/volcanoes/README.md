# Volcanoes — Master Plan

## Consolidation status

The active Volcanoes runtime is now part of this unified repository. The former standalone `Gustavaopere/Volcanoes` repository was retired to a three-file migration tombstone after post-consolidation Full Pack, Sonar New Code, and worldgen gates passed. See [`MIGRATION_CLOSEOUT.md`](MIGRATION_CLOSEOUT.md) for the pinned standalone checkpoint, validation runs, tombstone SHA, and ongoing authority rules.

This directory is the canonical memory for the project. A new ChatGPT/Codex session must read this file, `STATUS.md`, `DECISIONS.md`, then the README and task files of the currently active subsystem before changing code.

## Completion convention

A task file starts as `NN-name.md`. When all acceptance criteria are verified and the implementation branch has been merged into `main`, rename it to `✅-NN-name.md`. Do not mark a file complete before tests, CI and merge are green. `STATUS.md` must be updated in the same merge.

## Branch policy

Implementation branches are **not** created all at once. Each branch is created from the latest `main` only after the preceding dependent branch has merged. This avoids stale parallel bases. Every finished round is merged to `main`.

Planned sequence:

1. `round-1-foundation`
2. `feat/01-geology`
3. `feat/02-tectonics`
4. `feat/03-volcano-sites`
5. `feat/04-volcano-lifecycle`
6. `feat/05-volcano-eruptions`
7. `feat/06-atmosphere`
8. `feat/07-pressure`
9. `feat/08-integrations`
10. `feat/09-hardening`

A later branch may be split if a reviewable unit becomes too large, but branch order remains dependency-driven.

## Required engineering method

- NeoForge 1.21.1, Java 21.
- TDD for new behavior: failing test first, verify RED, minimal implementation, verify GREEN, refactor.
- Pure deterministic math belongs in JUnit tests; world interactions belong in NeoForge GameTests/integration tests.
- No mandatory dependency on TFC, TFC Registry API, Terralith, Tectonic, BWG, Destroy, Create, Cold Sweat, RNS, MineColonies, Sable or Aeronautics. Integrations are optional adapters.
- Fail safely when an optional mod changes or disappears.
- Never destroy player/MineColonies structures by default.
- Performance target: no global per-tick scans over loaded chunks; spatial data must be deterministic, cached or indexed.

## Architecture order

`00-foundation` establishes the build, upstream inventory and domain contracts. `01-geology` defines rock/strata data. `02-tectonics` determines plate boundaries and stress. `03-volcanoes` consumes geology and tectonics to create volcanoes, magma, lava, eruptions, ash and geothermal features. `04-atmosphere` models breathable air and pollutants. `05-pressure` models atmospheric/hydrostatic pressure. `06-integrations` connects existing mods without duplicating their systems. `07-hardening` validates compatibility, performance and world persistence.

## Source strategy

TFC Volcanoes 1.21.1 is the behavioral reference for eruption content. Reuse/adapt its implementation where practical, preserving required upstream notices. Do not drag TFC's complete world generator, agriculture, metallurgy or climate stack into this project. Replace TFC-facing queries with Volcanoes-owned interfaces.

ThinAir: ReLived is the reference for NeoForge `LivingBreatheEvent`, air consumption/refill and equipment handling. The project should adapt the useful mechanics into a richer `AtmosphereState`, not preserve the original four-state model as the core.

Sable Water Pressure is a behavioral reference only. Hydrostatic pressure is implemented locally from pressure/depth rather than copied as fixed Y bands.

## Subplans

- [`00-foundation`](00-foundation/README.md)
- [`01-geology`](01-geology/README.md)
- [`02-tectonics`](02-tectonics/README.md)
- [`03-volcanoes`](03-volcanoes/README.md)
- [`04-atmosphere`](04-atmosphere/README.md)
- [`05-pressure`](05-pressure/README.md)
- [`06-integrations`](06-integrations/README.md)
- [`07-hardening`](07-hardening/README.md)
