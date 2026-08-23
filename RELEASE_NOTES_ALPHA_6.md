# Alpha 6 — Verifiable JAR build pipeline

Version target: `0.6.0-alpha.6-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; Beta still requires a functional runtime smoke test.

## Included
- CI renamed to the project-wide RPG Skill Tree pipeline;
- every push to `main` publishes a `rpgskilltree/full-build` commit status with a direct Actions run link;
- the status begins as `pending` and finishes as `success` only if all data/core/runtime validation and the NeoForge Gradle build succeed;
- the generated JAR is opened and verified to contain `META-INF/neoforge.mods.toml` and the main mod class;
- successful main builds upload the verified JAR as a 30-day GitHub Actions artifact;
- failures publish a `failure` status whose run link can be used to retrieve job steps and logs.

## Beta gate
After Alpha 6 proves a real JAR can be built, the remaining gate is runtime initialization/smoke testing. A JAR that builds but cannot initialize is not Beta.
