# Hardening Plan — Compatibility Test Matrix

**Status:** ✅ COMPLETE — closure candidate in PR #86; becomes canonical when merged to `main`.

**Goal:** prove base and optional integration combinations.

- [x] CI base: Volcanoes only + NeoForge.
- [x] CI representative integrations: Destroy, Cold Sweat, Create; compile/runtime smoke with reproducibly pinned real host artifacts.
- [x] Full-pack matrix: Terralith + Tectonic + BWG + Create/Aeronautics/Sable + Destroy + Cold Sweat + RNS + MineColonies.
- [x] GameTests cover site persistence, eruption restart, breathable air, pressure, protected areas, optional-adapter absence and exact-host activation.
- [x] Exact tested versions are recorded in `docs/compatibility.md`.

## Closure evidence

Task 01 was implemented across PR #85 and PR #86. PR #85 established the base CI, exact Create/Sable host gate and deterministic WG-00 through WG-07 worldgen matrix. PR #86 adds the missing reproducible full-pack gate with real Destroy 0.4.1.

The exact pre-closeout implementation head `21a54d16bc9abf9c812d176c4c8340e0afaf2cfe` passed every canonical gate on 2026-08-30:

- `Volcanoes CI` — run `33337881713` — GREEN.
- `Create Sable Acceptance` — run `33337881698` — GREEN.
- `Cold Sweat Heat Acceptance` — run `33337881697` — GREEN.
- `RNS Hydrothermal Acceptance` — run `33337881834` — GREEN.
- `MineColonies Claim Acceptance` — run `33337881778` — GREEN.
- `Worldgen Compatibility Matrix` — run `33337881718` — GREEN with WG-00 through WG-07 all successful.
- `Full Pack Compatibility Acceptance` — run `33337881867` — GREEN.

The full-pack gate installs the exact supported stack, validates the Destroy 0.4.1 release asset against SHA-256 `ba20bd69fd69e94671060665f08249f782e5526e1fd4223995c681a23361d351`, validates the Petrolpark 1.5.0 ABI expected by Destroy, runs 31 required NeoForge GameTests, starts a dedicated server twice on the same world, flushes saves, performs clean shutdowns and verifies Volcanoes site persistence across reload.

The original Petrolpark Maven host is no longer DNS-resolvable. The gate therefore pins the immutable public Petrolpark NeoForge 1.21.1 release `3A7Utwm4` and rejects it before Minecraft startup unless `petrolpark/mc/library/mixin/plugin/PetrolparkMixinPlugin.class` is present. This prevents a same-version-name but ABI-incompatible Petrolpark artifact from producing a false support claim.

**Acceptance:** release claims are tied to explicit version combinations and executable evidence rather than assumptions.

**Next unopened task:** `02-performance.md`. It is not part of this closure.
