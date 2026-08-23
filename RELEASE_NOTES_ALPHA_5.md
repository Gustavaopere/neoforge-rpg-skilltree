# Alpha 5 — Epic Fight live weapon mastery

Version target: `0.5.0-alpha.5-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; not yet promoted to a playable Beta JAR.

## Included
- optional Epic Fight `21.17.3.1-mc1.21.1-neoforge` compile-time integration;
- registration through Epic Fight's own Hook API rather than vanilla combat approximation;
- successful `DELIVER_DAMAGE_POST` actions feed the shared mastery runtime;
- fake players are excluded;
- the weapon category comes from the exact `usedItem` attached to Epic Fight's damage source, avoiding simple current-main-hand inference;
- base and addon weapon categories normalize into stable `epicfight:*` mastery lanes;
- zero-damage delivery does not grant mastery;
- isolated regression coverage for category normalization.

## Beta gate
A Beta begins only after the project produces a JAR that builds successfully and initializes/functions in the target NeoForge 1.21.1 modpack environment.
