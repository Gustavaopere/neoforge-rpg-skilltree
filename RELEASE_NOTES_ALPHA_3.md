# Alpha 3 — Mining provenance anti-farm

Version target: `0.3.0-alpha.3-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; not yet promoted to a playable Beta JAR.

## Included
- persistent per-dimension provenance for ore blocks placed by players/fake players;
- player-placed ore no longer grants Character XP when mined;
- provenance is consumed on break, including Creative breaks, preventing stale save markers;
- explosion cleanup removes provenance for destroyed ore positions;
- natural/generated ores remain XP-eligible;
- isolated core regression coverage for mark/consume/bulk-removal behavior.

## Beta gate
A Beta begins only after the project produces a JAR that builds successfully and initializes/functions in the target NeoForge 1.21.1 modpack environment.
