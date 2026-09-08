---
name: minecraft-blockbench-geckolib
description: Use when creating, editing, exporting, or reviewing Blockbench models, UVs, rigs, animations, or GeckoLib-backed Minecraft assets for the NeoForge 1.21.1 project.
source: project-authored-2026-09-07
project_status: preferred
---
# Minecraft Blockbench + GeckoLib

## Target authority

This project targets Minecraft 1.21.1, NeoForge 21.1.x and Java 21. Confirm exact installed GeckoLib from the physical modlist/build. For 1.21.1 use the GeckoLib 4 family; do not transplant GeckoLib 5 patterns from 1.21.5+.

Blockbench + GeckoLib plugin is the canonical authoring path for GeckoLib model/animation resources. Preserve project-owned `.bbmodel` source.

## Production flow

1. Read `minecraft-asset-art-direction`, Visual Style Bible and model/animation briefs.
2. Use `GeckoLib Animated Model` only when GeckoLib is actually the chosen renderer.
3. Establish stable hierarchy, parenting, pivots and attachment points before animation.
4. UV/texture deliberately; derive resolution/texel density from project evidence rather than a fixed guess.
5. Create animations from named gameplay/presentation states and choose loop semantics deliberately.
6. Preserve `.bbmodel`; export only provider-appropriate model/animation/texture resources.
7. Run `../../tools/blockbench/rpg-asset-toolkit/rpg_asset_toolkit.js`.
8. When the contract specifies required bones/animations/bounds, use the Toolkit profile action.
9. Perform `minecraft-visual-qa` in-game; Toolkit PASS is structural, not aesthetic approval.

## Structural blockers

Applicable blockers include duplicate/empty bone names, parent cycles, malformed pivots/bounds, unresolved face textures, malformed UVs, missing contract bones/animations, wrong loop semantics, clipping that misleads gameplay, invalid provider export paths and client rendering code reachable on dedicated server.

## GeckoLib integration rule

Verify actual GeckoLib 4 classes/methods/signatures against installed dependency/source before Java implementation. Animation keyframes may participate in sound/particle/custom-effect timing, but do not invent handler signatures.

## QA evidence

A completed model task identifies source `.bbmodel`, exports, texture dimensions, density evidence, bone/attachment contract, animation names/loop policy, Toolkit result, in-game evidence or explicit PENDING visual review, and dedicated-server/client separation if Java integration changed.

If the user must click through Blockbench, follow `../../USER-GUIDED-WORKFLOW.md`: one action, verify visible result, then continue.
