# Alpha 4 — Ars Nouveau live mastery

Version target: `0.4.0-alpha.4-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; not yet promoted to a playable Beta JAR.

## Included
- optional Ars Nouveau 5.13.0 compile-time integration for Minecraft 1.21.1;
- real `SpellCastEvent` feed into the shared mastery runtime;
- only real server players receive mastery; Ars/NeoForge fake players and automated casters are excluded;
- every valid player cast advances `magic:casting`;
- spell recipes are classified into `ars:projectile`, `ars:amplification`, `ars:aoe`, `ars:duration`, `ars:summoning`, and `ars:control` from semantic glyph IDs;
- addon glyph namespaces participate without item/spell allowlists when their registered IDs describe the same mechanics;
- regression coverage for base Ars composition, addon-style IDs, and unrelated glyphs.

## Beta gate
A Beta begins only after the project produces a JAR that builds successfully and initializes/functions in the target NeoForge 1.21.1 modpack environment.
