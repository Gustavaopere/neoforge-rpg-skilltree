# Volcano Sites Complete — Physical Sites and Worldgen

**Goal:** create persistent volcano sites that fit Terralith/Tectonic/BWG terrain without replacing the Overworld generator.

**Implemented types:** `VolcanoSite`, `VolcanoType`, `VolcanoSitePlanner`, `VolcanoSavedData`, `VolcanicRegionService`, `VolcanoCandidateField`, `VolcanoTerrainProfile`, `VolcanoWorldgenResolver`, `VolcanoWorldgenFeature`, `VolcanoWorldgenRegistry`, `VolcanoWorldgenRuntime`, `VolcanoRegistrationQueue`, `VolcanicTerrainHints`.

- [x] Placement score increases near convergent/divergent boundaries and hotspots and respects minimum spacing.
- [x] Known volcanic terrain/biome tags are positive hints only, never mandatory conditions.
- [x] `STRATOVOLCANO`, `SHIELD`, `FISSURE` and `CALDERA` profiles are mapped to tectonic context.
- [x] Physical terrain is generated only through a bounded feature during new-chunk worldgen; existing chunks are never retroactively carved.
- [x] Site ID, center, type, state and tectonic/geological context persist through `SavedData`; reloading or revisiting a chunk cannot duplicate a site.

## Runtime contract

- Site candidacy is deterministic from `worldSeed + coarse candidate cell + tectonic sample`; it does not depend on chunk generation order.
- Physical site identity is resolved statelessly for worldgen. `SavedData` is not read from the worldgen feature thread.
- A sparse deterministic candidate field and minimum-spacing checks prevent dense or duplicate physical volcanoes.
- Terrain shaping is bounded to the chunk currently being generated and to `VolcanoWorldgenFeature.MAX_FOOTPRINT_RADIUS_BLOCKS`.
- `ChunkEvent.Load` queues persistence only when `isNewChunk=true` in the Overworld. Existing chunks, Nether and End fail closed before touching volcano persistence.
- Deferred server-tick persistence resolves the canonical owner candidate and registers it idempotently in `VolcanoSavedData`.
- `volcanoes:volcano_sites` is registered as a NeoForge/Minecraft worldgen `Feature<?>` and injected through configured/placed feature data plus a biome modifier; dedicated-server bootstrap verifies the datapack registry path.
- Volcanic biome hints do not create or remove sites and do not change persistence IDs. They only apply a small local shaping multiplier to already-resolved sites.
- TFC's common `c:is_volcanic` tag is accepted as the primary cross-mod volcanic hint, with `tfc:is_rift` and `volcanoes:is_volcanic` supported independently. Known Terralith volcanic IDs are handled conservatively; generic `shield`/`snowy_shield` terrain is intentionally not treated as volcanic evidence.

## TDD / verification

- Core planning RED: `6680cbaebe65d36a4855d296188f66812a4b0216`, workflow `32922858998` — expected failure before deterministic volcano site planning/persistence existed.
- Core planning GREEN: `f6682165c338c7c87191e2697d11722e29a04826`, workflow `32923103925` — unit tests, diff sanity, NeoForge build, JAR verification and dedicated-server smoke passed.
- Worldgen integration RED: `80d96b120ebe6331e0c4b0f475d483320fc5d4a9`, workflow `32924750847` — expected failure before the bounded physical worldgen integration existed.
- Bounded feature implementation checkpoint: `edd9bf77abcbaa9647f23eef72e63dcbd6e100a2`, workflow `32925020137` — unit-side contract reached the real bootstrap boundary; smoke exposed the then-missing `volcanoes:volcano_sites` feature registry entry instead of masking it.
- Registry/runtime RED: `0796ec135f8b5bb3c5e0c0828bf651a6b0b88156`, workflow `32926284207` — expected compile failure because `VolcanoWorldgenRegistry` and `VolcanoWorldgenRuntime` did not yet exist.
- Registry/runtime GREEN: `5df67590fb2b2478ad140b7f1c9f868ba756c4c9`, workflow `32926618080` — 91 unit tests, diff sanity, build, JAR verification and dedicated-server smoke all passed; the previous `Unknown registry key ... volcanoes:volcano_sites` bootstrap failure was eliminated.
- Biome-hint RED: `9e579e84dbc0147c7c2ce38492ed661e2c9fe7e0`, workflow `32927015800` — expected compile failure for the new optional hint contract.
- Final GREEN: `75f4543a398c13fe0850fc2ddc0b0ef5fd719b85`, workflow `32927211134` — 94 unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke all passed.

## Compatibility and safety decisions

- No replacement Overworld generator is introduced.
- Terralith, Tectonic, BWG and TFC remain optional terrain providers/hints rather than hard runtime dependencies for site identity.
- Existing chunks remain untouched unless a future explicit migration mechanism is implemented.
- Worldgen does not scan loaded chunks, load neighboring chunks, or mutate `SavedData` from generation code.
- Site persistence is server-authoritative, coarse, deterministic and idempotent.

**Acceptance:** satisfied by the final branch verification at `75f4543a398c13fe0850fc2ddc0b0ef5fd719b85` / workflow `32927211134`. New worlds receive sparse deterministic physical volcano sites through normal Overworld feature generation, while existing chunks are excluded from automatic persistence and terrain shaping.
