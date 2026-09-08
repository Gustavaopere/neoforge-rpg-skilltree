# Art Pipeline — Technical Sources

Checked: 2026-09-07.

This file records upstream evidence used to author the project skills. It is not version authority over the physical modlist or repository build.

## Blockbench

Official plugin guide:

- https://blockbench.net/wiki/docs/plugin/

Confirmed there:

- plugins are JavaScript files registered with `Plugin.register`;
- an `Action` can be created by a plugin;
- actions can be attached to the Tools menu;
- local plugins can be loaded for testing/internal use.

Generated API reference:

- https://web.blockbench.net/docs/

Confirmed in the generated reference used by `minecraft_asset_validator.js`:

- `Blockbench.Project` exposes the current `ModelProject`;
- `ModelProject` exposes project groups, elements, textures and animations;
- `Blockbench.showMessageBox(...)` is available for read-only reporting;
- Blockbench animation objects expose name, length and loop mode (`once`, `hold`, `loop`).

The generated reference is tied to current Blockbench releases. If the helper plugin stops loading after a Blockbench update, re-check these exact symbols rather than guessing a replacement.

## GeckoLib

GeckoLib Blockbench modeling guide:

- https://github.com/bernie-g/geckolib/wiki/Making-Your-Models-%28Blockbench%29

Confirmed:

- GeckoLib uses a Blockbench plugin for model/animation authoring;
- GeckoLib Animated Model is the intended project type;
- rigging includes grouping, parenting and pivots;
- Blockbench is used to create/export model and animation JSON.

GeckoLib versioned docs:

- https://wiki.geckolib.com/docs/geckolib4/
- https://github.com/bernie-g/geckolib/wiki/Geo-Models-%28Geckolib4%29
- https://github.com/bernie-g/geckolib/wiki/Keyframe-Triggers-%28Geckolib4%29

Confirmed:

- Minecraft 1.21.1 belongs to GeckoLib 4 support, while GeckoLib 5 applies to newer Minecraft generations;
- GeoModel connects geometry/model, texture and animation resources;
- animation keyframes can drive sound, particle and custom instruction callbacks.

Exact classes, methods and signatures used by Java code remain subject to the dependency actually installed in this repository. Inspect the current dependency/JAR before implementation.

## Project authority

The latest physical modlist/build remains authoritative for the exact GeckoLib version. Upstream documentation supplies workflow/API evidence, not permission to override the project's pinned runtime.