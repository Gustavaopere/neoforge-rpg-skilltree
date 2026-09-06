# Atmosphere Plan — Respiration and Oxygen

**Status:** complete and canonical.

**Goal:** adapt ThinAir's correct NeoForge breathing hooks into the richer atmosphere model.

- [x] Write tests converting oxygen partial pressure and contaminant exposure into breathability/air-consumption outcomes.
- [x] Handle `LivingBreatheEvent` server-side. Good air refills; hypoxia consumes air; severe conditions eventually cause vanilla suffocation-style consequences.
- [x] Preserve useful equipment semantics: Water Breathing/Respiration where appropriate, breathing-equipment tags, Create diving helmet/backtank via optional adapter.
- [x] Distinguish particulate filtration from oxygen supply: a mask can filter ash but cannot create oxygen at very low pressure.
- [x] Keep creative/invulnerable and non-breathing entity exemptions data-driven.

**Acceptance:** high-altitude hypoxia, ash-filled normal-O2 air and CO2-displaced low-O2 air produce different results and protections.

## Completion evidence

- Canonical Atmosphere respiration core: PR #31 / merge `bc91fc16a63a7422907c778a8ee9b197d1d056fe`.
- Focused respiration matrix verification: PR #54, final HEAD `cf9c3aac8652f11b5a1b6f0dc82112e4d6613bd7`, workflow `33197048358` GREEN through unit tests, diff sanity, NeoForge build, JAR verification, GameTests and dedicated-server smoke.
- Canonical Pressure authority and Atmosphere baseline reconciliation: PR #57 + PR #59, with pressure ownership wired into current `main` before final Create integration.
- Exact Create 6.0.10 diving helmet/backtank integration: PR #62, final HEAD `ece0aab59e9c96809dc18a648f882362481e0f0f`, workflow `33210837237` GREEN through unit tests, diff sanity, NeoForge build, JAR verification, GameTests and dedicated-server smoke.
- Create integration uses one shared `ProtectionUseRegistry` / `ProtectionUseSession` across Pressure and Respiration, so one physical host air resource is debited at most once per player/tick/resource key.
- Create contributes only oxygen supply; it does not imply particulate, acid-gas or toxic-gas filtration. Fluid breathing remains vanilla/Create-owned.

Task 02 is closed. The next Stage04 task is `03-volcanic-gases.md`.
