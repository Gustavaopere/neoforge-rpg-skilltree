# 04 — Atmosphere

**Branch:** `feat/06-atmosphere`, created after volcano eruption fundamentals merge.

Build one environmental atmosphere model shared by breathing, volcanic ash/gases and pollution adapters. ThinAir: ReLived is the breathing implementation reference; Destroy remains pollution authority when installed.

Task order:

- [x] `✅-01-atmosphere-state.md`
- [x] `✅-02-respiration.md`
- [x] `✅-03-volcanic-gases.md`
- [ ] `04-pollution-acid-rain.md`

Task 01 is canonical through Stage 04 integration PR #31, merge `bc91fc16a63a7422907c778a8ee9b197d1d056fe`, with exact-final workflow `33165968236` GREEN.

Task 02 respiration is canonical through the Stage04 core plus the completed dependency closures: focused respiration verification PR #54 / workflow `33197048358` GREEN, canonical Pressure reconciliation through PRs #57/#59, and exact Create 6.0.10 breathing-equipment integration PR #62 / HEAD `ece0aab59e9c96809dc18a648f882362481e0f0f` / workflow `33210837237` GREEN. Oxygen supply, filtration and contaminant hazards remain mechanically distinct, and shared protection-use transactions prevent duplicate host-resource debit.

Task 03 volcanic gases is canonical through the Stage04 source/hazard core, geothermal gas bridge PR #48, focused hazard verification PR #56 / workflow `33198327542`, and the canonical eruption-gas lifecycle/projection merged through PR #67 as `5813d51f99475f6221cba9346bf7234dcf2daf1a`. The current-main merge-ref workflow `33224094364` passed unit tests, diff sanity, NeoForge build, JAR verification, Eruption GameTests and dedicated-server smoke. Eruption gas observation is metadata-only and does not consume the work budget of the existing eruption-effect sink.

The next and only unopened Stage 04 task is `04-pollution-acid-rain.md`. Stage 04 remains open until the provenance-safe Destroy authority adapter and Task 04 acceptance are closed.
