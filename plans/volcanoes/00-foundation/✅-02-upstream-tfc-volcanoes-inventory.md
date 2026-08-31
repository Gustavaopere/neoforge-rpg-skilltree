# Foundation Plan — TFC Volcanoes Upstream Inventory

**Goal:** map the TFC Volcanoes 1.21.1 implementation into portable, replaceable and discardable components before copying behavior.

**Baseline:** TFC Volcanoes `2.2.1`, Minecraft 1.21.1, File ID `8710292`. The exact JAR SHA-256 and source location are recorded in `docs/upstream/TFC_VOLCANOES.md`.

## Required inventory

- [x] Obtain readable source from the public repository if complete; otherwise decompile the published JAR solely as the implementation reference for this private project. The public repository did not contain the current implementation source, so Foundation CI decompiled the pinned JAR with CFR.
- [x] Record upstream license/notice obligations in `docs/upstream/NOTICE.md` and require provenance headers for substantially adapted source.
- [x] Produce a symbol map separating eruption controller, tremors/earthquakes, pyroclastic projectiles/flows, ash, wildfire/charring, mineral regeneration, hot-spring hooks, lava/rock interaction, TFC worldgen queries, TFC climate queries and TFC Registry API queries.
- [x] Classify every decompiled top-level upstream source unit as `PORT`, `ADAPT`, or `DROP`. The 111-source inventory covers all 169 binary classes; nested/anonymous classes inherit their enclosing source-unit classification.
- [x] Add an automated architecture test that fails if production source imports both TFC and TFC Registry API namespaces.

## Verification evidence

The pinned JAR SHA-256 is `26e0acff330bc659c75270ad942b0d6ce60cff97d5fec2bd207d4486fb5b1b4e`. Complete classification is in `docs/upstream/TFC_VOLCANOES_CLASSIFICATION.md`: **30 PORT / 47 ADAPT / 34 DROP**. Final Foundation push workflow `32901372365` revalidated the upstream hash and generated inspection artifact `9583395854` on branch HEAD `0115fd1ba022b30ef81bd7dec60bd7ffd96a2987`.

## Acceptance criteria

A future worker can identify exactly which upstream classes/algorithms feed each Volcanoes subplan without rediscovering the JAR. No copied code lands without provenance.

**Acceptance status:** COMPLETE — merged in PR #2 as `bee79bc77688118e78e73deeb0cb3c06f0b7288b`.
