# Integration Plan — Destroy Pollution

**Goal:** join volcanic emissions with the Destroy fork already used in the modpack.

- [x] Pin integration tests to the installed Destroy fork/version and inspect its current pollution API/tags rather than assuming upstream 1.20.1 internals.
- [x] Map SO2/acid gases to acidification, particulates to smog where supported, CO2 to greenhouse contribution where supported.
- [x] Read relevant Destroy pollution into `AtmosphereState` only through stable APIs/data; the verified Destroy 0.4.1 surface does not expose source-provenance-safe aggregate readback, so Volcanoes intentionally refuses aggregate reinjection rather than double-count pollution and fails closed if verified internals change.
- [x] Honor Destroy acid-rain immunity/destruction tags when adapter is active. Volcanoes delegates acid-rain consequences to Destroy instead of implementing a second acid-rain destruction/damage path, so Destroy remains the authority for its immunity/destruction tags.
- [x] Add duplicate-accounting tests so one Volcanoes emission produces one pollution contribution.

**Acceptance:** industrial and volcanic pollution interact instead of behaving as disconnected status effects.

## Closure evidence

- `DestroyCompat` pins Destroy `0.4.1` and installs only through the verified optional-host path.
- `DestroyNeoForgePollutionWriter` resolves the verified Destroy pollution types/API behind the exact-version gate and fails closed on API drift; no Destroy type leaks into unconditional core linkage.
- `DestroyPollutionApplicationLedger` makes each emission/component application idempotent and keeps retry semantics correct after a failed host mutation.
- `DestroyPollutionRuntime`/`VolcanicPollutionRuntime` publish a Volcanoes emission once to the optional host and deliberately suppress immediate feedback into the local Atmosphere fallback, preventing double accounting.
- `DestroyCompatContractTest` and `DestroyPollutionWriterContractTest` cover version gating, API/linkage failure, mapping and one-emission/one-contribution behavior.
- No independent Volcanoes acid-rain block-destruction/damage subsystem was found in the active runtime; when Destroy is active, its pollution mechanics remain authoritative for acid-rain tags and consequences.
- Functional branch HEAD `69b031df88ebd3de34e8a2c2d902280d9293b84e` completed the Stage 06 branch workflows without failure before closure documentation was applied; final PR-head CI is required again before merge.
