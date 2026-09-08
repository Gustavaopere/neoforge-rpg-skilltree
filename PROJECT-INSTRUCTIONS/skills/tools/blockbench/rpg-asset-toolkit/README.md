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

Errors include no project; empty/duplicate bone names; malformed pivots; parent cycles; malformed/negative bounds; enabled face with unresolved texture; malformed UV; invalid texture dimensions; empty/duplicate animation names; missing required contract bones/animations; and profile-specific `maxSpan` overflow.

Warnings include unsaved/non-`.bbmodel` source; naming deviations; zero-thickness geometry; ungrouped elements; non-power-of-two textures; duplicate texture/element names; invalid animation length/loop mode; unknown animator target UUID.

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

Tests use Node's built-in runner and no third-party npm dependency.
