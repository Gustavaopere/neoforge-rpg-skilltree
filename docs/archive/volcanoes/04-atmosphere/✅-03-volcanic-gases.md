# Atmosphere Plan — Volcanic Gases

**Goal:** convert eruptions/geothermal vents into physically distinct atmospheric hazards.

- [x] Define source profiles for ash/particulates, CO2 and SO2/acid gases; optional H2S-like toxic source can be configured for geothermal areas.
- [x] Test dense CO2 lowers effective oxygen fraction locally without being treated as particulate smoke.
- [x] Test SO2 drives toxicity/acidification but does not magically remove all oxygen.
- [x] Make emission rates scale with eruption phase/chamber gas content and geothermal feature type.
- [x] Implement decay/diffusion hooks that can later consume wind/weather information without requiring a weather mod.

**Acceptance:** volcanic plume composition matters mechanically and is consumable by respiration and Destroy adapters.

## Completion evidence

- Distinct CO2, SO2/acid-gas, ash/particulate-smoke and configurable geothermal-toxic profiles are canonical through the Stage 04 core merged in PR #31. Focused hazard-separation verification PR #56 reached exact-head workflow `33198327542` GREEN.
- Geothermal atmospheric gas lifecycle/projection is canonical through PR #48: fumaroles and sulfurous vents project bounded stable `EXTERNAL` Atmosphere sources without inventing gas families for hot springs/geysers/mud pots.
- Canonical eruption-driven CO2/SO2 metadata and Atmosphere projection landed through PR #67, merge `5813d51f99475f6221cba9346bf7234dcf2daf1a`.
- The eruption gas path uses a dedicated metadata-only `VolcanicGasAuthority`, so Atmosphere observation does not register a second work-budgeted `EruptionSink` and cannot reduce existing eruption effect work allocation.
- Volcanic gas identity is stable per volcano; phase/intensity plus chamber gas/volatile state drive normalized emission strength; DORMANT removes the same source; active state hydrates from authoritative Stage 03 persistence after load.
- PR #67 was revalidated against `main` after the Atmosphere DYNAMIC/EXTERNAL capacity partition from PR #66. Workflow `33224094364` completed GREEN through unit tests, diff sanity, NeoForge build, JAR verification, Eruption GameTest server and dedicated-server smoke.

Task 03 is therefore complete. The remaining Stage 04 work is Task 04 — Pollution / Acid Rain, specifically the provenance-safe Destroy adapter tracked in `PENDING.md`.
