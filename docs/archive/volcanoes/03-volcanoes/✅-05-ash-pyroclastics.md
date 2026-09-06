# Volcano Plan — Ash, Bombs and Pyroclastics

**Goal:** retain high-value TFC Volcanoes eruption content: ash plume/deposition, volcanic bombs and pyroclastic hazards.

- [x] Port/adapt upstream projectile/flow logic only after mapping its TFC dependencies.
- [x] Represent ash emission as an environmental source feeding Atmosphere; visible particles are presentation, not the authoritative concentration model.
- [x] Deposit ash gradually using bounded chunk work and configurable replaceable-surface tags.
- [x] Volcanic bombs use server-authoritative trajectories and capped terrain interaction; player/colony protection rules apply.
- [x] Pyroclastic flows damage entities via heat/particulate exposure and limited natural terrain interaction; no uncontrolled recursive block destruction.

**Acceptance:** complete. Native ash/bomb/pyroclastic runtime is canonical; the Stage-04 bridge projects one stable non-persistent `EXTERNAL` Atmosphere source per authoritative Stage-03 ash source with deterministic replay/restart behavior and bounded retry; the focused MineColonies protected-area bridge is canonical and real-host GameTests prove ash, bomb and pyroclastic mutations preserve claimed terrain without force-loading distant chunks.

**Evidence:** native Task05 core PR #23 / workflow `32991852165`; real MineColonies acceptance PR #43 / workflow `33141314153`; protected-area merge PR #45 / exact-final workflow `33165202637` / merge `6469588ccdd96249ca8f507e47e6dc30b4901638`; ash/Atmosphere PR #31 / observed RED followed by exact-final workflow `33165968236` GREEN / merge `bc91fc16a63a7422907c778a8ee9b197d1d056fe`.
