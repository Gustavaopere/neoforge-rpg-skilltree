# Alpha 7 — Reconciliation-aware runtime validation

Version target: `0.7.0-alpha.7-dev`
Target: Minecraft 1.21.1 / NeoForge
Status: development alpha; Beta still requires a functional runtime smoke test.

## Included
- fixes the CI contract after login/respawn reconciliation was centralized in `PlayerProgressionRuntime.reconcilePlayerState`;
- preserves all legacy runtime scaffold checks while accepting only the two known obsolete direct-call expectations;
- independently verifies that login/respawn delegate to reconciliation and that reconciliation reaches `set()`, attribute refresh, and owner synchronization;
- avoids reintroducing duplicate `AttributeNodeEffectRuntime.refresh` and `ModNetworking.syncToOwner` calls in event handlers.

## Beta gate
The corrected validator lets CI reach the real NeoForge compilation stage. Any compile/runtime issue found next remains an Alpha fix until a JAR builds and starts successfully.
