# ✅ Volcano Plan — Lava and Rock Interaction

**Status:** implemented, reviewed, corrected and verified on `feat/03-lava`.

**Goal:** preserve the useful TFC Volcanoes concept that local rock properties affect volcanic lava while remaining compatible with vanilla/modded blocks.

**Implemented types:** `LavaEnvironmentSample`, `LavaFlowResolver`, `VolcanicLavaController`, `LavaCoolingProductPolicy`.

- [x] Basalt/tuff/granite/generic profiles produce distinct, bounded spread/cooling modifiers. Spread is clamped to `0.50–1.75`; cooling is clamped to `0.50–2.00`.
- [x] `LavaFlowResolver` resolves geology only through `RockProfileResolver`; it has no TFC rock enum dependency. Missing/null/generic geology falls back to the canonical generic profile.
- [x] Unknown/generic geology returns `usesVanillaFallback=true`, so `VolcanicLavaController` yields `VANILLA` instead of replacing the global fluid engine.
- [x] Specialized flow work is budgeted by both blocks-per-tick and blocks-per-eruption caps. The caller reports `blocksChangedThisTick` and `blocksChangedThisEruption`, so repeated planning calls share the same remaining safety envelopes rather than each receiving a fresh per-tick allowance.
- [x] Work targeting unloaded chunks is represented as `DEFERRED`, never as a chunk-load request.
- [x] Deferred chunk-boundary positions are held in a bounded per-`ChunkPos` FIFO queue. Overflow is rejected fail-closed; draining is explicitly bounded by the caller.
- [x] Cooling/solidification products are modeled through extensible block tags: `volcanoes:lava_cooling/basaltic`, `volcanoes:lava_cooling/glassy` and `volcanoes:lava_cooling/rubble`. Their bundled vanilla baselines are basalt, obsidian and cobblestone respectively, and `replace:false` allows datapacks/mods to extend the candidate sets without hard dependencies.
- [x] `LavaCoolingProductPolicy` preserves vanilla water-contact semantics at the fallback layer: source-like quenching selects the glassy/obsidian family, flowing quenching selects rubble/cobblestone, while dry specialized cooling chooses basaltic versus glassy products from the bounded cooling multiplier.

## TDD evidence

### Geology-aware flow modifiers

- RED HEAD: `6ce600faea814be570d7791a0e7c6bc25f284439`.
- RED workflow: `32960219952` — failed at `compileTestJava` only because `LavaEnvironmentSample` and `LavaFlowResolver` did not exist.
- GREEN HEAD: `41a0507098022bfc4e3f57ff993dd61a355f6198`.
- GREEN workflow: `32960517599` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

`LavaFlowResolverTest` proves rock-sensitive spread/cooling, bounded modifiers, coordinate forwarding to `RockProfileResolver`, generic vanilla fallback and clamping of extreme datapack values.

### Bounded controller and chunk-boundary queue

- RED HEAD: `03cf38c2810fcb8fcac0cf4ff35d6edd1c1e7362`.
- RED workflow: `32960846029` — failed at `compileTestJava` only because `VolcanicLavaController` did not exist.
- GREEN HEAD: `d3437b978a1ab82d628a6ca99e2225813eaddfd3`.
- GREEN workflow: `32961075555` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

`VolcanicLavaControllerTest` proves vanilla delegation, specialized per-tick/per-eruption budgets, exhausted-budget behavior, unloaded-chunk deferral, bounded FIFO queuing and bounded draining.

### Tag-driven cooling products

- RED HEAD: `3b30cc60151697fcfb9adcd68227f37b53700590`.
- RED workflow: `32961469738` — failed at `compileTestJava` only because `LavaCoolingProductPolicy` did not exist.
- GREEN HEAD: `3f1f265f08314caa2f27bd2bed76b43725d7773a`.
- GREEN workflow: `32961754238` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

`LavaCoolingProductPolicyTest` proves dry basaltic/glassy selection, vanilla-compatible source-versus-flowing water quenching and the three bundled extensible block-tag resources.

### Post-merge cumulative tick-budget correction

The automated review of PR #12 found that the first controller API applied `maxBlocksPerTick` independently on every `planStep()` call. That allowed multiple calls in one server tick to cumulatively exceed the advertised cap. The task was therefore corrected before downstream eruption work proceeded.

- RED HEAD: `a68b325ed2c078829ebbc28fa9391d82cd74f233`.
- RED workflow: `32963090494` — failed at `compileTestJava` because the controller did not yet accept caller-reported tick usage.
- GREEN HEAD: `f310bc975bbc5bf09039ba8c8de4659bb3004401`.
- GREEN branch workflow: `32963474291` — unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke passed.
- Corrective PR: #17.
- Corrective PR workflow: `32964735385` — GREEN, including dedicated-server smoke.
- Corrective merge SHA: `6fa9ec04c7245b2381b96426513dff49b97edb69`.
- Corrective post-merge `main` workflow: `32965025349` — GREEN, including dedicated-server smoke.

`VolcanicLavaControllerTest.multipleFlowStepsShareCallerReportedTickBudget` proves that a first 24-block grant leaves only 8 blocks of a 32-block tick budget for the next call and that subsequent work is capped at zero once the shared tick envelope is exhausted.

## Acceptance

For identical controller inputs, configured rock profiles produce different bounded flow/cooling samples while unknown geology delegates to vanilla. Specialized work has explicit cumulative per-tick and per-eruption caps, never owns chunk loading, and queues unloaded-chunk work within a bounded FIFO. Cooling outputs are tag-driven and have safe vanilla fallbacks.

Physical eruption emission remains intentionally outside this task: `04-eruptions.md` owns the persistent eruption state machine/events that future runtime consumers will feed into this lava controller. This avoids adding a second fluid engine or a parallel eruption scheduler prematurely.
