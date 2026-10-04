# Universal Material Chemistry — Live Repository Path Lock

This document supersedes the illustrative Java/resource roots used in the first draft of the implementation plan. It is based on the live `main` source tree inspected after the plan was written.

## Confirmed runtime roots

The consolidated repository currently uses:

```text
src/main/java/dev/gustavopere/rpgskilltree/
src/main/java/dev/gustavopere/volcanoes/
src/test/java/dev/gustavopere/rpgskilltree/
src/test/java/dev/gustavopere/volcanoes/
```

The Volcanoes subsystem is a native subsystem of the single `rpgskilltree` NeoForge mod, but preserves `volcanoes` as its stable data/resource namespace.

Therefore the material subsystem implementation root is locked to:

```text
src/main/java/dev/gustavopere/volcanoes/material/
src/test/java/dev/gustavopere/volcanoes/material/
```

and its primary data/resource namespace is locked to:

```text
src/main/resources/data/volcanoes/materials/
src/main/resources/assets/volcanoes/materials/
```

unless a specific existing cross-RPG encyclopedia resource requires the owning `rpgskilltree` namespace. Cross-namespace resources must be explicit; the common catalog itself remains under `volcanoes`.

## Confirmed bootstrap boundaries

- Common Volcanoes bootstrap: `src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java`.
- Physical-client-only Volcanoes subscriber: `src/main/java/dev/gustavopere/volcanoes/VolcanoesClientMod.java`.
- The client subscriber uses `@EventBusSubscriber(modid = RpgSkillTreeMod.MOD_ID, value = Dist.CLIENT)`.
- New common catalog/runtime registration must follow `VolcanoesMod.initialize(...)` conventions or another already-established Volcanoes lifecycle hook.
- New physical-client rendering/event registration must follow the `VolcanoesClientMod`/`Dist.CLIENT` separation and must not be referenced from common bootstrap in a way that forces client classloading on dedicated server.

## Corrected file family

Where the implementation plan says an illustrative path under `src/main/java/com/gustavo/rpgskilltree/material/...`, read it as the corresponding path under:

```text
src/main/java/dev/gustavopere/volcanoes/material/...
```

Where it says an illustrative test path under `src/test/java/com/gustavo/rpgskilltree/material/...`, read it as:

```text
src/test/java/dev/gustavopere/volcanoes/material/...
```

Where it says `data/rpgskilltree/materials` or `assets/rpgskilltree/materials`, read the common material catalog/visual resources as:

```text
data/volcanoes/materials
assets/volcanoes/materials
```

This path lock is mandatory for execution and removes the initial package-root assumption from the plan.
