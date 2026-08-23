# Alpha 9 — Dedicated-server startup smoke test

Version target: `0.9.0-alpha.9-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: release-candidate alpha; Beta promotion requires this runtime gate to pass.

## Included
- extends the verified Alpha 8 build/JAR pipeline with an actual NeoForge dedicated-server startup;
- accepts the EULA only inside the ephemeral CI server run directory;
- requires the server log to reach Minecraft's ready `Done (...)!` state;
- fails immediately if the server process exits before readiness or times out before initialization;
- uploads the server smoke log when the runtime gate fails;
- preserves JAR structural verification and artifact upload after successful runtime initialization.

## Beta gate
If this Alpha builds, packages, and reaches dedicated-server ready state, the next commit is promoted to Beta and produces the first Beta JAR artifact.
