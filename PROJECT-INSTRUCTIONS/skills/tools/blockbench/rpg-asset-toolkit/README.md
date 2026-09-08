# RPG Asset Toolkit for Blockbench

Internal project plugin: `rpg_asset_toolkit.js` (plugin ID matches filename).

## Scope

Read-only structural/contract QA plus fail-closed provider/extension capability resolution for project-owned Minecraft assets. It does **not** rewrite geometry, UVs, pivots, textures or animations automatically and does not install or invoke arbitrary extensions.

## Architecture

Canonical source lives in modular CommonJS files under `core/` and `blockbench-plugin/`. `build_toolkit_bundle.js` generates the standalone `rpg_asset_toolkit.js` consumed by Blockbench. CI requires the committed standalone bundle to match the modular source byte-for-byte.

Core modules:

- `project-model` — safe model predicates and bounds;
- `validator` — structural/contract validation;
- `contract-profile` — profile JSON parsing;
- `report` — deterministic read-only reports;
- `extension-registry` — exact extension identity/version/compatibility/MCP authorization;
- `provider-profile` — physical provider snapshot plus fail-closed profile resolution;
- `blockbench-plugin/plugin_adapter.js` — verified UI integration only.

Canonical policy documents:

- `../../../standards/BLOCKBENCH-EXTENSION-POLICY.md`;
- `../../../standards/ASSET-PROVIDER-PROFILES.md`.

## Verified Blockbench API surface

- `Plugin.register(...)`;
- `Action`;
- `MenuBar.menus.tools.addAction(...)`;
- `Blockbench.Project`;
- `Blockbench.showMessageBox(...)`;
- `Blockbench.textPrompt(...)`.

References: https://blockbench.net/wiki/docs/plugin/ · https://blockbench.net/wiki/docs/ui/ · https://web.blockbench.net/docs/

## Provider/extension rules

Installed is not equivalent to compatible, trusted or MCP-authorized. Required extensions must match the audited exact plugin version, pass the Blockbench compatibility gate and be explicitly session-allowlisted. Blocked/human-only/audit-required paths fail closed. Cross-profile conversion is denied unless a later audited adapter exists.

The executable snapshot follows the current physical modlist authority: GeckoLib 4.9.2, AzureLib 3.1.11 and Entity Model Features 3.3.5 are represented as provider requirements where applicable; current Blockbench catalog pins are maintained separately in the extension registry.

## Checks

Errors include no project; empty/duplicate bone names; malformed pivots; parent cycles; malformed/negative **cube** bounds; malformed Locator positions; enabled cube face with unresolved texture; malformed cube-face UV; invalid texture dimensions; empty/duplicate animation names; missing required contract bones/animations; and profile-specific `maxSpan` overflow.

Warnings include unsaved/non-`.bbmodel` source; naming deviations; zero-thickness cube geometry; ungrouped cubes/Locators; non-power-of-two textures; duplicate texture/element names; invalid animation length/loop mode; unknown animator target UUID.

### Cube x Locator semantics

Blockbench/GeckoLib Locators are attachment/VFX points, not renderable cubes. The Toolkit therefore applies cube bounds/face/UV/texture checks only to cube-like elements and validates Locator positions separately as `INVALID_LOCATOR_POSITION`.

## Contract profile

The Tools-menu contract action accepts JSON such as:

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

No Live Bridge, MCP transport, mutation engine, arbitrary JavaScript/shell execution, provider exporter, automatic plugin installation or gameplay authority is implemented in PR1.

## Tests

```bash
node --check rpg_asset_toolkit.js
node --test rpg_asset_toolkit.test.js
node build_toolkit_bundle.js --check
```

Tests use Node's built-in runner and no third-party npm dependency. They cover structural QA, Locator semantics, extension authorization, physical-provider authority, provider resolution, fail-closed conversion, schemas and generated-bundle reproducibility.
