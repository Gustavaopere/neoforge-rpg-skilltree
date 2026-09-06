# 06 — Integrations

**Canonical implementation:** Stage 06 landed incrementally through `feat/08-integrations` and focused RNS follow-ups. RNS coexistence landed through PR #82 / merge `7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`; post-merge ownership safety was hardened through PR #84 / merge `c26e97c136b543f1fa0ef2ebb12044d10d8af816`.

All adapters are optional and isolated under `dev.gustavopere.volcanoes.compat.<modid>`. Base Volcanoes starts with none of the optional host mods installed. Each adapter is version/presence gated and has an explicit fail-safe contract.

## Plan status

- [`✅-01-worldgen.md`](✅-01-worldgen.md) — Terralith/Tectonic/BWG coexistence and optional volcanic terrain hints.
- [`✅-02-create-sable.md`](✅-02-create-sable.md) — Create respiration plus Sable/Aeronautics contextual pressure integration.
- [`✅-03-cold-sweat.md`](✅-03-cold-sweat.md) — bounded volcanic/geothermal heat projection into Cold Sweat.
- [`✅-04-destroy.md`](✅-04-destroy.md) — volcanic emissions routed into Destroy without duplicate pollution authority.
- [`✅-05-rns.md`](✅-05-rns.md) — exact-host RNS prospecting coexistence for physically proven Volcanoes Cu/Fe/Au bodies while native RNS worldgen remains enabled for Cu/Fe/Au/Sn/Ni/Zn/Ag; Volcanoes-owned custom locations carry a durable source-owner marker for safe restart recovery/removal.
- [`✅-06-minecolonies-safety.md`](✅-06-minecolonies-safety.md) — generic protected-area service and MineColonies claim safety.

## Definition of done

- [x] Base mod starts without optional host mods.
- [x] Tested adapters activate only on verified installed versions/APIs.
- [x] Missing or mismatched host APIs disable/fail-close only the affected adapter instead of breaking core startup.
- [x] Host systems remain authoritative where they already own a mechanic: Cold Sweat for body temperature, Destroy for pollution/acid-rain consequences, RNS for prospecting/native deposit worldgen and Sable for vehicle/sub-level atmospheric pressure.
- [x] No duplicate scanner, temperature authority, pollution feedback loop or synthetic Aeronautics cabin-seal model is introduced.
- [x] Protected destructive terrain mutation is routed through the generic protection service, with exact-host MineColonies acceptance coverage.
- [x] Integration GameTests/contract tests and exact-host CI cover the server/runtime paths available for the installed versions.
- [x] RNS coexistence is production-valid: Volcanoes physically generates only bounded Cu/Fe/Au hydrothermal bodies, projects only those exact families into RNS custom prospecting locations, and keeps native RNS worldgen enabled for all protected metal families.
- [x] RNS ownership is restart-safe and foreign-record-safe: exact RNS custom locations created by Volcanoes persist the authoritative source UUID in their own NBT; rebind/removal requires that host-local marker, so a foreign same-value replacement is never adopted or deleted.

Stage 06 is complete and canonical on `main` through PR #84 / `c26e97c136b543f1fa0ef2ebb12044d10d8af816`.

The final ownership-hardening head `ce758af08296fe6230279cc9fa1a3958b232b961` passed Volcanoes CI `33324064118`, RNS Hydrothermal Acceptance `33324064114` with 26/26 required GameTests including the real RNS NBT round-trip, Worldgen Compatibility Matrix `33324064113` with WG-00 through WG-07, Cold Sweat Heat Acceptance `33324064099` and MineColonies Claim Acceptance `33324064131`. The downtime/foreign-replacement regression was RED first in exact-host run `33323644712`. Final Codex review reported no major issues before merge.

Stage `07-hardening` is the next unopened stage and was not started as part of this Stage 06 closeout.
