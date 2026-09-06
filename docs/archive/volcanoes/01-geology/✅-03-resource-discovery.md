# Geology Plan — Resource Classification and Deposits ✅

**Goal:** replace TFC Registry API rock/metal discovery with NeoForge registries/tags and expose hydrothermal/volcanic deposit metadata for RNS integration.

**Implemented files:** `geology/GeologyResourceTags.java`, `GeologicalDeposit.java`, `DepositOrigin.java`, `DepositRegistry.java`, `GeologicalDepositSource.java`; resource tags under `data/volcanoes/tags/block/resources/`.

- [x] Define tags for copper, iron, gold and generic metallic/mineral resources using vanilla/common tags where available.
- [x] Define `GeologicalDeposit` with resource tag, center, radius, richness, origin (`MAGMATIC`, `HYDROTHERMAL`, `SEDIMENTARY`, `GENERIC`) and persistence ID.
- [x] Test deposits serialize/deserialize deterministically and do not duplicate when a chunk reloads.
- [x] Add `DepositRegistry`/saved-data storage; no player scanner is implemented in base Volcanoes.
- [x] Expose an integration SPI so RNS can surface Volcanoes deposits when installed.

## Runtime contract

`DepositRegistry` is per-level `SavedData`. A deposit is keyed by its stable persistence UUID: registering the same UUID with identical content is idempotent, while reusing an existing UUID with different geological content fails closed. Serialized deposits are emitted in stable UUID order, and spatial queries are deterministic.

`GeologicalDepositSource` is the read-only integration SPI. It exposes deterministic snapshots through `all()` and bounded spatial discovery through `nearby(BlockPos, radius)` without exposing registry mutation or importing an optional scanner API into the geology core.

The current Create: Rock & Stone API was inspected before defining this seam. RNS has `CustomServerDepositLocation` and `LevelDepositData.addCustomDeposit(...)`, so a later optional adapter can translate Volcanoes deposits into RNS custom deposits instead of adding a competing scanner to Volcanoes.

## Verification

The SPI was developed with an explicit RED/GREEN cycle:

- RED commit `fb0799cebbb71dd34def53f4e100b8febf6eaa7f`; GitHub Actions run `32915908463` failed at unit tests because the new integration contract did not yet exist.
- GREEN implementation HEAD `cdb93577bea54dcef72afa277367e839a66f00b7`; GitHub Actions run `32916045328` completed successfully.

Final GREEN gates:

- unit tests: success, including persistence/idempotency, deterministic serialization/querying and the read-only SPI contract;
- `git diff --check`: success;
- NeoForge build: success;
- built JAR verification: success;
- dedicated-server smoke: success.

**Acceptance satisfied:** Volcanoes can classify and create/query persistent deposits without TFC Registry API and exposes them for optional RNS integration without duplicating RNS scanner gameplay.
