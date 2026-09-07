# PR summary

Adds repository-readable, audited Minecraft skill infrastructure under `PROJECT-INSTRUCTIONS/skills/`.

- 20 adapted skill entrypoints sourced from the user-supplied ZIP set;
- exact-version authority for Minecraft 1.21.1 / NeoForge 21.1.x / Java 21;
- one-manual-step-at-a-time user workflow;
- source ZIP SHA-256 manifest and safety audit;
- deterministic validator;
- repository entrypoint wiring through `AGENTS.md` and `PROJECT-INSTRUCTIONS/README.md`.

No runtime Java/dependency changes.
