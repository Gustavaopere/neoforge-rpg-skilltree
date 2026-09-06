# Foundation Plan — Project Scaffold and CI

**Goal:** establish a reproducible NeoForge 1.21.1 build and verification pipeline.

**Planned files:** `build.gradle`, `settings.gradle`, `gradle.properties`, `src/main/resources/META-INF/neoforge.mods.toml`, `src/main/resources/pack.mcmeta`, `src/main/java/dev/gustavopere/volcanoes/VolcanoesMod.java`, `.github/workflows/build.yml`.

## TDD/verification sequence

- [x] Add JUnit 5 configuration and a minimal `ProjectContractTest` that asserts constants `MOD_ID="volcanoes"` and supported Minecraft line `1.21.1`. RED verified in workflow run `32896167822`: compilation failed because `VolcanoesMod` did not yet exist.
- [x] Implement the minimal mod entrypoint and constants; GREEN verified by the final Foundation CI.
- [x] Configure NeoForge `21.1.248`, Java 21 and NeoGradle `7.1.26`.
- [x] Add CI steps: checkout, Java 21, Gradle 8.14, `gradle test`, `git diff --check`, `gradle build`, verify JAR contains `META-INF/neoforge.mods.toml` and `VolcanoesMod.class`.
- [x] Add a dedicated-server smoke launch with EULA and a bounded startup timeout; final Foundation CI reported `Dedicated-server smoke test: PASS`.

## Verification evidence

Foundation final branch HEAD `0115fd1ba022b30ef81bd7dec60bd7ffd96a2987` passed push workflow `32901372365` and PR workflow `32902286035`. PR #2 merged to `main` as `bee79bc77688118e78e73deeb0cb3c06f0b7288b`. Post-merge `main` workflow `32903071520` attempt 1 failed before checkout because GitHub's runner could not resolve its internal action-download host; attempt 2 reran the same commit and passed unit tests, diff sanity, NeoForge build, built-JAR verification and dedicated-server smoke test.

## Acceptance criteria

`gradle test`, `gradle build` and CI pass from a clean checkout. Built JAR loads as `volcanoes`. No optional integration dependency is required to compile the base module.

**Acceptance status:** COMPLETE — merged in PR #2 and verified GREEN on `main`.
