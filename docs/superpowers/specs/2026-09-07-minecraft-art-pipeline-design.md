# Minecraft Art Pipeline Design

## Goal

Give chats a repeatable, evidence-driven pipeline for project-owned Minecraft visual assets so model quality is not reduced to ad-hoc prompting or unvalidated exports.

## Target

- Minecraft 1.21.1
- NeoForge 21.1.x
- Java 21
- exact provider/runtime versions remain subordinate to the physical modlist and repository build

## Problem

The repository already had generic image/resource-pack guidance, but no canonical authority for:

- visual identity before geometry;
- model/texture contract;
- Blockbench/GeckoLib authoring;
- structural model validation;
- visual/in-game acceptance evidence.

Generic image generation can supply concepts but cannot guarantee UVs, pivots, rigging, runtime model format, animation semantics, or style consistency.

## Design

### 1. Art-direction skill

`minecraft-asset-art-direction` owns look-dev and the asset contract. It requires inspection of canonical neighboring assets before final production and prevents generated reference art from being silently treated as a finished game asset.

### 2. Blockbench/GeckoLib skill

`minecraft-blockbench-geckolib` owns `.bbmodel`, hierarchy, UV, pivots, animation/export discipline and the handoff into the verified GeckoLib 4 API family for the project's 1.21.1 target.

### 3. Project standards

`MODEL-ASSET-CONTRACT.md` records the visual/technical contract. `VISUAL-QA.md` defines acceptance evidence. These are project conventions rather than generic reusable skill prose.

### 4. Read-only Blockbench validator

`minecraft_asset_validator.js` exposes one Tools-menu action and reads the current Blockbench project. It reports structural errors/warnings but never mutates geometry, UVs, textures, pivots, or animations.

The first validator intentionally checks only evidence supported by stable documented Blockbench fields:

- groups/bone names and pivots;
- element bounds;
- texture presence/dimensions;
- animation names, lengths and loop modes.

A structural PASS never replaces visual/in-game QA.

### 5. CI contract

The existing repository skill validator expands from 20 to 22 canonical skills and verifies that the router, standards, tool documentation and required Blockbench plugin API tokens remain present.

## Failure policy

- unknown provider requirement -> `UNRESOLVED`, not guessed;
- incompatible style authorities -> report conflict;
- unsupported/newer GeckoLib examples -> do not transplant;
- missing in-game visual evidence -> `PENDING VISUAL QA`;
- Blockbench helper API drift -> re-check official Blockbench reference before changing symbols.

## User interaction

When GUI work is required, `USER-GUIDED-WORKFLOW.md` applies: one manual action at a time, receive the visible result, then give the next action.