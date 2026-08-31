# Integration Plan — Cold Sweat

**Goal:** let volcanic/geothermal heat and environmental conditions affect the existing temperature system.

- [x] Identify supported Cold Sweat modifier/event API for NeoForge 1.21.1.
- [x] Feed bounded local heat from lava, pyroclastic flows, hot springs and vents; avoid ticking every volcanic block individually.
- [x] Atmospheric/pressure systems do not duplicate body temperature.
- [x] Test modifier removal when source disappears or adapter unload conditions are not met.
- [x] Base Volcanoes behavior remains functional with Cold Sweat absent.

**Acceptance:** one temperature authority, no double thermal damage system.

## Closure evidence

- `ColdSweatCompat` pins Cold Sweat `2.4.2`, gates host linkage and fails closed on absence, mismatch or host-linkage failure.
- `ColdSweatIntegration` uses the official modifier/event API, samples the bounded `VolcanicHeatService`, refreshes at 20 ticks with a 40-tick TTL and removes the Volcanoes WORLD modifier when heat disappears.
- `ColdSweatHeatProjection` bounds source count, attenuation and contribution; Volcanoes contributes environmental heat only and never maintains a second body-temperature authority.
- `ColdSweatIntegrationGameTests` covers the exact-host path, bounded positive heat, source disappearance/removal and absence behavior.
- `.github/workflows/cold-sweat-acceptance.yml` exercises the exact Cold Sweat 2.4.2 host on NeoForge 1.21.1 / Java 21.
- Functional branch HEAD `69b031df88ebd3de34e8a2c2d902280d9293b84e` completed the Stage 06 branch workflows without failure before closure documentation was applied; final PR-head CI is required again before merge.
