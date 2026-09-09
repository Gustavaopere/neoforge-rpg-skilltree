# RPG Asset Toolkit for Blockbench

Internal project plugin: `rpg_asset_toolkit.js` (plugin ID matches filename).

## Scope

Structural/contract QA plus fail-closed provider/extension capability resolution for project-owned Minecraft assets, with narrowly bounded local Blockbench mutations where an audited contract exists. Mutation support is intentionally incremental: it does not authorize arbitrary geometry, UV, texture, pivot or animation rewrites, and it does not install or invoke arbitrary extensions.

The current PR4 slice adds deterministic, one-texture UV pack preview/apply for per-face cube UVs. It is a bounded local desktop operation, not a generic UV repacker and not a Live Bridge/MCP write surface.

## Architecture

Canonical source lives in modular CommonJS files under `core/` and `blockbench-plugin/`. `build_toolkit_bundle.js` generates the standalone `rpg_asset_toolkit.js` consumed by Blockbench. CI requires the committed standalone bundle to match the modular source byte-for-byte.

Core modules include:

- `project-model` — safe model predicates and bounds;
- `validator` — structural/contract validation;
- `contract-profile` — profile JSON parsing;
- `report` — deterministic read-only reports;
- `extension-registry` — exact extension identity/version/compatibility/MCP authorization;
- `provider-profile` — physical provider snapshot plus fail-closed profile resolution;
- `uv-texture/uv_pack` — bounded revision-bound UV pack preview/apply contract;
- `blockbench-plugin/uv_texture_adapter.js` — Blockbench UV/texture inspection and bounded transactional mutation adapter;
- `blockbench-plugin/plugin_adapter.js` — verified UI integration.

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

The bounded UV/texture adapter additionally uses the project texture/cube state and Blockbench Undo transaction surface behind the adapter boundary. Tests exercise begin/finish/cancel transaction behavior and bitmap/UV mutation semantics without exposing those mutations through Live Bridge/MCP.

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

## Bounded UV pack preview/apply

The initial PR4 pack contract is deliberately narrow:

- scope is one texture at a time (`scope: "texture"`);
- target faces must use per-face cube UVs; Box UV is rejected by this slice;
- preview is deterministic and mutation-free;
- preview is bound to `expectedRevision` and returns the before-revision, atlas plan, old/new UVs, source/destination pixel regions, copied-pixel count and a confirmation token;
- invalid confirmation fails before preflight or Undo;
- apply revalidates the revision-bound preview and requires its exact confirmation token;
- source bitmap regions are buffered before the first destination write, so overlapping source/destination moves preserve original pixels;
- apply uses one transaction and publishes the changed texture/UV state once;
- successful mutation must advance the project revision;
- face-count and copied-pixel budgets are bounded and fail closed;
- no resize, arbitrary global painting, generic global repack or remote Live Bridge/MCP write is introduced by this slice.

This closes only the bounded `pack preview/apply` subgate. PR4 remains broader: the canonical plan still requires the remaining UV/texture capabilities and final round-trip, visual-diff, Undo and texture-path evidence before PR4 can be considered complete or PR5 can begin.

## Deliberate non-automation

Exact texel density is not guessed from incomplete project data. The Toolkit reports texture dimensions/bounds and validates UV completeness; contract + Visual Style Bible govern density decisions. Screenshot/aesthetic approval remains `minecraft-visual-qa` work.

Mutation capabilities remain explicit and bounded. There is no arbitrary JavaScript/shell execution, provider exporter, automatic plugin installation, gameplay authority, generic animation mutation, arbitrary global UV repack, arbitrary global texture painting or new Live Bridge/MCP write surface in this PR4 slice.

## Tests

```bash
node --check rpg_asset_toolkit.js
node --test rpg_asset_toolkit.uv_pack.test.js
node --test rpg_asset_toolkit.bundle_live_bridge.test.js
node --test rpg_asset_toolkit.test.js
node build_toolkit_bundle.js --check
```

Tests use Node's built-in runner and no third-party npm dependency. Coverage includes structural QA, Locator semantics, extension authorization, physical-provider authority, provider resolution, fail-closed conversion, schemas, generated-bundle reproducibility, deterministic UV-pack preview, exact confirmation/revision binding, Blockbench Undo behavior, overlap-safe source-pixel buffering and standalone bundle loading without introducing a remote write surface.
