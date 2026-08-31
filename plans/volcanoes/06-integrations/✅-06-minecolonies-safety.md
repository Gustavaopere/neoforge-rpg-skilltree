# Integration Plan — MineColonies Safety

**Goal:** guarantee environmental systems do not casually destroy colony infrastructure.

- [x] Identify MineColonies claim/colony-boundary API for installed 1.21.1 version.
- [x] Implement `ProtectedAreaService` with MineColonies adapter plus generic extension point for claims.
- [x] Earthquake defaults never alter blocks anywhere; optional eruption/block effects check protected areas before mutation.
- [x] Volcano worldgen never retroactively spawns a new physical volcano inside already generated colony chunks.
- [x] Integration GameTests simulate protected blocks and verify the actual destructive hazard surface respects policy: ash, bombs/meteor impacts, pyroclastic terrain mutation and optional seismic terrain mutation. Lava terrain creation is a new-chunk worldgen concern rather than a separate runtime colony mutation path, and is therefore covered by the non-retroactive worldgen contract.

**Acceptance:** normal colony operation/mines are unaffected by the mod's default seismic behavior; protection is test-proven.

## Closure evidence

- `MineColoniesCompat` pins MineColonies `1.1.1374-1.21.1-snapshot`; absence yields an authoritative service with no MineColonies provider, while version/API mismatch yields an untrusted fail-closed service.
- `MineColoniesProtectedAreaProvider` uses the installed claim API (`IColonyManager.getClaimData`) and conservatively treats host-query failure as protected.
- `ProtectedAreaService` is a generic provider registry rather than MineColonies-specific core coupling; unreliable authority or provider failure blocks destructive mutation.
- `ProtectedAreaVolcanicProtectionBridge` covers ash, meteor/bomb, pyroclastic and optional earthquake-terrain mutation causes.
- `MineColoniesHazardProtectionGameTests` includes both deterministic protection tests and an exact-host test that creates a real MineColonies colony/claim and proves protected terrain survives ash, meteor and pyroclastic mutation.
- Default earthquake terrain mutation remains disabled.
- The Stage 03 volcano-site contract generates/carves physical volcano terrain only during new-chunk worldgen and persists sites from `ChunkEvent.Load` only when `isNewChunk=true`; already generated colony chunks are never retroactively recarved into volcanoes.
- `.github/workflows/minecolonies-claim-acceptance.yml` exercises the exact MineColonies host stack on NeoForge 1.21.1 / Java 21.
- Functional branch HEAD `69b031df88ebd3de34e8a2c2d902280d9293b84e` completed the Stage 06 branch workflows without failure before closure documentation was applied; final PR-head CI is required again before merge.
