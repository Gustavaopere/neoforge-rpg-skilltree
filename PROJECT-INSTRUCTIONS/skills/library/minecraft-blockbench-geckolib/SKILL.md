---
name: minecraft-blockbench-geckolib
description: Use when creating, editing, exporting, or reviewing Blockbench models, UVs, rigs, animations, or GeckoLib-backed Minecraft assets for the NeoForge 1.21.1 project.
source: project-authored-2026-09-07
project_status: preferred
---
# Minecraft Blockbench + GeckoLib

## Target authority

This project targets Minecraft 1.21.1, NeoForge 21.1.x, and Java 21. Confirm the exact installed GeckoLib version from the current physical modlist/build before implementation. For Minecraft 1.21.1, use the GeckoLib 4 API family; do not transplant GeckoLib 5 patterns from 1.21.5+.

Blockbench with the GeckoLib plugin is the canonical authoring path for GeckoLib model and animation JSON. Keep the `.bbmodel` source under project control whenever the asset is project-owned.

## Production flow

1. Read `minecraft-asset-art-direction` and the asset contract first.
2. Create or convert a `GeckoLib Animated Model` project only when GeckoLib is actually the chosen renderer.
3. Establish hierarchy before animation: stable bone names, intentional parenting, correct pivots, and attachment points required by held items/VFX.
4. UV and texture deliberately. Preserve consistent texel density and material readability; do not stretch a concept image over geometry.
5. Create animations from named gameplay states/poses. Decide whether each animation is `once`, `hold`, or `loop` intentionally.
6. Preserve the `.bbmodel`; export the provider-appropriate model/animation resources and textures.
7. Run `tools/blockbench/minecraft_asset_validator.js` inside Blockbench for structural checks.
8. Perform in-game visual QA; the plugin is not proof of visual quality.

## Structural checks

Treat these as release blockers when applicable:

- duplicate/empty bone names;
- malformed pivots or bounds;
- missing textures for renderable geometry;
- invalid export paths;
- missing required attachment bones;
- animations with wrong loop semantics;
- clipping that affects intended gameplay poses;
- client rendering code referenced from dedicated-server paths.

Warnings that require review rather than blind rejection include non-power-of-two textures, zero-thickness geometry, unusual bone naming, unusually large texture budgets, or intentionally unconventional pivots.

## GeckoLib integration rule

Verify actual GeckoLib 4 classes/methods/signatures against the installed dependency or source before writing Java. The normal resource responsibilities are model geometry, texture, and animation resources, but exact code must follow the version in this repository. GeckoLib animation keyframes can participate in sound/particle/custom-effect timing, but do not invent handler signatures: inspect the current API first.

## QA evidence

A completed model task should identify:

- source `.bbmodel`;
- exported resource paths;
- texture dimensions;
- bone/attachment contract;
- animation names and loop policy;
- validator result;
- in-game screenshots or explicit pending visual review;
- dedicated-server/client separation when Java integration changed.

If the user must click through Blockbench, follow `../../USER-GUIDED-WORKFLOW.md` and provide one manual action at a time.