---
name: minecraft-asset-art-direction
description: Use when creating, redesigning, reviewing, or specifying Minecraft visual assets that must match the project's established style, including mobs, items, blocks, armor, spell constructs, icons, textures, and model concepts.
source: project-authored-2026-09-07
project_status: preferred
---
# Minecraft Asset Art Direction

## Core principle

Do not start final geometry or texture work from a vague prompt. Establish the visual contract first, then preserve it through modeling, texturing, animation, integration and in-game review.

## Authority order

1. physical/current project assets and latest physical modlist;
2. `docs/art/VISUAL-STYLE-BIBLE.md` and approved project art direction;
3. nearby canonical project assets;
4. provider-native visual language when an integration must belong to that provider;
5. concept references/generated images;
6. generic Minecraft conventions.

If these disagree, report the conflict. Do not average incompatible styles silently.

## Required contract

Before final production, fill `../../templates/ASSET-BRIEF.md` and, for modeled assets, `../../templates/MODEL-BRIEF.md`.

Record at minimum role/viewing distance, silhouette, scale, palette/materials, resolution + texel-density evidence, special render regions, bones/attachments, animations, provider/style references, performance assumptions, provenance and acceptance views.

## Workflow

1. Read `../../../../docs/art/VISUAL-STYLE-BIBLE.md` and `../../VISUAL-STYLE-SOURCES.md`.
2. Inspect neighboring project/canonical assets before inventing a new language.
3. Produce the asset contract.
4. Use `minecraft-imagegen` only for concept/look-dev/reference sheets when useful; generated art is not automatically final texture/UV/rig/model.
5. Convert approved intent into explicit geometry/texture/animation requirements.
6. For Blockbench/GeckoLib work, use `minecraft-blockbench-geckolib`.
7. Run the RPG Asset Toolkit for structural/contract checks.
8. Run `minecraft-visual-qa` in the actual game contexts.
9. Record unresolved compromises instead of declaring the asset finished.

## Hard gates

- No final asset from concept art alone.
- No texture style chosen without checking the Bible and neighboring canonical assets.
- No universal texture resolution/texel density invented without measured evidence.
- No emissive/glow used as substitute for material definition.
- No animation accepted solely porque it exports.
- No third-party visual copied without provenance/licence compatible with redistribution.

If the user must operate Blockbench or another GUI, follow `../../USER-GUIDED-WORKFLOW.md` exactly.
