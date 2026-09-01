# Tectonics Complete — Safe Earthquakes

**Goal:** make earthquakes geologically meaningful without Natural-Disasters-style mass destruction.

**Default policy:** `terrainDamage=false`, `structureDamage=false`.

- [x] Stress release can emit a `SeismicEvent` with epicenter, magnitude, radius and distance decay while block-damage flags remain false.
- [x] Server-side entity effects include a client-bound camera-shake payload, sound, brief movement instability and distance-decayed intensity.
- [x] Seismic events are dispatched through `SeismicPerturbationSink` consumers even when block damage is disabled; magma/geothermal implementations in the Volcanoes round can register without owning tectonics.
- [x] Future opt-in damage is fail-closed through `SeismicDamageDecider`: only caller-confirmed natural/replaceable blocks may qualify, while protected regions, block entities and protected/player structures remain excluded.
- [x] Base behavior has no MineCollapse dependency.

## Runtime contract

- `TectonicStressService.tryReleaseStress(...)` requires coarse regional stress at or above the release threshold, emits one safe event and immediately lowers persisted stress so multiple players cannot duplicate the same regional release.
- `SeismicEvent.intensityAt(...)` decays to zero at the configured radius.
- `SeismicEventDispatcher` never mutates terrain; it fans events out to registered geological perturbation consumers.
- `SeismicServerEffects` sends the shake payload, plays a local seismic sound, applies short movement slowdown and a small deterministic horizontal instability to affected players; it performs no block writes.
- `SeismicShakePayload` uses the stable `volcanoes:seismic_shake` channel registered through NeoForge 1.21.1 payload handlers.
- Camera rendering is isolated in the physical-client-only `VolcanoesClientMod` entrypoint; dedicated-server smoke verifies no client-class leakage crashes the server.
- `TectonicRuntime` invokes perturbation sinks and player effects automatically when a stress release occurs during the server cadence.
- Repository audit found no `MineCollapse` reference and no tectonics `setBlock` path.

## Downstream integration boundary

Concrete magma chambers and geothermal systems are part of `03-volcanoes`, so this round does not fabricate those systems early. Tectonics owns and executes the seismic perturbation port now; later magma/geothermal implementations register `SeismicPerturbationSink` consumers and receive real emitted events without adding a reverse dependency into tectonics.

## TDD / verification

Safe seismic core:
- RED: `1f44cd07ff73d1f8014d8237d08a4b2834edc7f9`, workflow `32919767446`.
- GREEN: `33ea45e65c1c227dc8ebde352e64b5084586e002`, workflow `32920018981` — full CI GREEN.

Networking/entity runtime:
- RED: `07921636147ac8b745ee4b1952ad3864ac8716c3`, workflow `32920192091`.
- GREEN: `74c61e9e6a87c54ece35aefdb7b4843e64c1af17`, workflow `32920432857` — full CI GREEN including dedicated-server smoke.

Automatic server cadence/release orchestration:
- RED: `95c8980cede3f02cbe104efef38ee57e1367d5e9`, workflow `32920613879`.
- GREEN: `9c4c2f2acbb6d34fb5916c7a8782eaa1233274a0`, workflow `32920876929` — full CI GREEN including dedicated-server smoke.

**Acceptance:** satisfied at the tectonics boundary. Magnitude events are automatically produced from persisted stress, are felt by nearby players, are available to downstream volcanism/geothermal consumers, and leave terrain/structures untouched under defaults.
