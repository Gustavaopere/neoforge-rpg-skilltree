# Minecraft Asset Validator for Blockbench

`minecraft_asset_validator.js` is an internal, read-only Blockbench plugin for structural QA. It does not generate models, rewrite UVs, fix pivots, or decide whether an asset looks good.

## Verified API surface

The implementation intentionally uses a small Blockbench API surface documented by the official Blockbench wiki/reference:

- `Plugin.register(...)` for plugin lifecycle;
- `Action` for the validation command;
- `MenuBar.menus.tools.addAction(...)` to expose the command;
- `Blockbench.Project` as the current model project;
- `Blockbench.showMessageBox(...)` for the report.

Project data inspected is limited to documented model-project fields such as groups, elements, textures and animations.

## Checks

Errors:

- no open project;
- empty or duplicate bone/group names;
- malformed/non-finite bone pivots;
- malformed or negative element bounds;
- renderable geometry without a texture;
- invalid texture dimensions;
- unnamed animations.

Warnings:

- model elements without an intentional group/bone hierarchy;
- bone names outside project `lower_snake_case` convention;
- zero-thickness geometry;
- non-power-of-two textures;
- zero/non-finite animation length;
- unexpected animation loop mode.

Warnings are review items, not automatic rejection. Provider-specific formats may justify exceptions.

## Manual use

Do not dump a multi-step installation tutorial on the user. Apply `../../USER-GUIDED-WORKFLOW.md`.

When installation is actually needed, the first action is only: open Blockbench's plugin interface so the local plugin can be side-loaded. Ask for the visible result before giving the next action.

## Validation boundary

A PASS means only that this structural checker found no listed issue. Final acceptance still requires `../../standards/VISUAL-QA.md` and in-game evidence.

## Upstream references checked 2026-09-07

- Blockbench plugin guide: https://blockbench.net/wiki/docs/plugin/
- Blockbench generated API reference: https://web.blockbench.net/docs/
- GeckoLib Blockbench model guide: https://github.com/bernie-g/geckolib/wiki/Making-Your-Models-%28Blockbench%29
- GeckoLib 4 documentation: https://wiki.geckolib.com/docs/geckolib4/
