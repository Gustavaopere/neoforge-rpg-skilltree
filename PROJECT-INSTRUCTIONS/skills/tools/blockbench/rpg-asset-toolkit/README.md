# RPG Asset Toolkit for Blockbench

Internal project plugin: `rpg_asset_toolkit.js` (plugin ID matches filename).

## Scope

Read-only structural/contract QA for project-owned Minecraft/GeckoLib assets. It does **not** rewrite geometry, UVs, pivots, textures or animations automatically.

## Verified Blockbench API surface

- `Plugin.register(...)`;
- `Action`;
- `MenuBar.menus.tools.addAction(...)`;
- `Blockbench.Project`;
- `Blockbench.showMessageBox(...)`;
- `Blockbench.textPrompt(...)`.

References: https://blockbench.net/wiki/docs/plugin/ · https://blockbench.net/wiki/docs/ui/ · https://web.blockbench.net/docs/

## Checks

Errors include no project; empty/duplicate bone names; malformed pivots; parent cycles; malformed/negative **cube** bounds; malformed Locator positions; enabled cube face with unresolved texture; malformed cube-face UV; invalid texture dimensions; empty/duplicate animation names; missing required contract bones/animations; and profile-specific `maxSpan` overflow.

Warnings include unsaved/non-`.bbmodel` source; naming deviations; zero-thickness cube geometry; ungrouped cubes/Locators; non-power-of-two textures; duplicate texture/element names; invalid animation length/loop mode; unknown animator target UUID.

### Cube x Locator semantics

Blockbench/GeckoLib Locators are attachment/VFX points, not renderable cubes. The Toolkit therefore:

- applies `from`/`to`, face, UV, texture and model-bounds checks only to cube-like elements;
- validates Locator `from` as its position and reports `INVALID_LOCATOR_POSITION` when malformed;
- does not require a texture merely because a project contains Locators;
- still treats a cube-like object with missing/malformed `from` or `to` as `INVALID_ELEMENT_BOUNDS` rather than silently reclassifying it.

This separation follows the Blockbench type contracts: `Cube` owns `from`, `to` and `faces`, while `Locator` owns `from` without cube-style `to` bounds.

## Contract profile

The second Tools-menu action accepts JSON such as:

```json
{
  "requiredBones": ["root", "vfx_anchor"],
  "requiredAnimations": ["animation.example.idle", "animation.example.attack"],
  "maxSpan": [16, 32, 16]
}
```

`maxSpan` is **asset-specific contract data**, not a universal project budget.

## Deliberate non-automation

Exact texel density is not guessed from incomplete project data. The Toolkit reports texture dimensions/bounds and validates UV completeness; contract + Visual Style Bible govern density decisions. Screenshot/aesthetic approval remains `minecraft-visual-qa` work.

## Tests

```bash
node --check rpg_asset_toolkit.js
node --test rpg_asset_toolkit.test.js
```

Tests use Node's built-in runner and no third-party npm dependency. The regression suite includes Locator-only and malformed-Locator cases so attachment/VFX points cannot regress into cube validation.
