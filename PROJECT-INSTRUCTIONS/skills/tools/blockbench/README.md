# Blockbench tools

## Preferred — RPG Asset Toolkit

Use `rpg-asset-toolkit/rpg_asset_toolkit.js` for new project-owned model work. It adds richer structural checks, parent-cycle detection, face/UV validation and optional asset-contract profiles (`requiredBones`, `requiredAnimations`, `maxSpan`). It is read-only and has Node regression tests.

See `rpg-asset-toolkit/README.md`.

## Legacy — Minecraft Asset Validator

`minecraft_asset_validator.js` is retained for compatibility with the first art-pipeline PR. It remains a read-only baseline checker but should not receive new project requirements when the Toolkit can express them.

Neither plugin generates models, rewrites UVs, fixes pivots or decides whether an asset looks good. Final acceptance always requires `../../library/minecraft-visual-qa/SKILL.md` and in-game evidence.

When installation/sideloading is needed, apply `../../USER-GUIDED-WORKFLOW.md`: one manual action, verify, then continue.
