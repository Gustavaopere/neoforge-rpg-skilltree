# Pressure Plan — Equipment and Protection

**Goal:** unify what equipment protects against without making one item magically solve every hazard.

- [x] Define protection capabilities: `OXYGEN_SUPPLY`, `PARTICULATE_FILTER`, `ACID_GAS_FILTER`, `TOXIC_GAS_FILTER`, `PRESSURE_RATING`, `THERMAL_PROTECTION`.
- [x] Resolve capabilities from item tags/adapters and aggregate equipped armor/Curios safely.
- [x] Create Diving Helmet + Backtank adapter supplies breathable air but pressure protection requires suitable suit/rating.
- [x] Destroy masks/respirators, if API/tag-compatible, filter matching pollutants but do not solve low atmospheric pressure unless they provide oxygen.
- [x] Unit-test mixed equipment sets and resource consumption exactly once per update.

**Acceptance:** protection is modular, explainable and compatible with existing gear rather than duplicating it.

## Closure evidence

- `ProtectionCapability` keeps oxygen, particulate, acid, toxic, pressure and thermal channels independent; no item automatically receives unrelated protection.
- `PressureNeoForgeRuntime` aggregates vanilla armor plus bounded optional host equipment. Exact Curios `9.5.1+1.21.1` support uses `CuriosApi.getCuriosInventory(...).getEquippedCurios()` behind an exact-version, fail-closed compatibility gate.
- `CanonicalRespirationProtectionAdapter` maps only the three canonical Atmosphere filter tags to their matching filter capabilities. It deliberately grants no oxygen, `PRESSURE_RATING` or thermal protection.
- Create Diving Helmet + Backtank contributes only `OXYGEN_SUPPLY`; Atmosphere consumes it through the shared Pressure `ProtectionUseSession`, so one physical resource key is debited at most once per player/tick/update.
- Destroy-specific item semantics remain fail-closed because the verified Destroy 0.4.1 host surface does not provide a sufficiently precise stable mapping to Volcanoes filter channels/ratings. Compatible Destroy or datapack gear can opt into the canonical Volcanoes respiration tags; a host-specific semantic adapter remains Stage 06 work if a reliable contract is verified.
- Mixed capability composition, passive-vs-consumable selection, failed-consumer fail-closed behavior and single-debit semantics are covered by the protection tests plus the new Curios/tag/respiration bridge regressions.
- Exact pre-closeout verification: workflow `33263562636` GREEN on `4a7af9778b11ed8548142708838225aee46fec25`.
