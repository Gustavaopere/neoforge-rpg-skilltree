# Alpha 8 — Ars Nouveau dependency resolution

Version target: `0.8.0-alpha.8-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; Beta still requires a functional runtime smoke test.

## Included
- fixes the first real NeoForge build failure discovered by Alpha 7;
- pins Ars Nouveau 5.13.0 through its published Modrinth Maven artifact (`maven.modrinth:TKB6INcv:ugLa4qlw`);
- keeps the human-readable Ars version alongside the immutable artifact version id;
- reuses the existing exclusive Modrinth repository already used for Epic Fight;
- keeps Ars compile-only and non-transitive so the base RPG Skill Tree remains optional-provider safe.

## Beta gate
CI must now resolve the dependency and reach Java compilation/JAR verification. Any subsequent API mismatch is fixed as the next Alpha.
