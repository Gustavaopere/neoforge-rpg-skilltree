# Geology Plan — Rock Profiles ✅

**Goal:** make lava, geothermal and seismic systems query physical rock properties without TFC Registry API.

**Implemented files:** `geology/RockCategory.java`, `RockProfile.java`, `RockProfileRegistry.java`, `RockProfileResolver.java`, `RockProfileDataLoader.java`, `RockProfileReloadState.java`, `RockProfileRuntime.java`, `RockProfileReloadListener.java`; data under `data/volcanoes/rock_profiles/`.

- [x] Write tests for vanilla basalt, tuff, granite, and generic stone resolving to deterministic profiles.
- [x] Define categories `IGNEOUS_EXTRUSIVE`, `IGNEOUS_INTRUSIVE`, `SEDIMENTARY`, `METAMORPHIC`, `VOLCANIC_FRAGMENTAL`, `GENERIC`.
- [x] Define normalized properties: hardness, permeability, thermal conductivity, erosion resistance, lava-flow modifier and hydrothermal-reactivity.
- [x] Implement datapack reload that maps block IDs/tags to profiles and falls back to `GENERIC_STONE` for unknown modded blocks.
- [x] Add optional bundled mappings only for verified BWG/Create rock IDs; missing third-party mods do not hard-fail because bindings remain resource-location data.

## Runtime contract

`RockProfileRuntime.resolve(BlockState)` is the world-facing adapter. It extracts the block registry ID and the tags present on the state, then delegates to the same deterministic lookup core exercised by unit tests. Pure JUnit deliberately does not bootstrap Minecraft registries; lifecycle behavior is covered by the dedicated-server smoke test.

Reloads are atomic: malformed/conflicting definitions are rejected and the previously valid immutable snapshot remains active. Unknown blocks resolve to `GENERIC_STONE` rather than crashing downstream volcanism.

## Verified optional mappings

- `create:scoria` → `IGNEOUS_EXTRUSIVE`.
- `create:limestone` → `SEDIMENTARY`.
- `biomeswevegone:white_dacite` → `IGNEOUS_EXTRUSIVE`.
- `biomeswevegone:red_rock` was verified to exist but intentionally left unmapped because the block name alone does not establish a defensible lithology.

The Create IDs were checked against the official `mc1.21.1/dev` source assets. The BWG IDs were checked against the official `1.21.1` source assets before the mappings were added.

## Verification

Final implementation HEAD before this closeout: `592fe193c484b0410a2da12bee459c0951613655`.

GitHub Actions run `32909772057` is GREEN:

- unit tests: success (`20` tests, including bundled-resource coverage);
- `git diff --check`: success;
- NeoForge build: success;
- built JAR verification: success;
- dedicated-server smoke: success;
- smoke requires `Loaded 7 Volcanoes rock profile definitions` and fails closed on `Rejected Volcanoes rock profile reload`.

**Acceptance satisfied:** any block can be resolved safely; unknown blocks never crash volcanism; valid datapack reload changes behavior without recompilation; invalid reload preserves the prior snapshot; verified optional Create/BWG mappings remain safe when those mods are absent.
