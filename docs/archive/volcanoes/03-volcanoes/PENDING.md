# Stage 03 Volcanoes — Pending Cross-Stage Integrations

This file records dependency-accurate work that Stage 03 produces but cannot consume safely until the owning future stage is canonical. These entries are not generic TODOs and do not authorize compiling against preparatory branches.

Task 05 has no remaining cross-stage blockers. `VOLC-05-ATM-ASH-001` closed with the canonical ash lifecycle/Atmosphere integration merged in PR #31 (`bc91fc16a63a7422907c778a8ee9b197d1d056fe`), and `VOLC-05-PROTECTED-AREA-001` closed with the focused protected-area/MineColonies integration merged in PR #45 (`6469588ccdd96249ca8f507e47e6dc30b4901638`) after real-host acceptance in PR #43.

Task 06 has no remaining cross-stage blockers:

- `VOLC-06-ATM-GEOTHERMAL-GAS-001` closed in PR #48, merge `3fbe7d655f4ac75d291b5ec4502418fbdf89520f`. The canonical bridge maps geothermal source lifecycle into bounded Atmosphere gas sources while preserving Stage 03 source identity and authority.
- `VOLC-06-COLD-SWEAT-HEAT-001` closed in PR #49, merge `e5e2bf54dee60142db30cd54d057e164178e6f66`. The exact Cold Sweat 2.4.2 adapter consumes only bounded `VolcanicHeatService` output; LAVA, PYROCLASTIC and GEOTHERMAL producers are covered, and exact-host acceptance passed.
- `VOLC-06-RNS-HYDROTHERMAL-001` is finally closed through PR #82, merge `7839db6d9b718e1e2becfe8b88e9b3d24282e2ef`, with ownership-safety hardening through PR #84, merge `c26e97c136b543f1fa0ef2ebb12044d10d8af816`. Earlier PR #50 established the first exact-host lifecycle integration, but its native-worldgen ownership-handoff framing is superseded by the accepted coexistence contract: RNS remains prospecting and native worldgen authority for Cu/Fe/Au/Sn/Ni/Zn/Ag, while Volcanoes physically generates and projects only bounded, physically proven hydrothermal Cu/Fe/Au custom locations. Volcanoes-owned RNS custom locations persist the authoritative source UUID in the host record and require that marker for restart rebind/removal, so a foreign same-value replacement is never adopted or deleted.

Stage 03 currently has no remaining cross-stage integration blockers.
