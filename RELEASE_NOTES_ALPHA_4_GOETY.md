# Integration Alpha 4 — Goety live Soul-backed mastery

Version target: `1.0.0-alpha.4-dev`
Target: Minecraft 1.21.1 / NeoForge 21.1.248
Provider target: Goety 3.1.4
Status: integration Alpha; **not Beta** and not a GitHub Release JAR.

## Included
- optional compile-time integration with Goety 3.1.4 using the current NeoForge 1.21.1 CurseForge artifact;
- registration only when mod id `goety` is loaded, preserving bare-NeoForge startup behavior;
- Goety spell types normalize into stable mastery lanes: necromancy, nether, ill, frost, geomancy, wind, storm, abyss, wild and void;
- `ISummonSpell` adds a separate `goety:summoning` lane;
- successful Goety practice feeds `occult:practice` and `goety:casting` rather than pretending Goety is simply another Iron/Ars Arcane provider;
- focus registry IDs are used as stable spell evidence, so addon-provided focuses can participate without a hardcoded spell list;
- normal, channelled, touch and block spell paths create candidates through Goety's own NeoForge events;
- mastery is confirmed only by Goety's `ChangeSoulEnergyEvent.Loss`, preventing invalid targeting/start-cast callbacks from granting progression;
- creative and NeoForge fake players do not gain Goety mastery;
- proc-generated actions remain blocked by the shared `ActionOrigin.procDepth` policy;
- stale pending casts expire before an unrelated Soul expenditure can confirm them.

## Pack architecture implications
- Goety Iron and Goety Cataclysm are treated as provider bridges/content expansions rather than separate top-level classes.
- Existing Summoner, Occultist, Necromancer and Warlock directions remain shared archetypes; Goety is one legitimate route into those fantasies, not the only route.
- ShadowsZ remains an Iron-side/new-system compatibility revision because its Shadow Monarch progression is native and should not be duplicated by Goety logic.
- Vampirism/Bloodlines/Werewolves are tracked separately as supernatural-state progression.

## Verification gate
This Alpha is only considered closed after core tests, full NeoForge Gradle build, JAR structure verification and dedicated-server smoke pass on the Alpha 4 commit. The smoke server does not load the user's complete modpack, so provider-present/full-pack runtime testing remains a later gate.
